package com.hivescm.code.validator;

import com.hivescm.code.dto.CodeRule;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <b>Description:</b><br>
 * 新增编码规则请求参数校验器 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.validator
 * <br><b>Date:</b> 2017/10/13 20:00
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@Component(value = "addCodeRuleValidator")
public class AddCodeRuleValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddCodeRuleValidator.class);

	public void validate(CodeRule reqParam) {
		if (reqParam == null) {
			LOGGER.warn("add biz unit req param is null.");
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增业务单元请求参数为空");
		}
	}
}
