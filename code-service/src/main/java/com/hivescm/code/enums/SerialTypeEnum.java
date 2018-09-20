package com.hivescm.code.enums;

import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.utils.NumberUtil;

/**
 * <b>Description:</b><br>
 * 编码段类型枚举 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.enums
 * <br><b>Date:</b> 2017/10/20 17:38
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public enum SerialTypeEnum {

	NOT_SERIAL(0),//不流水
	DAY_SERIAL(1),//日流水
	MONTH_SERIAL(2), //月流水
	YEAR_SERIAL(3),//年流水
	STRING_SERIAL(4);//字符串流水

	private int type;

	SerialTypeEnum(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static final SerialTypeEnum getItemTypeEnum(Integer type) {
		for (SerialTypeEnum itemTypeEnum : SerialTypeEnum.values()) {
			if (itemTypeEnum.getType() == type) {
				return itemTypeEnum;
			}
		}
		throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "编码段类型不存在");
	}
}
