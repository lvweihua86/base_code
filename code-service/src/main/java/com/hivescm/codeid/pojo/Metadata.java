package com.hivescm.codeid.pojo;

import java.io.Serializable;

public class Metadata implements Serializable{

	private static final long serialVersionUID = 6163865232734631959L;

	private Integer id;

    private String tableName;

    private String columnName;

    private Integer codeType;

    private String tableNameDesc;

    private String columnNameDesc;

    private Integer businessId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public String getTableNameDesc() {
        return tableNameDesc;
    }

    public void setTableNameDesc(String tableNameDesc) {
        this.tableNameDesc = tableNameDesc == null ? null : tableNameDesc.trim();
    }

    public String getColumnNameDesc() {
        return columnNameDesc;
    }

    public void setColumnNameDesc(String columnNameDesc) {
        this.columnNameDesc = columnNameDesc == null ? null : columnNameDesc.trim();
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }
}