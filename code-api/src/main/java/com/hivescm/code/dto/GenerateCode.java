package com.hivescm.code.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Description:</b><br>
 * 生成编码请求参数 <br>
 * 注：
 * 1：此生成编码请求参数涵盖所有编码生成方式所需要的数据，对于选填字段是为应对客户设置的各种编码规则的统一形式；
 * 2：各调用方可根据编码规则的具体使用情况，进行相关处理，编码规则不做硬性要求，当编码所需数据不全时给以错误提示<br>
 *
 * @author DongChunfu
 */
public class GenerateCode implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 集团ID（选填）（平台为1）
	 */
	private Integer groupId;
	/**
	 * 组织ID（选填）
	 */
	private Integer orgId;
	/**
	 * 业务编码（必填）
	 */
	private String bizCode;
	/**
	 * 业务实体可能参与编码的字符类型域
	 * 业务实体列表存储的 field_name 为必填，
	 * 当取对应值时返回指定错误码，客户端自行处理；
	 */
	private Map<String, String> bizAttr = new HashMap<>(10, 1);
	/**
	 * 业务时间，时间类型的编码规则使用
	 * 业务实体列表存储的 field_name 为必填，
	 * 当取对应值时返回指定错误码，客户端自行处理；
	 */
	private Map<String, Date> bizTime = new HashMap<>(5, 1);

	public GenerateCode() {
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

	public Map<String, String> getBizAttr() {
		return bizAttr;
	}

	public void setBizAttr(Map<String, String> bizAttr) {
		this.bizAttr = bizAttr;
	}

	public Map<String, Date> getBizTime() {
		return bizTime;
	}

	public void setBizTime(Map<String, Date> bizTime) {
		this.bizTime = bizTime;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("GenerateCode{");
		sb.append("groupId=").append(groupId);
		sb.append(", orgId=").append(orgId);
		sb.append(", bizCode='").append(bizCode).append('\'');
		sb.append(", bizAttr=").append(bizAttr);
		sb.append(", bizTime=").append(bizTime);
		sb.append('}');
		return sb.toString();
	}
}
