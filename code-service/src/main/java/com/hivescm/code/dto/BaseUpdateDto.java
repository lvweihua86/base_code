package com.hivescm.code.dto;

import java.io.Serializable;

/**
 * <b>Description:</b><br>
 * ${TODO}(请描述本类的作用) <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.dto
 * <br><b>Date:</b> 2017/10/20 11:19
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class BaseUpdateDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 创建人
	 */
	private Integer updateUser;

	public BaseUpdateDto() {
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BaseUpdateDto{");
		sb.append("updateUser=").append(updateUser);
		sb.append('}');
		return sb.toString();
	}
}
