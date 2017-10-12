package com.hivescm.codeid.pojo;

import java.io.Serializable;

public class BusinessModul implements Serializable{

	private static final long serialVersionUID = 6767406443083135753L;

	private Integer id;

    private String businessName;

    private Integer systemProjectId;

    private String businessCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }

    public Integer getSystemProjectId() {
        return systemProjectId;
    }

    public void setSystemProjectId(Integer systemProjectId) {
        this.systemProjectId = systemProjectId;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}