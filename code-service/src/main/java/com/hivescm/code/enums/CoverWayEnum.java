package com.hivescm.code.enums;

/**
 * <b>Description:</b><br>
 * 补位方式枚举 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.enums
 * <br><b>Date:</b> 2017/10/23 11:58
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public enum CoverWayEnum {
	LEFT(1), RIGHT(2), NO(3);
	private int way;

	CoverWayEnum(int way) {
		this.way = way;
	}

	public int getWay() {
		return way;
	}
}
