package com.hivescm.code.enums;

/**
 * <b>Description:</b><br>
 * 缓存级别枚举 <br><br>
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
public enum CacheLevelEnum {
	NEW(0), PLATFORM(1), GROUP(2), BIZ_UNIT(3);

	private int level;

	CacheLevelEnum(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}
