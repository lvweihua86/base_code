package com.hivescm.codeid.error;

import com.hivescm.common.exception.SystemException;

/**
 * 系统公共状态码定义范围 :1001~2000
 * 
 * @author SHOUSHEN LUAN
 */
public enum ErrorCode {
	NO_RULE(2001, "该业务没有创建编码规则"),
	FLOWNUM_ERROR(2002, "流水号生成错误"),
	NO_FETCH_FLOWNUM(2003, "无法获取流水号"),
    MAX_FLOWNUM_NO_CREATE(2004, "序列号流水已经到最大值，无法再生成");

	private int errorCode;
	private String errorReason;

	public int getErrorCode() {
		return this.errorCode;
	}

	public String getErrorReason() {
		return this.errorReason;
	}

	private ErrorCode(int errorCode, String errorReason) {
		this.errorCode = errorCode;
		this.errorReason = errorReason;
	}

    public RuntimeException throwError() {
        throw new SystemException(this.getErrorCode(), this.getErrorReason());
    }

}
