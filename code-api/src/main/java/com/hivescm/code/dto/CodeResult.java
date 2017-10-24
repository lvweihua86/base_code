package com.hivescm.code.dto;

import java.io.Serializable;

/**
 * <b>Description:</b><br>
 * 编码结果 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.dto
 * <br><b>Date:</b> 2017/10/19 16:21
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class CodeResult implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 生成编码
	 */
	private String code;
	/**
	 * 规则ID
	 */
	private Integer ruleId;

	public CodeResult() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("CodeResult{");
		sb.append("code='").append(code).append('\'');
		sb.append(", ruleId=").append(ruleId);
		sb.append('}');
		return sb.toString();
	}
}
