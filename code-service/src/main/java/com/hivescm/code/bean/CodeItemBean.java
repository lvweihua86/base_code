package com.hivescm.code.bean;

import java.io.Serializable;

/**
 * <b>Description:</b><br>
 * 编码项实体数据 <br><br>
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
public class CodeItemBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编码规则ID
	 */
	private Long codeId;
	/**
	 * 顺序（5段）
	 */
	private Integer sequence;
	/**
	 * 段类型
	 * 1常量;
	 * 2字符串;
	 * 3时间类型;
	 * 4流水号
	 */
	private Integer sectionType;
	/**
	 * 段值
	 */
	private String sectionValue;
	/**
	 * 段长度
	 */
	private Integer sectionLength;
	/**
	 * 流水依据
	 * 0 否
	 * 1 是
	 */
	private Integer serial;
	/**
	 * 流水依据
	 * 0:不流水;
	 * 1:按日流水;
	 * 2:按月流水;
	 * 3:按年流水;
	 * 4:按字符串流水;
	 */
	private Integer serialType;
	/**
	 * 补位方式
	 * 1左补位；
	 * 2右补位;
	 * 3不补位。
	 */
	private Integer coverWay;
	/**
	 * 补位字符
	 */
	private String coverChar;

	public CodeItemBean() {
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getSectionType() {
		return sectionType;
	}

	public void setSectionType(Integer sectionType) {
		this.sectionType = sectionType;
	}

	public String getSectionValue() {
		return sectionValue;
	}

	public void setSectionValue(String sectionValue) {
		this.sectionValue = sectionValue;
	}

	public Integer getSectionLength() {
		return sectionLength;
	}

	public void setSectionLength(Integer sectionLength) {
		this.sectionLength = sectionLength;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Integer getSerialType() {
		return serialType;
	}

	public void setSerialType(Integer serialType) {
		this.serialType = serialType;
	}

	public Integer getCoverWay() {
		return coverWay;
	}

	public void setCoverWay(Integer coverWay) {
		this.coverWay = coverWay;
	}

	public String getCoverChar() {
		return coverChar;
	}

	public void setCoverChar(String coverChar) {
		this.coverChar = coverChar;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("CodeItemBean{");
		sb.append("codeId=").append(codeId);
		sb.append(", sequence=").append(sequence);
		sb.append(", sectionType=").append(sectionType);
		sb.append(", sectionValue='").append(sectionValue).append('\'');
		sb.append(", sectionLength=").append(sectionLength);
		sb.append(", serial=").append(serial);
		sb.append(", serialType=").append(serialType);
		sb.append(", coverWay=").append(coverWay);
		sb.append(", coverChar='").append(coverChar).append('\'');
		sb.append('}');
		return sb.toString();
	}
}