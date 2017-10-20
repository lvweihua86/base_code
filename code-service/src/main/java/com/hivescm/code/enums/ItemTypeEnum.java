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
public enum ItemTypeEnum {

	CONSTANT(1), STRING(2), TIME(3), SERIAL(4);

	private int type;

	ItemTypeEnum(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static final ItemTypeEnum getItemTypeEnum(Integer type) {
		if (NumberUtil.nullOrlessThanOrEqualToZero(type)) {
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "编码段类型不存在");
		}
		for (ItemTypeEnum itemTypeEnum : ItemTypeEnum.values()) {
			if (itemTypeEnum.getType() == type) {
				return itemTypeEnum;
			}
		}
		throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "编码段类型不存在");
	}
}
