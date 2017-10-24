package com.hivescm.code.utils;

import com.hivescm.code.enums.CoverWayEnum;
import com.hivescm.code.enums.CutWayEnum;

/**
 * <b>Description:</b><br>
 * 字符串工具类 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.utils
 * <br><b>Date:</b> 2017/10/23 11:54
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class StringUtils {
	/**
	 * 补位字符串
	 *
	 * @param expectLength 期望长度
	 * @param str          字符
	 * @return 处理后的字符串
	 */
	public static final String coverLength(Integer expectLength, CutWayEnum cutWayEnum, String str, String coverChar,
			CoverWayEnum coverWay) {
		final int strLength = str.length();
		if (strLength == expectLength) {
			return str;
		}

		if (strLength > expectLength) {
			if (cutWayEnum == CutWayEnum.CUT_LEFT) {
				return str.substring(strLength - expectLength, strLength);
			}
			if (cutWayEnum == CutWayEnum.CUT_RIGHT) {
				return str.substring(0, expectLength);
			}
			return str;
		}

		switch (coverWay) {
		case NO:
			return str;
		case RIGHT:
			String tempRightStr = str;
			for (int i = 0; i < expectLength - strLength; i++) {
				tempRightStr = tempRightStr + "0";
			}
			return tempRightStr;
		case LEFT:
			String tempLeftStr = str;
			for (int i = 0; i < expectLength - strLength; i++) {
				tempLeftStr = "0" + tempLeftStr;
			}
			return tempLeftStr;
		default:
			return null;

		}
	}

	/**
	 * 空字符
	 *
	 * @param str 字符
	 * @return <code>true</code> null or "";<code>false</code> 非空；
	 */
	public static final boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
}
