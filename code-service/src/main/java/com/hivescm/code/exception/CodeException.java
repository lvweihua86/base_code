package com.hivescm.code.exception;

/**
 * <b>Description:</b><br>
 * 编码异常 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.exception
 * <br><b>Date:</b> 2017/10/12 17:44
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class CodeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
	private Integer errorCode;

	public CodeException(Integer errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}
