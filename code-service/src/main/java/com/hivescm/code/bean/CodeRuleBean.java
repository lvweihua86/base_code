package com.hivescm.code.dto;

import java.io.Serializable;

public class CodeRule implements Serializable{

	private static final long serialVersionUID = -2109831853530494617L;

	private Long id;

    private String ruleCode;

    private String ruleName;

    private String businessCode;

    private Integer codeType;

    private Integer isBreakCode;

    private Integer zeroReason;

    private String orgId;

    private Integer isDefault;

    private Integer isUse;

    private String timeFormat;

    private Integer isEdit;

    private Integer isFlow;

    private Integer sumLongth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public Integer getIsBreakCode() {
        return isBreakCode;
    }

    public void setIsBreakCode(Integer isBreakCode) {
        this.isBreakCode = isBreakCode;
    }

    public Integer getZeroReason() {
        return zeroReason;
    }

    public void setZeroReason(Integer zeroReason) {
        this.zeroReason = zeroReason;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public Integer getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Integer isEdit) {
        this.isEdit = isEdit;
    }

    public Integer getIsFlow() {
        return isFlow;
    }

    public void setIsFlow(Integer isFlow) {
        this.isFlow = isFlow;
    }

    public Integer getSumLongth() {
        return sumLongth;
    }

    public void setSumLongth(Integer sumLongth) {
        this.sumLongth = sumLongth;
    }

    @Override
    public String toString() {
        return "CodeId{" +
                "id=" + id +
                ", ruleCode='" + ruleCode + '\'' +
                ", ruleName='" + ruleName + '\'' +
                ", businessCode='" + businessCode + '\'' +
                ", codeType=" + codeType +
                ", isBreakCode=" + isBreakCode +
                ", zeroReason=" + zeroReason +
                ", orgId='" + orgId + '\'' +
                ", isDefault=" + isDefault +
                ", isUse=" + isUse +
                ", timeFormat='" + timeFormat + '\'' +
                ", isEdit=" + isEdit +
                ", isFlow=" + isFlow +
                ", sumLongth=" + sumLongth +
                '}';
    }
}