package com.hivescm.code.service.impl;

import com.hivescm.code.dto.CodeResult;
import com.hivescm.code.dto.GenerateCode;
import com.hivescm.code.mapper.CodeItemMapper;
import com.hivescm.code.mapper.CodeRuleMapper;
import com.hivescm.code.cache.CodeCacheData;
import com.hivescm.code.cache.RedisCodeCache;
import com.hivescm.code.service.CodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <b>Description:</b><br>
 * 编码服务为实现 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.bean
 * <br><b>Date:</b> 2017/10/19 17:17
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@Service("codeService")
public class CodeServiceImpl implements CodeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CodeServiceImpl.class);

	@Autowired
	private CodeRuleMapper codeRuleMapper;

	@Autowired
	private CodeItemMapper codeItemMapper;

	@Resource
	private RedisCodeCache redisCodeCache;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CodeResult generateCode(final GenerateCode reqParam) {

		final CodeCacheData cacheData = redisCodeCache.getCacheData(reqParam);
		if (cacheData.hasCaceh()) {
			return generateCodeByCache(cacheData);
		}

		return null;
	}

	private CodeResult generateCodeByCache(final CodeCacheData cacheData) {
		CodeResult codeResult = new CodeResult();
		return codeResult;
	}

	@Override
	public void initCodeIDTemplate() {

	}
}

