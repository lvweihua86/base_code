package com.hivescm.code.dto;

import java.io.Serializable;

public class CodeItem implements Serializable{

	private static final long serialVersionUID = 4301203973242845410L;

	private Long id;

    private Integer codeType;

    private String codeColumn;

    private Integer codeLongth;

    private Integer sequence;

    private Integer isSerial;

    private Integer serialType;

    private Integer fillPattern;

    private String fillStr;

    private Long codeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public String getCodeColumn() {
        return codeColumn;
    }

    public void setCodeColumn(String codeColumn) {
        this.codeColumn = codeColumn == null ? null : codeColumn.trim();
    }

    public Integer getCodeLongth() {
        return codeLongth;
    }

    public void setCodeLongth(Integer codeLongth) {
        this.codeLongth = codeLongth;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getIsSerial() {
        return isSerial;
    }

    public void setIsSerial(Integer isSerial) {
        this.isSerial = isSerial;
    }

    public Integer getSerialType() {
        return serialType;
    }

    public void setSerialType(Integer serialType) {
        this.serialType = serialType;
    }

    public Integer getFillPattern() {
        return fillPattern;
    }

    public void setFillPattern(Integer fillPattern) {
        this.fillPattern = fillPattern;
    }

    public String getFillStr() {
        return fillStr;
    }

    public void setFillStr(String fillStr) {
        this.fillStr = fillStr == null ? null : fillStr.trim();
    }

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    @Override
    public String toString() {
        return "CodeIDItem{" +
                "id=" + id +
                ", codeType=" + codeType +
                ", codeColumn='" + codeColumn + '\'' +
                ", codeLongth=" + codeLongth +
                ", sequence=" + sequence +
                ", isSerial=" + isSerial +
                ", serialType=" + serialType +
                ", fillPattern=" + fillPattern +
                ", fillStr='" + fillStr + '\'' +
                ", codeId=" + codeId +
                '}';
    }
}