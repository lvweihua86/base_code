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
	 * 平台级 编码模板 Redis 缓存 Key prefix
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
	 * 集团级 编码模板 Redis 缓存 Key prefix
	 * 完整模板 PREFIX:集团ID:业务编码
	 */
	public static final String GROUP_CODE_TEMPLATE_REDIS_PREFIX = "BASE:CODE:GROUP:TEMPLATE:";

	/**
	 * 集团级 编码流水号 Redis 缓存 Key prefix
	 * 完整模板 BASE:CODE:TEMPLATE:集团ID:业务编码
	 */
	public static final String GROUP_CODE_SERIAL_NUM_REDIS_PREFIX = "BASE:CODE:GROUP:SERIAL_NUM:";

	/**
	 * 集团级 编码最大流水号 Redis 缓存 Key prefix
	 * 完整模板 BASE:CODE:TEMPLATE:集团ID:业务编码
	 */
	public static final String GROUP_CODE_MAX_SERIAL_NUM_REDIS_PREFIX = "BASE:CODE:GROUP:MAX_SERIAL_NUM:";

	/**
	 * 业务单元级 编码模板 Redis 缓存 Key prefix
	 * 完整模板 PREFIX:BU_ID:业务编码
	 */
	public static final String BIZ_UNIT_CODE_TEMPLATE_REDIS_PREFIX = "BASE:CODE:BIZ_UNIT:TEMPLATE:";

	/**
	 * 业务单元级 编码流水号 Redis 缓存 Key prefix
	 * 完整模板 BASE:CODE:TEMPLATE:BU_ID:业务编码
	 */
	public static final String BIZ_UNIT_CODE_SERIAL_NUM_REDIS_PREFIX = "BASE:CODE:BIZ_UNIT:SERIAL_NUM:";

	/**
	 * 业务单元级 编码最大流水号 Redis 缓存 Key prefix
	 * 完整模板 BASE:CODE:TEMPLATE:BU_ID:业务编码
	 */
	public static final String BIZ_UNIT_CODE_MAX_SERIAL_NUM_REDIS_PREFIX = "BASE:CODE:BIZ_UNIT:MAX_SERIAL_NUM:";

	/**
	 * Redis 缓存数据实际大小
	 */
	public static final int CACHE_SERIAL_NUM_DEFAULT_STEP_SIZE = 1000;

	/**
	 * Redis 缓存数据步数
	 */
	public static final int CACHE_SERIAL_NUM_DEFAULT_STEP_NUM = 1;

	/**
	 * Redis 缓存数据阈值
	 */
	public static final double CACHE_SERIAL_NUM_THRESHOLD_VALUE = 0.9;
}
