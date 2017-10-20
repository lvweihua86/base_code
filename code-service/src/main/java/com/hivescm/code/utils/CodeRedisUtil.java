package com.hivescm.code.utils;

import com.hivescm.code.common.Constants;

/**
 * <b>Description:</b><br>
 * 编码Redis 工具 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.utils
 * <br><b>Date:</b> 2017/10/20 15:41
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class CodeRedisUtil {
	/**
	 * 获取编码模板KEY
	 * @param bizCode 业务编码
	 * @return 编码模板KEY
	 */
	public static final String codeTemplatePrefix(String bizCode){
		return Constants.CODE_TEMPLATE_REDIS_PREFIX + bizCode;
	}

	/**
	 * 获取编码模板KEY
	 * @param bizCode 业务编码
	 * @return 编码模板KEY
	 */
	public static final String codeSerialNumPrefix(String bizCode){
		return Constants.CODE_SERIAL_NUM_REDIS_PREFIX + bizCode;
	}

	/**
	 * 获取编码模板KEY
	 * @param bizCode 业务编码
	 * @return 编码模板KEY
	 */
	public static final String codeMaxSerialNumPrefix(String bizCode){
		return Constants.CODE_MAX_SERIAL_NUM_REDIS_PREFIX + bizCode;
	}
}
