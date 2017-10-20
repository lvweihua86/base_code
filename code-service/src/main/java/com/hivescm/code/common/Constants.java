package com.hivescm.code.common;

/**
 * <b>Description:</b><br>
 * 编码常量 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.bean
 * <br><b>Date:</b> 2017/10/19 17:17
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class Constants {

	public static final String CODE_SERVICE_ERROR = "编码系统错误";

	/**
	 * 平台级集团的ID
	 */
	public static final int PLATFORM_GROUP_ID = 1;

	/**
	 * 编码规则编码，业务编码
	 */
	public static final String CODE_RULE_CODE_BIZ_CODE = "BASE_CODE_RULE_CODE";

	/**
	 * 编码规则 Redis 缓存 Key prefix
	 * 完整模板 BASE:CODE:TEMPLATE:业务编码
	 */
	public static final String CODE_TEMPLATE_REDIS_PREFIX = "BASE:CODE:TEMPLATE:";

	/**
	 * 平台级 编码规则 Redis 缓存 Key prefix
	 * 完整模板 PREFIX:业务编码
	 */
	public static final String PLATFORM_CODE_TEMPLATE_REDIS_PREFIX = "BASE:CODE:PLATFORM:TEMPLATE:";
	/**
	 * 平台级 编码流水号 Redis 缓存 Key prefix
	 * 完整模板 BASE:CODE:TEMPLATE:业务编码
	 */
	public static final String PLATFORM_CODE_SERIAL_NUM_REDIS_PREFIX = "BASE:CODE:PLATFORM:SERIAL_NUM:";

	/**
	 * 平台级 编码最大流水号 Redis 缓存 Key prefix
	 * 完整模板 BASE:CODE:TEMPLATE:业务编码
	 */
	public static final String PLATFORM_CODE_MAX_SERIAL_NUM_REDIS_PREFIX = "BASE:CODE:PLATFORM:MAX_SERIAL_NUM:";

	/**
	 * 编码流水号 Redis 缓存 Key prefix
	 * 完整模板 BASE:CODE:TEMPLATE:业务编码
	 */
	public static final String CODE_SERIAL_NUM_REDIS_PREFIX = "BASE:CODE:SERIAL_NUM:";

	/**
	 * 编码最大流水号 Redis 缓存 Key prefix
	 * 完整模板 BASE:CODE:TEMPLATE:业务编码
	 */
	public static final String CODE_MAX_SERIAL_NUM_REDIS_PREFIX = "BASE:CODE:MAX_SERIAL_NUM:";

}
