package com.hivescm.code.bean;

import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;

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
public class CodeItemBean extends BaseBean implements Serializable, Comparable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编码规则ID
	 */
	private Integer ruleId;
	/**
	 * 顺序（5段）
	 */
	private int orderNum;
	/**
	 * 项类型
	 * 1常量;
	 * 2字符串;
	 * 3时间类型;
	 * 4流水号
	 */
	private Integer itemType;
	/**
	 * 项值
	 */
	private String itemValue = "";
	/**
	 * 项长
	 */
	private Integer itemLength;
	/**
	 * 流水依据
	 * 0 否
	 * 1 是
	 */
	private Integer serial = 0;
	/**
	 * 流水依据
	 * 0:不流水;
	 * 1:按日流水;
	 * 2:按月流水;
	 * 3:按年流水;
	 * 4:按字符串流水;
	 */
	private Integer serialType = 0;
	/**
	 * 补位方式
	 * {@link com.hivescm.code.enums.CoverWayEnum}
	 */
	private Integer coverWay = 3;
	/**
	 * 补位字符
	 */
	private String coverChar = "0";

	public CodeItemBean() {
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
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

	public String getCoverChar() {
		return coverChar;
	}

	public void setCoverChar(String coverChar) {
		this.coverChar = coverChar;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("CodeItemBean{");
		sb.append("ruleId=").append(ruleId);
		sb.append(", orderNum=").append(orderNum);
		sb.append(", itemType=").append(itemType);
		sb.append(", itemValue='").append(itemValue).append('\'');
		sb.append(", itemLength=").append(itemLength);
		sb.append(", serial=").append(serial);
		sb.append(", serialType=").append(serialType);
		sb.append(", coverWay=").append(coverWay);
		sb.append(", coverChar='").append(coverChar).append('\'');
		sb.append('}');
		return sb.toString();
	}

	@Override
	public int compareTo(Object codeItemObject) {
		if (codeItemObject instanceof CodeItemBean) {
			final CodeItemBean codeItem = (CodeItemBean) codeItemObject;
			return this.getOrderNum() - codeItem.getOrderNum();
		}
		throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE,"非法操作");
	}
}