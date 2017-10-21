package com.hivescm.code.cache;

import com.google.gson.Gson;
import com.hivescm.cache.client.JedisClient;
import com.hivescm.code.bean.CodeItemBean;
import com.hivescm.code.bean.CodeRuleBean;
import com.hivescm.code.bean.RuleItemRelationBean;
import com.hivescm.code.common.Constants;
import com.hivescm.code.dto.GenerateCode;
import com.hivescm.code.enums.BooleanEnum;
import com.hivescm.code.enums.CacheLevelEnum;
import com.hivescm.code.enums.LevelEnum;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.utils.NumberUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisCodeCache.class);
	private static final Gson GSON = new Gson();

	@Autowired
	private JedisClient jedisClient;

	/**
	 * 初始化编码规则模板数据
	 *
	 * @param codeRule  编码规则
	 * @param codeItems 编码项集
	 * @param relation  绑定关系（平台级可空）
	 */
	public void initCache(final CodeRuleBean codeRule, final List<CodeItemBean> codeItems,
			final RuleItemRelationBean relation, CacheLevelEnum cacheLeve) {

		if (cacheLeve != CacheLevelEnum.NEW) {
			if (relation == null) {
				throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "关系及步长信息不得为空");
			}
		}

		switch (cacheLeve) {
		case NEW:
			initNewRule(codeRule, codeItems);
			return;
		case BIZ_UNIT:
			initBizUnitRule(codeRule, codeItems, relation);
			return;
		case GROUP:
			initGroupRule(codeRule, codeItems, relation);
			return;
		case PLATFORM:
			initPlatformRule(codeRule, codeItems, relation);
			return;
		}
		throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "缓存级别不存在");
	}

	/**
	 * 新增编码规则初始化
	 *
	 * @param codeRule  编码规则
	 * @param codeItems 编码项
	 */
	private void initNewRule(final CodeRuleBean codeRule, final List<CodeItemBean> codeItems) {
		final Integer defaulted = codeRule.getDefaulted();
		if (BooleanEnum.TRUE.getTruth() != defaulted) {
			return;
		}
		TemplateCacheData templateCacheData = new TemplateCacheData();
		templateCacheData.setCodeRule(codeRule);
		templateCacheData.setCodeItems(codeItems);
		final String cacheData = GSON.toJson(templateCacheData);

		final Integer ruleLevel = codeRule.getRuleLevel();
		if (LevelEnum.PLATFORM.getLevel() == ruleLevel) {
			String redisTemplateKey = Constants.PLATFORM_CODE_TEMPLATE_REDIS_PREFIX + codeRule.getBizCode();
			String redisSerialNumKey = Constants.PLATFORM_CODE_SERIAL_NUM_REDIS_PREFIX + codeRule.getBizCode();
			String redisMaxSerialNumKey = Constants.PLATFORM_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + codeRule.getBizCode();

			jedisClient.set(redisTemplateKey, cacheData);
			jedisClient.set(redisSerialNumKey, "1");
			jedisClient.set(redisMaxSerialNumKey, "10000");
		}

		if (LevelEnum.GROUP.getLevel() == ruleLevel) {
			String redisTemplateKey =
					Constants.GROUP_CODE_TEMPLATE_REDIS_PREFIX + codeRule.getGroupId() + ":" + codeRule.getBizCode();
			String redisSerialNumKey = Constants.GROUP_CODE_SERIAL_NUM_REDIS_PREFIX + codeRule.getBizCode();
			String redisMaxSerialNumKey =
					Constants.GROUP_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + codeRule.getGroupId() + ":" + codeRule.getBizCode();

			jedisClient.set(redisTemplateKey, cacheData);
			jedisClient.set(redisSerialNumKey, "1");
			jedisClient.set(redisMaxSerialNumKey, "10000");
		}
	}

	/**
	 * 新增编码规则初始化
	 *
	 * @param codeRule  编码规则
	 * @param codeItems 编码项
	 */
	private void initBizUnitRule(final CodeRuleBean codeRule, final List<CodeItemBean> codeItems,
			final RuleItemRelationBean relation) {
		TemplateCacheData templateCacheData = new TemplateCacheData();
		templateCacheData.setCodeRule(codeRule);
		templateCacheData.setCodeItems(codeItems);
		final String cacheData = GSON.toJson(templateCacheData);

		String redisTemplateKey =
				Constants.BIZ_UNIT_CODE_TEMPLATE_REDIS_PREFIX + relation.getOrgId() + ":" + codeRule.getBizCode();
		String redisSerialNumKey =
				Constants.BIZ_UNIT_CODE_SERIAL_NUM_REDIS_PREFIX + relation.getOrgId() + ":" + codeRule.getBizCode();
		String redisMaxSerialNumKey =
				Constants.BIZ_UNIT_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + relation.getOrgId() + ":" + codeRule.getBizCode();

		jedisClient.set(redisTemplateKey, cacheData);
		jedisClient.set(redisSerialNumKey, relation.getStepNum() * relation.getStepNum() + "");
		jedisClient.set(redisMaxSerialNumKey, (relation.getStepNum() + 1) * relation.getStepNum() + "");
	}

	/**
	 * 新增编码规则初始化
	 *
	 * @param codeRule  编码规则
	 * @param codeItems 编码项
	 */
	private void initGroupRule(final CodeRuleBean codeRule, final List<CodeItemBean> codeItems,
			final RuleItemRelationBean relation) {
		TemplateCacheData templateCacheData = new TemplateCacheData();
		templateCacheData.setCodeRule(codeRule);
		templateCacheData.setCodeItems(codeItems);
		final String cacheData = GSON.toJson(templateCacheData);
		final Integer groupId = codeRule.getGroupId();

		String redisTemplateKey =
				Constants.GROUP_CODE_TEMPLATE_REDIS_PREFIX + groupId + ":" + codeRule.getBizCode();
		String redisSerialNumKey =
				Constants.GROUP_CODE_SERIAL_NUM_REDIS_PREFIX + groupId + ":" + codeRule.getBizCode();
		String redisMaxSerialNumKey =
				Constants.GROUP_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + groupId + ":" + codeRule.getBizCode();

		jedisClient.set(redisTemplateKey, cacheData);
		jedisClient.set(redisSerialNumKey, relation.getStepNum() * relation.getStepNum() + "");
		jedisClient.set(redisMaxSerialNumKey, (relation.getStepNum() + 1) * relation.getStepNum() + "");
	}

	/**
	 * 新增编码规则初始化
	 *
	 * @param codeRule  编码规则
	 * @param codeItems 编码项
	 */
	private void initPlatformRule(final CodeRuleBean codeRule, final List<CodeItemBean> codeItems,
			final RuleItemRelationBean relation) {
		TemplateCacheData templateCacheData = new TemplateCacheData();
		templateCacheData.setCodeRule(codeRule);
		templateCacheData.setCodeItems(codeItems);
		final String cacheData = GSON.toJson(templateCacheData);
		final Integer groupId = codeRule.getGroupId();

		String redisTemplateKey =
				Constants.PLATFORM_CODE_TEMPLATE_REDIS_PREFIX + codeRule.getBizCode();
		String redisSerialNumKey =
				Constants.PLATFORM_CODE_SERIAL_NUM_REDIS_PREFIX + codeRule.getBizCode();
		String redisMaxSerialNumKey =
				Constants.PLATFORM_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + codeRule.getBizCode();

		jedisClient.set(redisTemplateKey, cacheData);
		jedisClient.set(redisSerialNumKey, relation.getStepNum() * relation.getStepNum() + "");
		jedisClient.set(redisMaxSerialNumKey, (relation.getStepNum() + 1) * relation.getStepNum() + "");
	}

	/**
	 * 获取缓存数据
	 */
	public CodeCacheDataInfo getCacheData(final GenerateCode reqParam) {
		CodeCacheDataInfo cacheBean = new CodeCacheDataInfo();

		// 获取 Redis 编码模板缓存
		final String bizCode = reqParam.getBizCode();
		final Integer groupId = reqParam.getGroupId();
		if (Constants.PLATFORM_GROUP_ID == groupId) {// 平台级编码
			return getPlatFormCache(bizCode);
		}

		final Integer orgId = reqParam.getOrgId();
		if (!NumberUtil.nullOrlessThanOrEqualToZero(orgId)) {
			String redisTemplateCacheKey = Constants.BIZ_UNIT_CODE_TEMPLATE_REDIS_PREFIX + orgId + ":" + bizCode;
			final String cacheTemplate = jedisClient.get(redisTemplateCacheKey);
			LOGGER.debug("业务单元级缓存的编码规则模板：{}.", cacheTemplate);
			if (!StringUtils.isEmpty(cacheTemplate)) {
				cacheBean.setHasCaceh(true);
				cacheBean.setCodTemplate(cacheTemplate);
				cacheBean.setSerialNumKey(Constants.BIZ_UNIT_CODE_TEMPLATE_REDIS_PREFIX + orgId + ":" + bizCode);
				cacheBean.setMaxSerialNumKey(Constants.BIZ_UNIT_CODE_TEMPLATE_REDIS_PREFIX + orgId + ":" + bizCode);
				return cacheBean;
			}
		}

		if (!NumberUtil.nullOrlessThanOrEqualToZero(groupId)) {
			String redisTemplateCacheKey = Constants.GROUP_CODE_TEMPLATE_REDIS_PREFIX + groupId + ":" + bizCode;
			final String cacheTemplate = jedisClient.get(redisTemplateCacheKey);
			LOGGER.debug("业务单元级缓存的编码规则模板：{}.", cacheTemplate);
			if (!StringUtils.isEmpty(cacheTemplate)) {
				cacheBean.setHasCaceh(true);
				cacheBean.setCodTemplate(cacheTemplate);
				cacheBean.setSerialNumKey(Constants.GROUP_CODE_TEMPLATE_REDIS_PREFIX + groupId + ":" + bizCode);
				cacheBean.setMaxSerialNumKey(Constants.GROUP_CODE_TEMPLATE_REDIS_PREFIX + groupId + ":" + bizCode);
				return cacheBean;
			}
		}

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
			cacheBean.setHasCaceh(true);
			cacheBean.setCodTemplate(cacheTemplate);
			cacheBean.setSerialNumKey(Constants.PLATFORM_CODE_SERIAL_NUM_REDIS_PREFIX + bizCode);
			cacheBean.setMaxSerialNumKey(Constants.PLATFORM_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + bizCode);
		}
		return cacheBean;
	}
}
