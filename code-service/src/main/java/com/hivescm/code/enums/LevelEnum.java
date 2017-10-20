package com.hivescm.code.enums;

import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;

/**
 * <b>Description:</b><br>
 * 级别枚举 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.enums
 * <br><b>Date:</b> 2017/10/20 09:46
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public enum LevelEnum {
	PLATFORM(1), GROUP(2);

	private int level;

	LevelEnum(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public static final LevelEnum getLevel(Integer level) {
		if (level == null) {
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "级别不存在");
		}

		for (LevelEnum levelEnum : LevelEnum.values()) {
			if (levelEnum.getLevel() == level) {
				return levelEnum;
			}
		}
		throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "级别不存在");
	}
}
