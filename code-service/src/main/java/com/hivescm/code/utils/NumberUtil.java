package com.hivescm.code.utils;

/**
 * Created by DongChunfu on 2017/7/27
 * <p>
 * 字符串 工具类
 */
public class NumberUtil {
	/**
	 * 为空 或 小于零 或 等于零
	 *
	 * @param number 数字
	 * @return <code>true</code> 小于零,<code>false</code>大于零
	 */
	public static boolean nullOrlessThanOrEqualToZero(Number number) {
		return null == number || number.intValue() <= 0;
	}


}
