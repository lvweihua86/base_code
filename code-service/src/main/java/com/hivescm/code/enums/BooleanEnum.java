package com.hivescm.code.enums;

import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;

/**
 * <b>Description:</b><BR>
 * 默认枚举 <br>
 *
 * @author DongChunfu
 * @version 1.0
 * @Note<br> <b>ProjectName:</b> base-org-permission
 * <br><b>PackageName:</b> com.hivescm.org.enums
 * <br><b>Date:</b> 2017/9/15 09:05
 * @since JDK 1.8
 */
public enum BooleanEnum {
	FALSE(0), TRUE(1);

	private int truth;

	BooleanEnum(int truth) {
		this.truth = truth;
	}

	public int getTruth() {
		return truth;
	}

	public static final BooleanEnum getTruth(Integer truth) {
		if (truth == null) {
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "真假值错误");
		}

		for (BooleanEnum booleanEnum : BooleanEnum.values()) {
			if (booleanEnum.getTruth() == truth) {
				return booleanEnum;
			}
		}
		throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "真假值错误");
	}
}
