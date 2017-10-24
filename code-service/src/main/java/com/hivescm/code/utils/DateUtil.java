package com.hivescm.code.utils;

import com.hivescm.code.enums.DateFormateEnum;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	 *
	 * @return
	 */
	public static long currentSecond() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 格式化时间
	 *
	 * @param date    时间
	 * @param formate 格式
	 * @return 指定格式的时间字符
	 */
	public static final String formateDate(Date date, String formate) {
		if (date == null) {
			return null;
		}

		// 校验时间格式
		DateFormateEnum.getDateFormateEnum(formate);

		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		final String formatDate = sdf.format(date);
		return formatDate;
	}
}
