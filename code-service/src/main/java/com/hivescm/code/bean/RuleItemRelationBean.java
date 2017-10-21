package com.hivescm.code.bean;

import java.io.Serializable;

/**
 * <b>Description:</b><br>
 * 规则与项关系数据实体 <br><br>
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
public class RuleItemRelationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 规则ID
	 */
	private Integer ruleId;
	/**
	 * 集团ID（全局为1）
	 */
	private Integer groupId;
	/**
	 * 业务实体
	 */
	private Integer orgId;
	/**
	 * 业务实体编码
	 */
	private String bizCode;
	/**
	 * 默认
	 * <li>0,否</li>
	 * <li>1,是</li>
	 */
	private Integer defaulted;
	/**
	 * 缓存步长
	 */
	private Integer stepSize;
	/**
	 * 缓存次数
	 */
	private Integer stepNum;

	public RuleItemRelationBean() {
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Integer getDefaulted() {
		return defaulted;
	}

	public void setDefaulted(Integer defaulted) {
		this.defaulted = defaulted;
	}

	public Integer getStepSize() {
		return stepSize;
	}

	public void setStepSize(Integer stepSize) {
		this.stepSize = stepSize;
	}

	public Integer getStepNum() {
		return stepNum;
	}

	public void setStepNum(Integer stepNum) {
		this.stepNum = stepNum;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("RuleItemRelationBean{");
		sb.append(super.toString());
		sb.append(", ruleId=").append(ruleId);
		sb.append(", groupId=").append(groupId);
		sb.append(", orgId=").append(orgId);
		sb.append(", bizCode='").append(bizCode).append('\'');
		sb.append(", defaulted=").append(defaulted);
		sb.append(", stepSize=").append(stepSize);
		sb.append(", stepNum=").append(stepNum);
		sb.append('}');
		return sb.toString();
	}
}