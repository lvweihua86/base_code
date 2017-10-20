package com.hivescm.code.bean;

import java.io.Serializable;

/**
 * <b>Description:</b><br>
 * 编码规则实体数据 <br><br>
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
public class CodeRuleInfoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 规则级别
	 * <li>1,平台</li>
	 * <li>2,集团</li>
	 */
	private Integer level;
	/**
	 * 集团ID（平台为1）
	 */
	private Integer groupId;
	/**
	 * 业务编码
	 */
	private String bizCode;
	/**
	 * 规则编码
	 */
	private String ruleCode;
	/**
	 * 规则名称
	 */
	private String ruleName;
	/**
	 * 编码方式
	 * <li>1,保存前编码</li>
	 * <li>2,保存后编码</li>
	 */
	private Integer codeWay;
	/**
	 * 断码补码
	 * <li>0,否</li>
	 * <li>1,是</li>
	 */
	private Integer breakCode;
	/**
	 * 归零依据
	 * <li>1,平台</li>
	 * <li>2,集团</li>
	 * <li>3,组织</li>
	 */
	private Integer zeroReason;
	/**
	 * 默认
	 * <li>0,否</li>
	 * <li>1,是</li>
	 */
	private Integer defaulted;
	/**
	 * 可编辑
	 * <li>0,否</li>
	 * <li>1,是</li>
	 */
	private Integer editable;
	/**
	 * 补位
	 * <li>0,否</li>
	 * <li>1,是</li>
	 */
	private Integer cover;
	/**
	 * 时间格式
	 */
	private String timeFormat;
	/**
	 * 总长度
	 */
	private Integer totalLenght;

	public CodeRuleInfoBean() {
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getCodeWay() {
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
	}

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

	public Integer getEditable() {
		return editable;
	}

	public void setEditable(Integer editable) {
		this.editable = editable;
	}

	public Integer getCover() {
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
	}

	public Integer getTotalLenght() {
		return totalLenght;
	}

	public void setTotalLenght(Integer totalLenght) {
		this.totalLenght = totalLenght;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("CodeRuleBean{");
		sb.append(super.toString());
		sb.append(", level=").append(level);
		sb.append(", groupId=").append(groupId);
		sb.append(", bizCode='").append(bizCode).append('\'');
		sb.append(", ruleCode='").append(ruleCode).append('\'');
		sb.append(", ruleName='").append(ruleName).append('\'');
		sb.append(", codeWay=").append(codeWay);
		sb.append(", breakCode=").append(breakCode);
		sb.append(", zeroReason=").append(zeroReason);
		sb.append(", defaulted=").append(defaulted);
		sb.append(", editable=").append(editable);
		sb.append(", cover=").append(cover);
		sb.append(", timeFormat='").append(timeFormat).append('\'');
		sb.append(", totalLenght=").append(totalLenght);
		sb.append('}');
		return sb.toString();
	}
}