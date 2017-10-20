package com.hivescm.code.dto;

/**
 * <b>Description:</b><br>
 * 业务实体简要信息 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.bean
 * <br><b>Date:</b> 2017/10/19 19:00
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class BizTypeInfoDto {
	private static final long serialVersionUID = 1L;
	/**
	 * 系统名称
	 */
	private String systemName;
	/**
	 * 业务名称
	 */
	private String bizName;
	/**
	 * 业务编码
	 */
	private String bizCode;

	public BizTypeInfoDto() {
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BizTypeBean{");
		sb.append(super.toString());
		sb.append(", systemName='").append(systemName).append('\'');
		sb.append(", bizName='").append(bizName).append('\'');
		sb.append(", bizCode='").append(bizCode).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
