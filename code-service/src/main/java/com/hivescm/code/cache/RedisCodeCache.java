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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisCodeCache.class);
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
			final RuleItemRelationBean relation, CacheLevelEnum cacheLeve, Boolean firstTime) {

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
	public void deleteBizUnitCache(final RuleItemRelationBean currentRelation) {
		final Integer orgId = currentRelation.getOrgId();
		final String bizCode = currentRelation.getBizCode();

		String redisTemplateKey = Constants.BIZ_UNIT_CODE_TEMPLATE_REDIS_PREFIX + orgId + ":" + bizCode;
		String redisSerialNumKey = Constants.BIZ_UNIT_CODE_SERIAL_NUM_REDIS_PREFIX + orgId + ":" + bizCode;
		String redisMaxSerialNumKey = Constants.BIZ_UNIT_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + orgId + ":" + bizCode;

		jedisClient.delete(redisTemplateKey, redisSerialNumKey, redisMaxSerialNumKey);
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
	 * @param bindingRelation 规则与组织的绑定关系
	 * @return Redis 缓存keies
	 */
	private List<String> jointCacheKey(final RuleItemRelationBean bindingRelation) {
		List<String> keies = new ArrayList<>(3);

		final Integer groupId = bindingRelation.getGroupId();
		final String bizCode = bindingRelation.getBizCode();
		if (groupId == Constants.PLATFORM_GROUP_ID) {
			keies.add(Constants.PLATFORM_CODE_TEMPLATE_REDIS_PREFIX + bizCode);
			keies.add(Constants.PLATFORM_CODE_SERIAL_NUM_REDIS_PREFIX + bizCode);
			keies.add(Constants.PLATFORM_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + bizCode);
			return keies;
		}

		final Integer orgId = bindingRelation.getOrgId();
		if (orgId == Constants.NO_ORG_ID) {
			keies.add(Constants.GROUP_CODE_TEMPLATE_REDIS_PREFIX + groupId + ":" + bizCode);
			keies.add(Constants.GROUP_CODE_SERIAL_NUM_REDIS_PREFIX + groupId + ":" + bizCode);
			keies.add(Constants.GROUP_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + groupId + ":" + bizCode);
			return keies;
		}

		keies.add(Constants.BIZ_UNIT_CODE_TEMPLATE_REDIS_PREFIX + orgId + ":" + bizCode);
		keies.add(Constants.BIZ_UNIT_CODE_SERIAL_NUM_REDIS_PREFIX + orgId + ":" + bizCode);
		keies.add(Constants.BIZ_UNIT_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + orgId + ":" + bizCode);
		return keies;
	}

	/**
	 * 新增编码规则初始化
	 *
	 * @param codeRule  编码规则
	 * @param codeItems 编码项
	 */
	private CodeCacheDataInfo initNewRule(final CodeRuleBean codeRule, final List<CodeItemBean> codeItems,
			final RuleItemRelationBean relation) {
		final Integer defaulted = codeRule.getDefaulted();
		if (BooleanEnum.TRUE.getTruth() != defaulted) {
			return null;
		}
		CodeCacheDataInfo codeCacheDataInfo = new CodeCacheDataInfo();
		TemplateCacheData templateCacheData = new TemplateCacheData();
		templateCacheData.setCodeRule(codeRule);
		templateCacheData.setCodeItems(codeItems);
		final String cacheData = GSON.toJson(templateCacheData);
		codeCacheDataInfo.setCacheTemplate(cacheData);

		final Integer groupId = codeRule.getGroupId();
		String redisTemplateKey;
		String redisSerialNumKey;
		String redisMaxSerialNumKey;
		final String bizCode = codeRule.getBizCode();
		if (Constants.PLATFORM_GROUP_ID == groupId) {
			redisTemplateKey = Constants.PLATFORM_CODE_TEMPLATE_REDIS_PREFIX + bizCode;
			redisSerialNumKey = Constants.PLATFORM_CODE_SERIAL_NUM_REDIS_PREFIX + bizCode;
			redisMaxSerialNumKey = Constants.PLATFORM_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + bizCode;
		} else {
			redisTemplateKey = Constants.GROUP_CODE_TEMPLATE_REDIS_PREFIX + groupId + ":" + bizCode;
			redisSerialNumKey = Constants.GROUP_CODE_SERIAL_NUM_REDIS_PREFIX + groupId + ":"+ bizCode;
			redisMaxSerialNumKey = Constants.GROUP_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + groupId + ":" + bizCode;
		}

		jedisClient.set(redisTemplateKey, cacheData);
		jedisClient.set(redisSerialNumKey, 0 + "");
		jedisClient.set(redisMaxSerialNumKey, relation.currentMaxSerialNum() + "");

		codeCacheDataInfo.setGroupId(groupId);
		codeCacheDataInfo.setOrgId(0);
		codeCacheDataInfo.setBizCode(bizCode);
		codeCacheDataInfo.setTemplateData(templateCacheData);
		codeCacheDataInfo.setSerialNumKey(redisSerialNumKey);
		codeCacheDataInfo.setMaxSerialNumKey(redisMaxSerialNumKey);
		return codeCacheDataInfo;
	}

	/**
	 * 业务单元级编码规则初始化
	 *
	 * @param codeRule  编码规则
	 * @param codeItems 编码项
	 * @param firstTime 是否第一次初始化
	 */
	private CodeCacheDataInfo initBizUnitRule(final CodeRuleBean codeRule, final List<CodeItemBean> codeItems,
			final RuleItemRelationBean relation, Boolean firstTime) {

		CodeCacheDataInfo codeCacheDataInfo = new CodeCacheDataInfo();
		TemplateCacheData templateCacheData = new TemplateCacheData();
		templateCacheData.setCodeRule(codeRule);
		templateCacheData.setCodeItems(codeItems);
		final String cacheData = GSON.toJson(templateCacheData);
		codeCacheDataInfo.setCacheTemplate(cacheData);

		final Integer orgId = relation.getOrgId();
		final String bizCode = codeRule.getBizCode();
		String redisTemplateKey = Constants.BIZ_UNIT_CODE_TEMPLATE_REDIS_PREFIX + orgId + ":" + bizCode;
		String redisSerialNumKey = Constants.BIZ_UNIT_CODE_SERIAL_NUM_REDIS_PREFIX + orgId + ":" + bizCode;
		String redisMaxSerialNumKey = Constants.BIZ_UNIT_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + orgId + ":" + bizCode;

		jedisClient.set(redisTemplateKey, cacheData);
		long cacheSerialNum = firstTime ? 0 : relation.nextCacheSerialNum();
		jedisClient.set(redisSerialNumKey, cacheSerialNum + "");
		long cacheMaxSerialNum = firstTime ? relation.currentMaxSerialNum() : relation.nextMaxSerialNum();
		jedisClient.set(redisMaxSerialNumKey, cacheMaxSerialNum + "");

		codeCacheDataInfo.setGroupId(relation.getGroupId());
		codeCacheDataInfo.setOrgId(orgId);
		codeCacheDataInfo.setBizCode(bizCode);
		codeCacheDataInfo.setTemplateData(templateCacheData);
		codeCacheDataInfo.setSerialNumKey(redisSerialNumKey);
		codeCacheDataInfo.setMaxSerialNumKey(redisMaxSerialNumKey);
		return codeCacheDataInfo;
	}

	/**
	 * 集团级编码规则初始化
	 *
	 * @param codeRule  编码规则
	 * @param codeItems 编码项
	 * @param firstTime 是否第一次初始化
	 */
	private CodeCacheDataInfo initGroupRule(final CodeRuleBean codeRule, final List<CodeItemBean> codeItems,
			final RuleItemRelationBean relation, Boolean firstTime) {

		CodeCacheDataInfo codeCacheDataInfo = new CodeCacheDataInfo();
		TemplateCacheData templateCacheData = new TemplateCacheData();
		templateCacheData.setCodeRule(codeRule);
		templateCacheData.setCodeItems(codeItems);
		final String cacheData = GSON.toJson(templateCacheData);
		codeCacheDataInfo.setCacheTemplate(cacheData);

		final Integer groupId = codeRule.getGroupId();
		final String bizCode = codeRule.getBizCode();
		String redisTemplateKey = Constants.GROUP_CODE_TEMPLATE_REDIS_PREFIX + groupId + ":" + bizCode;
		String redisSerialNumKey = Constants.GROUP_CODE_SERIAL_NUM_REDIS_PREFIX + groupId + ":" + bizCode;
		String redisMaxSerialNumKey = Constants.GROUP_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + groupId + ":" + bizCode;

		jedisClient.set(redisTemplateKey, cacheData);
		long cacheSerialNum = firstTime ? 0 : relation.nextCacheSerialNum();
		jedisClient.set(redisSerialNumKey, cacheSerialNum + "");
		long cacheMaxSerialNum = firstTime ? relation.currentMaxSerialNum() : relation.nextMaxSerialNum();
		jedisClient.set(redisMaxSerialNumKey, cacheMaxSerialNum + "");

		codeCacheDataInfo.setGroupId(groupId);
		codeCacheDataInfo.setOrgId(0);
		codeCacheDataInfo.setBizCode(bizCode);
		codeCacheDataInfo.setTemplateData(templateCacheData);
		codeCacheDataInfo.setSerialNumKey(redisSerialNumKey);
		codeCacheDataInfo.setMaxSerialNumKey(redisMaxSerialNumKey);
		return codeCacheDataInfo;
	}

	/**
	 * 平台级编码规则初始化
	 *
	 * @param codeRule  编码规则
	 * @param codeItems 编码项
	 * @param firstTime 是否第一次初始化
	 */
	private CodeCacheDataInfo initPlatformRule(final CodeRuleBean codeRule, final List<CodeItemBean> codeItems,
			final RuleItemRelationBean relation, Boolean firstTime) {

		CodeCacheDataInfo codeCacheDataInfo = new CodeCacheDataInfo();
		TemplateCacheData templateCacheData = new TemplateCacheData();
		templateCacheData.setCodeRule(codeRule);
		templateCacheData.setCodeItems(codeItems);
		final String cacheData = GSON.toJson(templateCacheData);
		codeCacheDataInfo.setCacheTemplate(cacheData);

		final Integer groupId = codeRule.getGroupId();
		final String bizCode = codeRule.getBizCode();
		String redisTemplateKey = Constants.PLATFORM_CODE_TEMPLATE_REDIS_PREFIX + bizCode;
		String redisSerialNumKey = Constants.PLATFORM_CODE_SERIAL_NUM_REDIS_PREFIX + bizCode;
		String redisMaxSerialNumKey = Constants.PLATFORM_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + bizCode;

		jedisClient.set(redisTemplateKey, cacheData);
		long cacheSerialNum = firstTime ? 0 : relation.nextCacheSerialNum();
		jedisClient.set(redisSerialNumKey, cacheSerialNum + "");
		long cacheMaxSerialNum = firstTime ? relation.currentMaxSerialNum() : relation.nextMaxSerialNum();
		jedisClient.set(redisMaxSerialNumKey, cacheMaxSerialNum + "");

		codeCacheDataInfo.setGroupId(groupId);
		codeCacheDataInfo.setOrgId(0);
		codeCacheDataInfo.setBizCode(bizCode);
		codeCacheDataInfo.setTemplateData(templateCacheData);
		codeCacheDataInfo.setSerialNumKey(redisSerialNumKey);
		codeCacheDataInfo.setMaxSerialNumKey(redisMaxSerialNumKey);
		return codeCacheDataInfo;
	}
}
