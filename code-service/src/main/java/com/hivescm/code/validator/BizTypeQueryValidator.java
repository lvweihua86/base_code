package com.hivescm.code.validator;

import com.hivescm.code.dto.BizTypeQueryDto;
import com.hivescm.code.enums.LevelEnum;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.utils.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <b>Description:</b><br>
 * 业务类型查询请求参数校验器 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.validator
 * <br><b>Date:</b> 2017/10/20 10:08
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@Component(value = "bizTypeQueryValidator")
public class BizTypeQueryValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(BizTypeQueryValidator.class);

	public void validate(BizTypeQueryDto reqParam) {
		if (reqParam == null) {
			LOGGER.warn("query biz type req param is null.");
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "查询业务类型请求参数为空");
		}

		final Integer userId = reqParam.getUserId();
		if (NumberUtil.nullOrlessThanOrEqualToZero(userId)) {
			LOGGER.warn("query biz type req ilegall param,[userId]={}.", userId);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "查询业务类型，非法请求参数【userId】");
		}

		final Integer typeLevel = reqParam.getTypeLevel();
		try {
			LevelEnum.getLevel(typeLevel);
		} catch (CodeException ce) {
			LOGGER.warn("query biz type req ilegall param,[typeLevel]={}.", typeLevel);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "查询业务类型，非法请求参数【typeLevel】");
		}
	}
}
