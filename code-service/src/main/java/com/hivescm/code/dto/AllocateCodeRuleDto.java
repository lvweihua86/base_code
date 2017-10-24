package com.hivescm.code.dto;

import java.io.Serializable;

/**
 * <b>Description:</b><br>
 * 分配编码规则请求参数 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.dto
 * <br><b>Date:</b> 2017/10/23 16:45
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class AllocateCodeRuleDto implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 规则ID
	 */
	private Integer ruleId;
	/**
	 * 集团ID
	 */
	private Integer groupId;
	/**
	 * 业务单元ID
	 */
	private Integer orgId;
	/**
	 * 业务编码
	 */
	private String bizCode;
	/**
	 * 是否默认
	 */
	private Integer defaulted;

	public AllocateCodeRuleDto() {
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("AllocateCodeRuleDto{");
		sb.append("userId=").append(userId);
		sb.append(", ruleId=").append(ruleId);
		sb.append(", groupId=").append(groupId);
		sb.append(", orgId=").append(orgId);
		sb.append(", bizCode=").append(bizCode);
		sb.append(", defaulted=").append(defaulted);
		sb.append('}');
		return sb.toString();
	}
}
