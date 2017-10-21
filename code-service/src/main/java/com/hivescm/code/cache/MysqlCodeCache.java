package com.hivescm.code.cache;

import com.hivescm.cache.client.JedisClient;
import com.hivescm.code.bean.CodeItemBean;
import com.hivescm.code.bean.CodeRuleBean;
import com.hivescm.code.bean.RuleItemRelationBean;
import com.hivescm.code.common.Constants;
import com.hivescm.code.dto.GenerateCode;
import com.hivescm.code.enums.CacheLevelEnum;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.mapper.CodeItemMapper;
import com.hivescm.code.mapper.CodeRuleMapper;
import com.hivescm.code.mapper.RuleItemRelationMapper;
import com.hivescm.code.utils.NumberUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <b>Description:</b><br>
 * Mysql 缓存类 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.cache
 * <br><b>Date:</b> 2017/10/20 21:08
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@Component(value = "mysqlCodeCache")
public class MysqlCodeCache {

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

	/**
	 * 查找数据库并初始化Redis缓存
	 *
	 * @param reqParam
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public CodeCacheDataInfo findAndInitCodeRule(final GenerateCode reqParam) {
		CodeCacheDataInfo codeCacheDataInfo = new CodeCacheDataInfo();
		// 获取 Redis 编码模板缓存
		final String bizCode = reqParam.getBizCode();
		final Integer groupId = reqParam.getGroupId();
		if (Constants.PLATFORM_GROUP_ID == groupId) {// 平台级编码
			getPlatFormRule(bizCode);
			return;
		}

		final Integer orgId = reqParam.getOrgId();
		if (!NumberUtil.nullOrlessThanOrEqualToZero(orgId)) {
			final RuleItemRelationBean ruleItemRelation = ruleItemRelationMapper
					.queryDefaultBandingRuleLock(orgId, bizCode);

			String redisTemplateCacheKey = Constants.BIZ_UNIT_CODE_TEMPLATE_REDIS_PREFIX + orgId + ":" + bizCode;
			final String cacheTemplate = jedisClient.get(redisTemplateCacheKey);
			if (!StringUtils.isEmpty(cacheTemplate)) {
				return;
			}

			if (ruleItemRelation != null) {
				final Integer ruleId = ruleItemRelation.getRuleId();
				final CodeRuleBean codeRule = codeRuleMapper.queryRuleByIdLock(ruleId);

				if (codeRule == null) {
					throw new CodeException(CodeErrorCode.DATE_NOT_EXIST_ERROR_CODE, "业务编码对应的默认编码规则不存在");
				}

				final List<CodeItemBean> codeItems = codeItemMapper.queryItemsByRuleId(codeRule.getId());

				redisCodeCache.initCache(codeRule, codeItems, ruleItemRelation, CacheLevelEnum.BIZ_UNIT);
				return;
			}
		}

		if (!NumberUtil.nullOrlessThanOrEqualToZero(groupId)) {
			final CodeRuleBean codeRule = codeRuleMapper.queryDefaultedRuleByBizCodeLock(bizCode, groupId);
			if (codeRule == null) {
				throw new CodeException(CodeErrorCode.DATE_NOT_EXIST_ERROR_CODE, "业务编码对应的默认编码规则不存在");
			}

			String redisTemplateCacheKey = Constants.GROUP_CODE_TEMPLATE_REDIS_PREFIX + groupId + ":" + bizCode;
			final String cacheTemplate = jedisClient.get(redisTemplateCacheKey);
			if (!StringUtils.isEmpty(cacheTemplate)) {
				return;
			}

			final List<CodeItemBean> codeItems = codeItemMapper.queryItemsByRuleId(codeRule.getId());
			redisCodeCache.initCache(codeRule, codeItems, null,CacheLevelEnum.GROUP);
			return;
		}

		getPlatFormRule(bizCode);
	}

	/**
	 * 获取平台级规则模板缓存
	 *
	 * @param bizCode 业务编码
	 * @return 编码缓存信息
	 */
	private CodeCacheDataInfo getPlatFormRule(String bizCode) {

		final CodeRuleBean codeRule = codeRuleMapper.queryDefaultedRuleByBizCodeLock(bizCode, Constants.PLATFORM_GROUP_ID);
		if (codeRule == null) {
			throw new CodeException(CodeErrorCode.DATE_NOT_EXIST_ERROR_CODE, "业务编码对应的默认编码规则不存在");
		}

		// 获取到锁后，判断缓存是否已经存在，若存在直接返回,避免并发阻塞重复初始化
		String redisTemplateCacheKey = Constants.PLATFORM_CODE_TEMPLATE_REDIS_PREFIX + bizCode;
		final String cacheTemplate = jedisClient.get(redisTemplateCacheKey);
		if (!StringUtils.isEmpty(cacheTemplate)) {
			return null;
		}

		final List<CodeItemBean> codeItems = codeItemMapper.queryItemsByRuleId(codeRule.getId());
		redisCodeCache.initCache(codeRule, codeItems, null,CacheLevelEnum.PLATFORM);
	}
}
