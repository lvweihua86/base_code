package com.hivescm.code.cache;

import com.google.gson.Gson;
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
	private static final Gson GSON = new Gson();

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
		CodeCacheDataInfo cacheBean = new CodeCacheDataInfo();

		// 获取 Redis 编码模板缓存
		final String bizCode = reqParam.getBizCode();
		final Integer groupId = reqParam.getGroupId();
		if (Constants.PLATFORM_GROUP_ID == groupId) {
			// 若集团ID为1，即取平台级的编码编码规则
			return getPlatFormCache(bizCode);
		}

		final Integer orgId = reqParam.getOrgId();
		if (!NumberUtil.nullOrlessThanOrEqualToZero(orgId)) {
			String redisTemplateCacheKey = Constants.BIZ_UNIT_CODE_TEMPLATE_REDIS_PREFIX + orgId + ":" + bizCode;
			final String cacheTemplate = jedisClient.get(redisTemplateCacheKey);
			LOGGER.debug("业务单元级缓存的编码规则模板：{}.", cacheTemplate);
			if (!StringUtils.isEmpty(cacheTemplate)) {
				cacheBean.setHasCache(true);
				cacheBean.setCacheTemplate(cacheTemplate);
				cacheBean.setSerialNumKey(Constants.BIZ_UNIT_CODE_SERIAL_NUM_REDIS_PREFIX + orgId + ":" + bizCode);
				cacheBean.setMaxSerialNumKey(Constants.BIZ_UNIT_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + orgId + ":" + bizCode);
				cacheBean.setOrgId(orgId);
				cacheBean.setGroupId(groupId);
				cacheBean.setBizCode(bizCode);
				return cacheBean;
			}
		}

		if (!NumberUtil.nullOrlessThanOrEqualToZero(groupId)) {
			String redisTemplateCacheKey = Constants.GROUP_CODE_TEMPLATE_REDIS_PREFIX + groupId + ":" + bizCode;
			final String cacheTemplate = jedisClient.get(redisTemplateCacheKey);
			LOGGER.debug("业务单元级缓存的编码规则模板：{}.", cacheTemplate);
			if (!StringUtils.isEmpty(cacheTemplate)) {
				cacheBean.setHasCache(true);
				cacheBean.setCacheTemplate(cacheTemplate);
				cacheBean.setSerialNumKey(Constants.GROUP_CODE_SERIAL_NUM_REDIS_PREFIX + groupId + ":" + bizCode);
				cacheBean.setMaxSerialNumKey(Constants.GROUP_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + groupId + ":" + bizCode);
				cacheBean.setOrgId(0);
				cacheBean.setGroupId(groupId);
				cacheBean.setBizCode(bizCode);
				return cacheBean;
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
		CodeCacheDataInfo cacheBean = new CodeCacheDataInfo();
		String redisTemplateCacheKey = Constants.PLATFORM_CODE_TEMPLATE_REDIS_PREFIX + bizCode;
		final String cacheTemplate = jedisClient.get(redisTemplateCacheKey);
		LOGGER.debug("平台级缓存的编码规则模板：{}.", cacheTemplate);
		if (!StringUtils.isEmpty(cacheTemplate)) {
			cacheBean.setHasCache(true);
			cacheBean.setCacheTemplate(cacheTemplate);
			cacheBean.setSerialNumKey(Constants.PLATFORM_CODE_SERIAL_NUM_REDIS_PREFIX + bizCode);
			cacheBean.setMaxSerialNumKey(Constants.PLATFORM_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + bizCode);
			cacheBean.setOrgId(0);
			cacheBean.setGroupId(Constants.PLATFORM_GROUP_ID);
			cacheBean.setBizCode(bizCode);
		}
		return cacheBean;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CodeCacheDataInfo findCodeRuleAndInitCacheData(final GenerateCode reqParam) {
		// 获取 Redis 编码模板缓存
		final String bizCode = reqParam.getBizCode();
		final Integer groupId = reqParam.getGroupId();
		if (Constants.PLATFORM_GROUP_ID == groupId) {// 平台级编码
			return getPlatFormRule(bizCode);
		}

		final Integer orgId = reqParam.getOrgId();
		if (!NumberUtil.nullOrlessThanOrEqualToZero(orgId)) {
			RuleItemRelationBean ruleItemRelation = ruleItemRelationMapper.queryDefaultBandingRuleLock(groupId, orgId,
					bizCode);
			if (ruleItemRelation != null) {
				// 再次获取模板，避免并发修改
				String redisTemplateCacheKey = Constants.BIZ_UNIT_CODE_TEMPLATE_REDIS_PREFIX + orgId + ":" + bizCode;
				final String cacheTemplate = jedisClient.get(redisTemplateCacheKey);
				if (!StringUtils.isEmpty(cacheTemplate)) { // 再次验证模板是否为空，若非空则表明并发缓存完毕
					CodeCacheDataInfo codeCacheDataInfo = new CodeCacheDataInfo();

					codeCacheDataInfo.setCacheTemplate(cacheTemplate);
					String redisSerialNumKey =
							Constants.BIZ_UNIT_CODE_SERIAL_NUM_REDIS_PREFIX + ruleItemRelation.getOrgId() + ":" + bizCode;
					String redisMaxSerialNumKey =
							Constants.BIZ_UNIT_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + ruleItemRelation.getOrgId() + ":" + bizCode;
					codeCacheDataInfo.setSerialNumKey(redisSerialNumKey);
					codeCacheDataInfo.setMaxSerialNumKey(redisMaxSerialNumKey);

					return codeCacheDataInfo;
				}

				// 缓存的步数 增加一次
				ruleItemRelationMapper.incrCacheStepNum(ruleItemRelation.getId(), 1);

				final Integer ruleId = ruleItemRelation.getRuleId();
				final CodeRuleBean codeRule = codeRuleMapper.queryRuleByIdLock(ruleId);
				final List<CodeItemBean> codeItems = codeItemMapper.queryItemsByRuleId(codeRule.getId());
				return redisCodeCache.initCache(codeRule, codeItems, ruleItemRelation, CacheLevelEnum.BIZ_UNIT, Boolean.FALSE);
			}
		}

		if (!NumberUtil.nullOrlessThanOrEqualToZero(groupId)) {
			RuleItemRelationBean ruleItemRelation = ruleItemRelationMapper.queryDefaultBandingRuleLock(groupId, 0, bizCode);
			if (ruleItemRelation != null) {
				// 再次获取模板，避免并发修改
				String redisTemplateCacheKey = Constants.GROUP_CODE_TEMPLATE_REDIS_PREFIX + groupId + ":" + bizCode;
				final String cacheTemplate = jedisClient.get(redisTemplateCacheKey);
				if (!StringUtils.isEmpty(cacheTemplate)) {
					// 再次验证模板是否为空，若非空则表明并发缓存完毕
					CodeCacheDataInfo codeCacheDataInfo = new CodeCacheDataInfo();
					codeCacheDataInfo.setCacheTemplate(cacheTemplate);
					codeCacheDataInfo.setSerialNumKey(Constants.GROUP_CODE_SERIAL_NUM_REDIS_PREFIX + groupId + ":" + bizCode);
					codeCacheDataInfo
							.setMaxSerialNumKey(Constants.GROUP_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + groupId + ":" + bizCode);
					return codeCacheDataInfo;
				}
				// 缓存的步数 增加一次
				ruleItemRelationMapper.incrCacheStepNum(ruleItemRelation.getId(), 1);

				final CodeRuleBean codeRule = codeRuleMapper.queryDefaultedRuleByBizCodeLock(bizCode, groupId);
				final List<CodeItemBean> codeItems = codeItemMapper.queryItemsByRuleId(codeRule.getId());
				return redisCodeCache.initCache(codeRule, codeItems, ruleItemRelation, CacheLevelEnum.GROUP, Boolean.FALSE);
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
		RuleItemRelationBean ruleItemRelation = ruleItemRelationMapper
				.queryDefaultBandingRuleLock(Constants.PLATFORM_GROUP_ID, 0, bizCode);
		if (ruleItemRelation == null) {
			return new CodeCacheDataInfo();
		}

		// 获取到锁后，判断缓存是否已经存在，若存在直接返回,避免并发阻塞重复初始化
		String redisTemplateCacheKey = Constants.PLATFORM_CODE_TEMPLATE_REDIS_PREFIX + bizCode;
		final String cacheTemplate = jedisClient.get(redisTemplateCacheKey);
		if (!StringUtils.isEmpty(cacheTemplate)) {
			CodeCacheDataInfo codeCacheDataInfo = new CodeCacheDataInfo();
			codeCacheDataInfo.setCacheTemplate(cacheTemplate);
			codeCacheDataInfo.setSerialNumKey(Constants.PLATFORM_CODE_SERIAL_NUM_REDIS_PREFIX + bizCode);
			codeCacheDataInfo.setMaxSerialNumKey(Constants.PLATFORM_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + bizCode);
			return codeCacheDataInfo;
		}

		// 缓存的步数 增加一次
		ruleItemRelationMapper.incrCacheStepNum(ruleItemRelation.getId(), 1);

		final CodeRuleBean codeRule = codeRuleMapper.queryDefaultedRuleByBizCodeLock(bizCode, Constants.PLATFORM_GROUP_ID);
		final List<CodeItemBean> codeItems = codeItemMapper.queryItemsByRuleId(codeRule.getId());
		return redisCodeCache.initCache(codeRule, codeItems, ruleItemRelation, CacheLevelEnum.PLATFORM, Boolean.FALSE);
	}
}
