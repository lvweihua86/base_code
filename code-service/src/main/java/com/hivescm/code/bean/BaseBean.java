package com.hivescm.code.bean;

import java.io.Serializable;

/**
 * <b>Description:</b><br>
 * 数据实体基类 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.bean
 * <br><b>Date:</b> 2017/10/19 17:17
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 业务自增主键
	 */
	private Integer id;
	/**
	 * 数据状态
	 * 1 未启用；2启用；3停用；4删除
	 */
	private int state;
	/**
	 * 创建人
	 */
	private Integer createUser;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 更新人
	 */
	private Integer updateUser;
	/**
	 * 更新时间
	 */
	private Long updateTime;

	public BaseBean() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BaseBean2{");
		sb.append("id=").append(id);
		sb.append(", createUser=").append(createUser);
		sb.append(", createTime=").append(createTime);
		sb.append(", updateUser=").append(updateUser);
		sb.append(", updateTime=").append(updateTime);
		sb.append('}');
		return sb.toString();
	}
}
