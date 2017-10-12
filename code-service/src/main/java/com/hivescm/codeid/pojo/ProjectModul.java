package com.hivescm.codeid.pojo;

import java.io.Serializable;

public class ProjectModul implements Serializable{

	private static final long serialVersionUID = 6908905361092348658L;

	private Integer id;

    private String projectName;

    private String projectCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
}