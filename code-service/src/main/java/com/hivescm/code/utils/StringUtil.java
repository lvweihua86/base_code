package com.hivescm.code.utils;

/**
 * <b>Description:</b><br>
 * 字符串工具类 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.controller.doc
 * <br><b>Date:</b> 2017/10/12 17:39
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class StringUtil {
	/**
	 * 验证字符串是否为空或空串
	 * @param str 待验证字符串
	 * @return <code>true</code> 空串;<code>fasle</code> 非空;
	 */
	public static boolean emptyString(String str) {
		return null == str || str.isEmpty();
	}
}
