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
public class BaseCreateDto implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 创建人
	 */
	private Integer createUser;

	public BaseCreateDto() {
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BaseCreateDto{");
		sb.append("createUser=").append(createUser);
		sb.append('}');
		return sb.toString();
	}
}
