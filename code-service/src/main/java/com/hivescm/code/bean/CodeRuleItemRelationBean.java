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
public class CodeRuleItemRelationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 规则级别
	 */
	private Long rule_id;
	/**
	 * 集团ID（全局为0）
	 */
	private Integer groupId;
	/**
	 * 业务实体
	 */
	private Integer buId;
	/**
	 * 规则编码
	 */
	private String bizEntity;

	public CodeRuleItemRelationBean() {
	}

	public Long getRule_id() {
		return rule_id;
	}

	public void setRule_id(Long rule_id) {
		this.rule_id = rule_id;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getBuId() {
		return buId;
	}

	public void setBuId(Integer buId) {
		this.buId = buId;
	}

	public String getBizEntity() {
		return bizEntity;
	}

	public void setBizEntity(String bizEntity) {
		this.bizEntity = bizEntity;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("CodeRuleItemRelationBean{");
		sb.append("rule_id=").append(rule_id);
		sb.append(", groupId=").append(groupId);
		sb.append(", buId=").append(buId);
		sb.append(", bizEntity='").append(bizEntity).append('\'');
		sb.append('}');
		return sb.toString();
	}
}