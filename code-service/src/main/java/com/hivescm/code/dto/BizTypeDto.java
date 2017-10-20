package com.hivescm.code.dto;

import java.util.List;

/**
 * <b>Description:</b><br>
 * 业务实体 <br><br>
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
public class BizTypeDto extends BaseCreateDto {
	private static final long serialVersionUID = 1L;
	/**
	 * 规则级别
	 * <li>1,平台</li>
	 * <li>2,通用</li>
	 */
	private Integer typeLevel;
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

	private List<BizTypeMetadataDto> metadatas;

	public BizTypeDto() {
	}

	public Integer getTypeLevel() {
		return typeLevel;
	}

	public void setTypeLevel(Integer typeLevel) {
		this.typeLevel = typeLevel;
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

	public List<BizTypeMetadataDto> getMetadatas() {
		return metadatas;
	}

	public void setMetadatas(List<BizTypeMetadataDto> metadatas) {
		this.metadatas = metadatas;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BizTypeDto{");
		sb.append(super.toString());
		sb.append(", typeLevel=").append(typeLevel);
		sb.append(", systemName='").append(systemName).append('\'');
		sb.append(", bizName='").append(bizName).append('\'');
		sb.append(", bizCode='").append(bizCode).append('\'');
		sb.append(", metadatas='").append(metadatas).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
