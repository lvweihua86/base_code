package com.hivescm.code.cache;

import com.google.gson.Gson;
import com.hivescm.cache.client.JedisClient;
import com.hivescm.code.bean.CodeItemBean;
import com.hivescm.code.bean.CodeRuleBean;
import com.hivescm.code.bean.RuleItemRelationBean;
import com.hivescm.code.common.Constants;
import com.hivescm.code.enums.BooleanEnum;
import com.hivescm.code.enums.CacheLevelEnum;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Description:</b><br>
 * Redis 编码缓存初始化类 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.cache
 * <br><b>Date:</b> 2017/10/20 20:10
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@Component(value = "redisCodeCache")
public class RedisCodeCache {
    private static final Gson GSON = new Gson();

    @Autowired
    private JedisClient jedisClient;

    /**
     * 初始化编码规则模板数据
     *
     * @param codeRule  编码规则
     * @param codeItems 编码项集
     * @param relation  绑定关系
     * @param firstTime 是否第一次初始化
     */
    public CodeCacheDataInfo initCache(final CodeRuleBean codeRule, final List<CodeItemBean> codeItems,
                                       final RuleItemRelationBean relation, CacheLevelEnum cacheLeve, Boolean
                                               firstTime) {
        switch (cacheLeve) {
            case NEW:
                return initNewRule(codeRule, codeItems, relation);
            case BIZ_UNIT:
                return initBizUnitRule(codeRule, codeItems, relation, firstTime);
            case GROUP:
                return initGroupRule(codeRule, codeItems, relation, firstTime);
            case PLATFORM:
                return initPlatformRule(codeRule, codeItems, relation, firstTime);
        }
        throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "缓存级别不存在");
    }

    /**
     * 删除业务单元级的缓存
     *
     * @param currentRelation
     */
    public void deleteBizUnitCache(RuleItemRelationBean currentRelation) {
        CacheKey key = CacheKey.getOrgCacheKey(currentRelation.getOrgId(), currentRelation.getBizCode());
        jedisClient.delete(key.getTemplateKey(), key.getSerialNumKey(), key.getMaxSerialNumKey());
    }

    /**
     * 批量删除缓存
     *
     * @param bindingRelations 规则绑定关系
     */
    public void batchDeleteCache(final List<RuleItemRelationBean> bindingRelations) {
        if (CollectionUtils.isEmpty(bindingRelations)) {
            return;
        }

        final List<String> allKeies = new ArrayList<>(bindingRelations.size() * 3);
        for (RuleItemRelationBean bindingRelation : bindingRelations) {
            List<String> keies = jointCacheKey(bindingRelation);
            allKeies.addAll(keies);

        }
        jedisClient.delete(allKeies.toArray(new String[0]));
    }

    /**
     * 依据绑定关系拼接Redis缓存key
     *
     * @param relation 规则与组织的绑定关系
     * @return Redis 缓存keys
     */
    private List<String> jointCacheKey(RuleItemRelationBean relation) {
        Integer groupId = relation.getGroupId();
        String bizCode = relation.getBizCode();
        Integer orgId = relation.getOrgId();

        if (groupId == Constants.PLATFORM_GROUP_ID) {
            return CacheKey.getPlatformCacheKeyList(bizCode);
        }
        if (orgId == Constants.NO_ORG_ID) {
            return CacheKey.getGroupCacheKeyList(groupId, bizCode);
        }
        return CacheKey.getOrgCacheKeyList(orgId, bizCode);
    }

    /**
     * 新增编码规则初始化
     *
     * @param codeRule  编码规则
     * @param codeItems 编码项
     */
    private CodeCacheDataInfo initNewRule(CodeRuleBean codeRule, List<CodeItemBean> codeItems,
                                          RuleItemRelationBean relation) {
        final Integer defaulted = codeRule.getDefaulted();
        if (BooleanEnum.TRUE.getTruth() != defaulted) {
            return null;
        }

        // template cache
        TemplateCacheData templateCacheData = new TemplateCacheData(codeRule, codeItems);
        final String cacheData = GSON.toJson(templateCacheData);
        // cache keys
        CacheKey key = null;
        if (Constants.PLATFORM_GROUP_ID == codeRule.getGroupId()) {
            key = CacheKey.getPlatformCacheKey(codeRule.getBizCode());
        } else {
            key = CacheKey.getGroupCacheKey(codeRule.getGroupId(), codeRule.getBizCode());
        }
        // set to redis
        setRedis(key.getTemplateKey(), cacheData, key.getSerialNumKey(), "0", key.getMaxSerialNumKey(),
                String.valueOf(relation.currentMaxSerialNum()));
        return CodeCacheDataInfo.newCache(relation.getGroupId(), codeRule.getBizCode(), cacheData, templateCacheData,
                key.getSerialNumKey(), key.getMaxSerialNumKey());
    }

    /**
     * 业务单元级编码规则初始化
     *
     * @param codeRule  编码规则
     * @param codeItems 编码项
     * @param firstTime 是否第一次初始化
     */
    private CodeCacheDataInfo initBizUnitRule(CodeRuleBean codeRule, List<CodeItemBean> codeItems,
                                              RuleItemRelationBean relation, Boolean firstTime) {
        // template cache
        TemplateCacheData templateCacheData = new TemplateCacheData(codeRule, codeItems);
        String cacheData = GSON.toJson(templateCacheData);
        // cache keys
        CacheKey key = CacheKey.getOrgCacheKey(relation.getOrgId(), codeRule.getBizCode());
        // cache nums
        String[] nums = getCacheSerialNumAndCacheMaxSerialNum(relation, firstTime);
        // set to redis
        setRedis(key.getTemplateKey(), cacheData, key.getSerialNumKey(), nums[0], key.getMaxSerialNumKey(), nums[1]);
        return CodeCacheDataInfo.newOrgCache(relation.getGroupId(), relation.getOrgId(), codeRule.getBizCode(),
                cacheData, templateCacheData, key.getSerialNumKey(), key.getMaxSerialNumKey());
    }

    /**
     * 集团级编码规则初始化
     *
     * @param codeRule  编码规则
     * @param codeItems 编码项
     * @param firstTime 是否第一次初始化
     */
    private CodeCacheDataInfo initGroupRule(CodeRuleBean codeRule, List<CodeItemBean> codeItems,
                                            RuleItemRelationBean relation, Boolean firstTime) {
        // template cache
        TemplateCacheData templateCacheData = new TemplateCacheData(codeRule, codeItems);
        String cacheData = GSON.toJson(templateCacheData);
        // cache keys
        CacheKey key = CacheKey.getGroupCacheKey(codeRule.getGroupId(), codeRule.getBizCode());
        // cache nums
        String[] nums = getCacheSerialNumAndCacheMaxSerialNum(relation, firstTime);
        // set to redis
        setRedis(key.getTemplateKey(), cacheData, key.getSerialNumKey(), nums[0], key.getMaxSerialNumKey(), nums[1]);
        return CodeCacheDataInfo.newGroupCache(codeRule.getGroupId(), codeRule.getBizCode(), cacheData,
                templateCacheData, key.getSerialNumKey(), key.getMaxSerialNumKey());
    }

    /**
     * 平台级编码规则初始化
     *
     * @param codeRule  编码规则
     * @param codeItems 编码项
     * @param firstTime 是否第一次初始化
     */
    private CodeCacheDataInfo initPlatformRule(CodeRuleBean codeRule, List<CodeItemBean> codeItems,
                                               RuleItemRelationBean relation, Boolean firstTime) {
        // template cache
        TemplateCacheData templateCacheData = new TemplateCacheData(codeRule, codeItems);
        String cacheData = GSON.toJson(templateCacheData);
        // cache keys
        CacheKey key = CacheKey.getPlatformCacheKey(codeRule.getBizCode());
        // cache nums
        String[] nums = getCacheSerialNumAndCacheMaxSerialNum(relation, firstTime);
        // set to redis
        setRedis(key.getTemplateKey(), cacheData, key.getSerialNumKey(), nums[0], key.getMaxSerialNumKey(), nums[1]);
        return CodeCacheDataInfo.newPlatformCache(codeRule.getBizCode(), cacheData, templateCacheData,
                key.getSerialNumKey(), key.getMaxSerialNumKey());
    }

    private void setRedis(String templateKey, String templateData,//
                          String serialNumKey, String serialNumData,//
                          String maxSerialNumKey, String maxSerialNumData) {
        jedisClient.set(templateKey, templateData);
        jedisClient.set(serialNumKey, serialNumData);
        jedisClient.set(maxSerialNumKey, maxSerialNumData);
    }

    private String[] getCacheSerialNumAndCacheMaxSerialNum(RuleItemRelationBean relation, boolean firstTime) {
        long cacheSerialNum = firstTime ? 0 : relation.nextCacheSerialNum();
        long cacheMaxSerialNum = firstTime ? relation.currentMaxSerialNum() : relation.nextMaxSerialNum();
        return new String[]{String.valueOf(cacheSerialNum), String.valueOf(cacheMaxSerialNum)};
    }
}
