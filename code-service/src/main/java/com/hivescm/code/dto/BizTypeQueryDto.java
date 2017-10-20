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
	 * 查询级别
	 * <p>
	 * <li>1,全部</li>
	 * <li>2,平台</li>
	 * <li>3,通用</li>
	 */
	private Integer queryLevel;

	public BizTypeQueryDto() {
	}

	public Integer getQueryLevel() {
		return queryLevel;
	}

	public void setQueryLevel(Integer queryLevel) {
		this.queryLevel = queryLevel;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BizTypeQueryDto{");
		sb.append("queryLevel=").append(queryLevel);
		sb.append('}');
		return sb.toString();
	}
}
