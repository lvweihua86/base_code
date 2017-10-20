package com.hivescm.code.dto;

import java.io.Serializable;

/**
 * <b>Description:</b><br>
 * 主键操作请求参数 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.dto
 * <br><b>Date:</b> 2017/10/20 14:10
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class KeyOperateDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 数据ID
	 */
	private Integer dataId;

	public KeyOperateDto() {
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("DeleteOperateDto{");
		sb.append("userId=").append(userId);
		sb.append(", dataId=").append(dataId);
		sb.append('}');
		return sb.toString();
	}
}
