package com.hivescm.code.utils;

/**
 * <b>Description:</b><br>
 * 日期类工具 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.utils
 * <br><b>Date:</b> 2017/10/13 10:00
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class DateUtil {
	/**
	 * 获取当前时间（单位 秒 ）
	 * @return
	 */
	public static long currentSecond() {
		return System.currentTimeMillis() / 1000;
	}
}
