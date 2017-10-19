package com.hivescm.code.controller;

import com.hivescm.cache.client.JedisClient;
import com.hivescm.code.controller.doc.ICodeRuleDoc;
import com.hivescm.code.dto.CodeRule;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.service.CodeRuleService;
import com.hivescm.code.validator.AddCodeRuleValidator;
import com.hivescm.common.domain.DataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
/**
 * <b>Description:</b><br>
 * 编码规则控制器 <br><br>
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
@RestController
public class CodeRuleController implements ICodeRuleDoc {
	private static final Logger LOGGER = LoggerFactory.getLogger(CodeController.class);

	@Resource
	private AddCodeRuleValidator addCodeRuleValidator;

	@Resource
	private CodeRuleService codeRuleService;

	@Resource
	private CodeItemService codeItemService;

	@Autowired
	private JedisClient jedisClient;

	@Override
	@RequestMapping(value = "/addCodeRule", method = RequestMethod.POST)
	public DataResult<Boolean> addCodeRule(@RequestBody CodeRule reqParam) {
		LOGGER.info("add code rule request,param:{}.", reqParam);
		try {
			addCodeRuleValidator.validate(reqParam);

			codeRuleService.addCodeRule(reqParam);

			return DataResult.success(Boolean.TRUE, Boolean.class);
		} catch (CodeException ce) {
			LOGGER.error("add code rule error,param:" + reqParam, ce);
			return DataResult.faild(ce.getErrorCode(), ce.getMessage());
		} catch (Exception ex) {
			LOGGER.error("add code rule error,param:" + reqParam, ex);
			return DataResult.faild(CodeErrorCode.CODE_SERVICE_CODE, "编码系统错误");
		}
	}

}
