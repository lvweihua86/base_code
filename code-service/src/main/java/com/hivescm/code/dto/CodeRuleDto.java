package com.hivescm.code.dto;

import java.io.Serializable;
import java.util.List;

public class CodeRuleDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 规则级别（必填）
	 * <li>1,平台</li>
	 * <li>2,集团</li>
	 */
	private Integer ruleLevel;
	/**
	 * 集团ID（平台为1）（必填）
	 */
	private Integer groupId;
	/**
	 * 业务编码（必填）
	 */
	private String bizCode;
	/**
	 * 规则名称（必填）
	 */
	private String ruleName;
	/**
	 * 编码方式（选填）
	 * <li>1,保存前编码</li>
	 * <li>2,保存后编码</li>
	 */
	//private Integer codeWay = 1;
	/**
	 * 断码补码（选填）
	 * <li>0,否</li>
	 * <li>1,是</li>
	 */
	//private Integer breakCode = 0;
	/**
	 * 归零依据（选填）
	 * <li>1,平台</li>
	 * <li>2,集团</li>
	 * <li>3,组织</li>
	 */
	private Integer zeroReason = 1;
	/**
	 * 默认（选填）
	 * <li>0,否</li>
	 * <li>1,是</li>
	 */
	private Integer defaulted = 0;
	/**
	 * 补位（选填）
	 * <li>0,否</li>
	 * <li>1,是</li>
	 */
	//private Integer cover = 1;
	/**
	 * 时间格式（选填）
	 */
	//private String timeFormat = "yyyyMMdd";
	/**
	 * 编码项(1~5项)
	 */
	private List<CodeItemDto> codeItems;

	public CodeRuleDto() {
	}

	public Integer getRuleLevel() {
		return ruleLevel;
	}

	public void setRuleLevel(Integer ruleLevel) {
		this.ruleLevel = ruleLevel;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	/*public Integer getCodeWay() {
		return codeWay;
	}

	public void setCodeWay(Integer codeWay) {
		this.codeWay = codeWay;
	}

	public Integer getBreakCode() {
		return breakCode;
	}

	public void setBreakCode(Integer breakCode) {
		this.breakCode = breakCode;
	}*/

	public Integer getZeroReason() {
		return zeroReason;
	}

	public void setZeroReason(Integer zeroReason) {
		this.zeroReason = zeroReason;
	}

	public Integer getDefaulted() {
		return defaulted;
	}

	public void setDefaulted(Integer defaulted) {
		this.defaulted = defaulted;
	}

	/*public Integer getCover() {
		return cover;
	}

	public void setCover(Integer cover) {
		this.cover = cover;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}*/

	public List<CodeItemDto> getCodeItems() {
		return codeItems;
	}

	public void setCodeItems(List<CodeItemDto> codeItems) {
		this.codeItems = codeItems;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("CodeRuleDto{");
		sb.append("ruleLevel=").append(ruleLevel);
		sb.append(", groupId=").append(groupId);
		sb.append(", bizCode='").append(bizCode).append('\'');
		sb.append(", ruleName='").append(ruleName).append('\'');
		//sb.append(", codeWay=").append(codeWay);
		//sb.append(", breakCode=").append(breakCode);
		sb.append(", zeroReason=").append(zeroReason);
		sb.append(", defaulted=").append(defaulted);
		//sb.append(", cover=").append(cover);
		//sb.append(", timeFormat='").append(timeFormat).append('\'');
		sb.append(", codeItems=").append(codeItems);
		sb.append('}');
		return sb.toString();
	}
}