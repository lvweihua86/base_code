package com.hivescm.code.enums;

/**
 * <b>Description:</b><br>
 * 剪切方式 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.enums
 * <br><b>Date:</b> 2017/10/24 16:40
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public enum CutWayEnum {
	// 切右面
	CUT_RIGHT(1),
	// 切左面
	CUT_LEFT(2);
	private int way;

	CutWayEnum(int way) {
		this.way = way;
	}
}
