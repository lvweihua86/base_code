package com.hivescm.code.enums;

import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;

/**
 * <b>Description:</b><br>
 * 时间格式枚举 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.enums
 * <br><b>Date:</b> 2017/10/23 11:41
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public enum DateFormateEnum {
	YYYY_MM_DD("yyyyMMdd");

	private String formate;

	DateFormateEnum(String formate) {
		this.formate = formate;
	}

	public String getFormate() {
		return formate;
	}

	public static final DateFormateEnum getDateFormateEnum(String formate) {
		for (DateFormateEnum dateFormateEnum : DateFormateEnum.values()) {
			if (dateFormateEnum.getFormate().equals(formate)) {
				return dateFormateEnum;
			}
		}

		throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "非法时间格式：【" + formate + "】");
	}
}
