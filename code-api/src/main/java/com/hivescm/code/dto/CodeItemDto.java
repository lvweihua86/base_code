package com.hivescm.code.dto;

import java.io.Serializable;

public class CodeItemDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 段顺序（1~5段，大于零，底层再排序）（必填）
	 */
	private Integer orderNum;
	/**
	 * 段类型（必填）
	 * 1常量;
	 * 2字符串;
	 * 3时间类型;
	 * 4流水号
	 */
	private Integer itemType;
	/**
	 * 段值
	 * 1 常量 ，常量值
	 * 2 字符 ，{@link BizTypeMetadataDto#metadataName}
	 * 3 时间 ，{@link BizTypeMetadataDto#metadataName}
	 * 4 流水 ，可空
	 */
	private String itemValue;
	/**
	 * 段长度，若超长保左截右
	 */
	private Integer itemLength;
	/**
	 * 流水依据（不填）
	 * 0 否
	 * 1 是
	 */
	private Integer serial = 0;
	/**
	 * 流水类型（不填）
	 * 0:不流水;
	 * 1:按日流水;
	 * 2:按月流水;
	 * 3:按年流水;
	 * 4:按字符串流水;
	 */
	private Integer serialType = 0;
	/**
	 * 补位方式（不填）
	 * {@link com.hivescm.code.enums.CoverWayEnum}
	 * 1左补位；
	 * 2右补位;
	 * 3不补位。
	 */
	private Integer coverWay = 1;
	/**
	 * 补位字符
	 */
	private Character coverChar = '0';

	public CodeItemDto() {
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public Integer getItemLength() {
		return itemLength;
	}

	public void setItemLength(Integer itemLength) {
		this.itemLength = itemLength;
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

	public Character getCoverChar() {
		return coverChar;
	}

	public void setCoverChar(Character coverChar) {
		this.coverChar = coverChar;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("CodeItemDto{");
		sb.append("orderNum=").append(orderNum);
		sb.append(", itemType=").append(itemType);
		sb.append(", itemValue='").append(itemValue).append('\'');
		sb.append(", itemLength=").append(itemLength);
		sb.append(", serial=").append(serial);
		sb.append(", serialType=").append(serialType);
		sb.append(", coverWay=").append(coverWay);
		sb.append(", coverChar=").append(coverChar);
		sb.append('}');
		return sb.toString();
	}
}