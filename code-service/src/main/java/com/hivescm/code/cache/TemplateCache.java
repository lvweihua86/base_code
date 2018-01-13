package com.hivescm.code.cache;

import com.hivescm.cache.client.JedisClient;
import com.hivescm.code.bean.CodeItemBean;
import com.hivescm.code.bean.CodeRuleBean;
import com.hivescm.code.bean.RuleItemRelationBean;
import com.hivescm.code.common.Constants;
import com.hivescm.code.dto.GenerateCode;
import com.hivescm.code.enums.CacheLevelEnum;
import com.hivescm.code.mapper.CodeItemMapper;
import com.hivescm.code.mapper.CodeRuleMapper;
import com.hivescm.code.mapper.RuleItemRelationMapper;
import com.hivescm.code.utils.NumberUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <b>Description:</b><br>
 * 模板缓存 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.cache
 * <br><b>Date:</b> 2017/10/23 09:47
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@Component(value = "templateCache")
public class TemplateCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCodeCache.class);

    @Autowired
    private CodeRuleMapper codeRuleMapper;

    @Autowired
    private CodeItemMapper codeItemMapper;

    @Autowired
    private RuleItemRelationMapper ruleItemRelationMapper;

    @Resource
    private RedisCodeCache redisCodeCache;

    @Autowired
    private JedisClient jedisClient;

    public CodeCacheDataInfo getCodeInfo(final GenerateCode reqParam) {
        final CodeCacheDataInfo redisCacheData = getRedisCacheData(reqParam);
        if (redisCacheData.hasCache()) {
            return redisCacheData;
        }
        return findCodeRuleAndInitCacheData(reqParam);
    }

    /**
     * 获取缓存数据
     */
    private CodeCacheDataInfo getRedisCacheData(final GenerateCode reqParam) {
        // 获取 Redis 编码模板缓存
        final String bizCode = reqParam.getBizCode();
        final Integer groupId = reqParam.getGroupId();
        if (groupId != null && Constants.PLATFORM_GROUP_ID == groupId) {
            // 若集团ID为1，即取平台级的编码编码规则
            return getPlatFormCache(bizCode);
        }

        final Integer orgId = reqParam.getOrgId();
        if (!NumberUtil.nullOrlessThanOrEqualToZero(orgId)) {
            CacheKey key = CacheKey.getOrgCacheKey(orgId, bizCode);
            String cacheTemplate = jedisClient.get(key.getTemplateKey());
            LOGGER.debug("业务单元级缓存的编码规则模板：{}.", cacheTemplate);
            if (!StringUtils.isEmpty(cacheTemplate)) {
                return CodeCacheDataInfo.newOrgCache(groupId, orgId, bizCode, cacheTemplate, key.getSerialNumKey(),
                        key.getMaxSerialNumKey());
            }
        }

        if (!NumberUtil.nullOrlessThanOrEqualToZero(groupId)) {
            CacheKey key = CacheKey.getGroupCacheKey(groupId, bizCode);
            String cacheTemplate = jedisClient.get(key.getTemplateKey());
            LOGGER.debug("集团级缓存的编码规则模板：{}.", cacheTemplate);
            if (!StringUtils.isEmpty(cacheTemplate)) {
                return CodeCacheDataInfo.newGroupCache(groupId, bizCode, cacheTemplate, key.getSerialNumKey(),
                        key.getMaxSerialNumKey());
            }
        }
        // 均没有获取到模板，去平台级默认的模板
        return getPlatFormCache(bizCode);
    }

    /**
     * 获取平台级规则模板缓存
     *
     * @param bizCode 业务编码
     * @return 编码缓存信息
     */
    private CodeCacheDataInfo getPlatFormCache(String bizCode) {
        CacheKey key = CacheKey.getPlatformCacheKey(bizCode);
        String cacheTemplate = jedisClient.get(key.getTemplateKey());
        LOGGER.debug("平台级缓存的编码规则模板：{}.", cacheTemplate);
        if (!StringUtils.isEmpty(cacheTemplate)) {
            return CodeCacheDataInfo.newPlatformCache(bizCode, cacheTemplate, key.getSerialNumKey(),
                    key.getMaxSerialNumKey());
        }
        return new CodeCacheDataInfo();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public CodeCacheDataInfo findCodeRuleAndInitCacheData(GenerateCode reqParam) {
        // 获取 Redis 编码模板缓存
        String bizCode = reqParam.getBizCode();
        Integer groupId = reqParam.getGroupId();
        if (Constants.PLATFORM_GROUP_ID == groupId) {// 平台级编码
            return getPlatFormRule(bizCode);
        }

        Integer orgId = reqParam.getOrgId();
        if (!NumberUtil.nullOrlessThanOrEqualToZero(orgId)) {
            RuleItemRelationBean ruleItemRelation = ruleItemRelationMapper.queryDefaultBandingRuleLock(groupId, orgId,
                    bizCode);
            if (ruleItemRelation != null) {
                // 再次获取模板，避免并发修改
                CacheKey key = CacheKey.getOrgCacheKey(orgId, bizCode);
                String cacheTemplate = jedisClient.get(key.getTemplateKey());
                if (!StringUtils.isEmpty(cacheTemplate)) { // 再次验证模板是否为空，若非空则表明并发缓存完毕
                    return CodeCacheDataInfo.newOrgCache(groupId, orgId, bizCode, cacheTemplate, key.getSerialNumKey(),
                            key.getMaxSerialNumKey());
                }

                // 缓存的步数 增加一次
                ruleItemRelationMapper.incrCacheStepNum(ruleItemRelation.getId(), 1);

                Integer ruleId = ruleItemRelation.getRuleId();
                CodeRuleBean codeRule = codeRuleMapper.queryRuleByIdLock(ruleId);
                List<CodeItemBean> codeItems = codeItemMapper.queryItemsByRuleId(codeRule.getId());
                return redisCodeCache.initCache(codeRule, codeItems, ruleItemRelation, CacheLevelEnum.BIZ_UNIT,
                        Boolean.FALSE);
            }
        }

        if (!NumberUtil.nullOrlessThanOrEqualToZero(groupId)) {
            RuleItemRelationBean ruleItemRelation = ruleItemRelationMapper.queryDefaultBandingRuleLock(groupId,
                    Constants.NO_ORG_ID, bizCode);
            if (ruleItemRelation != null) {
                // 再次获取模板，避免并发修改
                CacheKey key = CacheKey.getGroupCacheKey(groupId, bizCode);
                String cacheTemplate = jedisClient.get(key.getTemplateKey());
                if (!StringUtils.isEmpty(cacheTemplate)) {
                    return CodeCacheDataInfo.newGroupCache(groupId, bizCode, cacheTemplate, key.getSerialNumKey(),
                            key.getMaxSerialNumKey());
                }
                // 缓存的步数 增加一次
                ruleItemRelationMapper.incrCacheStepNum(ruleItemRelation.getId(), 1);

                CodeRuleBean codeRule = codeRuleMapper.queryDefaultedRuleByBizCodeLock(bizCode, groupId);
                List<CodeItemBean> codeItems = codeItemMapper.queryItemsByRuleId(codeRule.getId());
                return redisCodeCache.initCache(codeRule, codeItems, ruleItemRelation, CacheLevelEnum.GROUP,
                        Boolean.FALSE);
            }
        }

        return getPlatFormRule(bizCode);
    }

    /**
     * 获取平台级规则模板缓存
     *
     * @param bizCode 业务编码
     * @return 编码缓存信息
     */
    private CodeCacheDataInfo getPlatFormRule(String bizCode) {
        RuleItemRelationBean ruleItemRelation = ruleItemRelationMapper.queryDefaultBandingRuleLock(
                Constants.PLATFORM_GROUP_ID, Constants.NO_ORG_ID, bizCode);
        if (ruleItemRelation == null) {
            return new CodeCacheDataInfo();
        }

        // 获取到锁后，判断缓存是否已经存在，若存在直接返回,避免并发阻塞重复初始化
        CacheKey key = CacheKey.getPlatformCacheKey(bizCode);
        String cacheTemplate = jedisClient.get(key.getTemplateKey());
        if (!StringUtils.isEmpty(cacheTemplate)) {
            return CodeCacheDataInfo.newPlatformCache(bizCode, cacheTemplate, key.getSerialNumKey(),
                    key.getMaxSerialNumKey());
        }

        // 缓存的步数 增加一次
        ruleItemRelationMapper.incrCacheStepNum(ruleItemRelation.getId(), 1);

        CodeRuleBean codeRule = codeRuleMapper.queryDefaultedRuleByBizCodeLock(bizCode, Constants.PLATFORM_GROUP_ID);
        List<CodeItemBean> codeItems = codeItemMapper.queryItemsByRuleId(codeRule.getId());
        return redisCodeCache.initCache(codeRule, codeItems, ruleItemRelation, CacheLevelEnum.PLATFORM, Boolean.FALSE);
    }
}
