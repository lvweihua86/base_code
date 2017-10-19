package com.hivescm.code.service.impl;

import com.hivescm.cache.client.JedisClient;
import com.hivescm.code.dto.CodeResult;
import com.hivescm.code.dto.GenerateCode;
import com.hivescm.code.mapper.CodeItemMapper;
import com.hivescm.code.mapper.CodeRuleMapper;
import com.hivescm.code.service.CodeService;
import com.hivescm.common.domain.DataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

	@Autowired
	private JedisClient jedisClient;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CodeResult generateCode(final GenerateCode reqParam) {
		return null;
	}

	@Override
	public void initCodeIDTemplate() {

	}
}

