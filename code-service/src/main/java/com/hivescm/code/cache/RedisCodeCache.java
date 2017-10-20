package com.hivescm.code.cache;

import com.hivescm.cache.client.JedisClient;
import com.hivescm.code.bean.CodeItemBean;
import com.hivescm.code.bean.CodeRuleBean;
import com.hivescm.code.bean.RuleItemRelationBean;
import com.hivescm.code.common.Constants;
import com.hivescm.code.dto.GenerateCode;
import org.apache.commons.lang.StringUtils;
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
			final RuleItemRelationBean relation) {

	}

	/**
	 * 获取缓存数据
	 */
	public CodeCacheData getCacheData(final GenerateCode reqParam) {
		CodeCacheData cacheBean = new CodeCacheData();
		final String bizCode = reqParam.getBizCode();
		final Integer groupId = reqParam.getGroupId();
		if (Constants.PLATFORM_GROUP_ID == groupId) {// 平台级编码
			String redisTemplateCacheKey = Constants.PLATFORM_CODE_TEMPLATE_REDIS_PREFIX + bizCode;
			final String cacheTemplate = jedisClient.get(redisTemplateCacheKey);
			if (StringUtils.isEmpty(cacheTemplate)) {
				return cacheBean;
			}
			cacheBean.setHasCaceh(true);
			cacheBean.setSerialNumKey(Constants.PLATFORM_CODE_SERIAL_NUM_REDIS_PREFIX + bizCode);
			cacheBean.setMaxSerialNumKey(Constants.PLATFORM_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + bizCode);
			return cacheBean;
		}

		return cacheBean;
	}
}
