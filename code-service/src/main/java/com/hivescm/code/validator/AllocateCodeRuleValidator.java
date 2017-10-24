package com.hivescm.code.validator;

import com.hivescm.code.dto.AllocateCodeRuleDto;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.utils.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <b>Description:</b><br>
 * 分配编码规则请求参数校验器 <br><br>
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
@Component(value = "allocateCodeRuleValidator")
public class AllocateCodeRuleValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(AllocateCodeRuleValidator.class);

	public void validate(AllocateCodeRuleDto reqParam) {
		if (reqParam == null) {
			LOGGER.warn("allocate code rule req param is null.");
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "分配编码规则请求参数为空");
		}

		final Integer userId = reqParam.getUserId();
		if (NumberUtil.nullOrlessThanOrEqualToZero(userId)) {
			LOGGER.warn("allocate code rule req ilegall param,[userId]={}.", userId);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "分配编码规则,非法请求参数【userId】");
		}

		final Integer groupId = reqParam.getGroupId();
		if (NumberUtil.nullOrlessThanOrEqualToZero(groupId)) {
			LOGGER.warn("allocate code rule req ilegall param,[groupId]={}.", groupId);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "分配编码规则,非法请求参数【groupId】");
		}

		final Integer orgId = reqParam.getOrgId();
		if (NumberUtil.nullOrlessThanOrEqualToZero(orgId)) {
			LOGGER.warn("allocate code rule req ilegall param,[orgId]={}.", orgId);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "分配编码规则,非法请求参数【orgId】");
		}

		final Integer ruleId = reqParam.getRuleId();
		if (NumberUtil.nullOrlessThanOrEqualToZero(ruleId)) {
			LOGGER.warn("allocate code rule req ilegall param,[ruleId]={}.", ruleId);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "分配编码规则,非法请求参数【ruleId】");
		}

	}
}
