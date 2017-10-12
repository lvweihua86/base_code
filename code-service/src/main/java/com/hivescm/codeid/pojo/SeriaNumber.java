package com.hivescm.codeid.pojo;

import java.io.Serializable;

public class SeriaNumber implements Serializable{

	private static final long serialVersionUID = 7220044913048121330L;

	private Long id;

    private Long codeId;

    private String flowBy1;

    private String flowBy2;

    private String currentMaxNum;

    private String orgId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public String getFlowBy1() {
        return flowBy1;
    }

    public void setFlowBy1(String flowBy1) {
        this.flowBy1 = flowBy1 == null ? null : flowBy1.trim();
    }

    public String getFlowBy2() {
        return flowBy2;
    }

    public void setFlowBy2(String flowBy2) {
        this.flowBy2 = flowBy2 == null ? null : flowBy2.trim();
    }

    public String getCurrentMaxNum() {
        return currentMaxNum;
    }

    public void setCurrentMaxNum(String currentMaxNum) {
        this.currentMaxNum = currentMaxNum == null ? null : currentMaxNum.trim();
    }


    public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setFlowIdentity(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    @Override
    public String toString() {
        return "SeriaNumber{" +
                "id=" + id +
                ", codeId=" + codeId +
                ", flowBy1='" + flowBy1 + '\'' +
                ", flowBy2='" + flowBy2 + '\'' +
                ", currentMaxNum='" + currentMaxNum + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }
}