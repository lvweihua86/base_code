package com.hivescm.code.exception;

/**
 * <b>Description:</b><br>
 * 编码服务错误码 <br><br>
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
public class CodeErrorCode {

	/**
	 * 编码系统错误
	 */
	public static final int CODE_SYSTEM_ERROR_CODE = 9999;

	// 请求参数错误
	public static final int REQ_PARAM_ERROR_CODE = 7000;


	// 编码规则不存在
	public static final int CODE_RULE_NOT_EXIST_ERROR_CODE = 2001;
	// 流水号生成错误
	public static final int GENERATE_FLOW_CODE_ERROR_CODE = 2002;
	// 无法获取流水号
	public static final int FETCH_FLOW_CODE_ERROR_CODE = 2003;
	// 序列号流水已经到最大值，无法再生成
	public static final int FLOW_CODE_OVERFLOW_ERROR_CODE = 2004;

}
