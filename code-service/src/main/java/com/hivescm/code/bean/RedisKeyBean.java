package com.hivescm.code.bean;

import java.io.Serializable;

/**
 * Description:
 * User: luca
 * Date: 2018-08-20
 * Time: 下午4:09
 * Company：蜂网供应链（上海）有限公司
 */
public class RedisKeyBean implements Serializable {


    private Long groupId;

    private String bizCode;

    private Long value;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
