package com.hivescm.code.bean;

import java.io.Serializable;

/**
 * 编码规则实体数据
 */
public class CodeRuleBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 规则级别
	 */
	private Integer rule_level;
	/**
	 * 集团ID（全局为0）
	 */
	private Integer groupId;
	/**
	 * 业务实体
	 */
	private String bizEntity;
	/**
	 * 规则编码
	 */
	private String ruleCode;
	/**
	 * 规则名称
	 */
	private String ruleName;
	/**
	 * 状态
	 * 1 未用；
	 * 2 启用；
	 * 3 停用；
	 * 4 删除。
	 */
	private Integer state;
	/**
	 * 编码方式
	 */
	private Integer codeWay;
	/**
	 * 断码补码
	 * 0 否；
	 * 1 是
	 */
	private Integer breakCode;
	/**
	 * 归零依据（1全局；2集团；3组织）
	 * 编码达到最大值时使用
	 */
	private Integer zeroReason;
	/**
	 * 默认
	 * 0 否;
	 * 1 是。
	 */
	private Integer defaulted;
	/**
	 * 可编辑
	 * 0 否；
	 * 1 是。
	 */
	private Integer editable;
	/**
	 * 补位
	 * 0 否；
	 * 1 是。
	 */
	private Integer coverPosition;
	/**
	 * 时间格式
	 */
	private String timeFormat;
	/**
	 * 总长度
	 */
	private Integer totalLenght;

	public CodeRuleBean() {
	}

	public Integer getRule_level() {
		return rule_level;
	}

	public void setRule_level(Integer rule_level) {
		this.rule_level = rule_level;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getBizEntity() {
		return bizEntity;
	}

	public void setBizEntity(String bizEntity) {
		this.bizEntity = bizEntity;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public Integer getCoverPosition() {
		return coverPosition;
	}

	public void setCoverPosition(Integer coverPosition) {
		this.coverPosition = coverPosition;
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
		sb.append(", rule_level=").append(rule_level);
		sb.append(", groupId=").append(groupId);
		sb.append(", bizEntity='").append(bizEntity).append('\'');
		sb.append(", ruleCode='").append(ruleCode).append('\'');
		sb.append(", ruleName='").append(ruleName).append('\'');
		sb.append(", state=").append(state);
		sb.append(", codeWay=").append(codeWay);
		sb.append(", breakCode=").append(breakCode);
		sb.append(", zeroReason=").append(zeroReason);
		sb.append(", defaulted=").append(defaulted);
		sb.append(", editable=").append(editable);
		sb.append(", coverPosition=").append(coverPosition);
		sb.append(", timeFormat='").append(timeFormat).append('\'');
		sb.append(", totalLenght=").append(totalLenght);
		sb.append('}');
		return sb.toString();
	}
}