package com.hivescm.code.dto;

/**
 * <b>Description:</b><br>
 * 业务实体查询参数 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.bean
 * <br><b>Date:</b> 2017/10/19 19:00
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class BizTypeQueryDto {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID（必填）
	 */
	private Integer userId;
	/**
	 * 查询级别（必填）
	 * <p>
	 * <li>1,平台</li>
	 * <li>2,通用</li>
	 */
	private Integer typeLevel;
	/**
	 * 系统名字（选填）
	 */
	private String systemName;

	public BizTypeQueryDto() {
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTypeLevel() {
		return typeLevel;
	}

	public void setTypeLevel(Integer typeLevel) {
		this.typeLevel = typeLevel;
	}

	public String getSystemName() {
		return "%" + systemName + "%";
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BizTypeQueryDto{");
		sb.append("userId=").append(userId);
		sb.append(", typeLevel=").append(typeLevel);
		sb.append(", systemName='").append(systemName).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
