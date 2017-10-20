package com.hivescm.code.dto;

/**
 * <b>Description:</b><br>
 * 业务类型元数据 DTO <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.bean
 * <br><b>Date:</b> 2017/10/19 19:05
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class BizTypeMetadataDto extends BaseCreateDto {
	private static final long serialVersionUID = 1L;
	/**
	 * 类型ID
	 */
	private Integer typeId;
	/**
	 * 域名，用于传递
	 */
	private String metadataName;
	/**
	 * 域注释,用于展示
	 */
	private String metadataShow;

	public BizTypeMetadataDto() {
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getMetadataName() {
		return metadataName;
	}

	public void setMetadataName(String metadataName) {
		this.metadataName = metadataName;
	}

	public String getMetadataShow() {
		return metadataShow;
	}

	public void setMetadataShow(String metadataShow) {
		this.metadataShow = metadataShow;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BizTypeMetadataDto{");
		sb.append(super.toString());
		sb.append(", typeId=").append(typeId);
		sb.append(", metadataName='").append(metadataName).append('\'');
		sb.append(", metadataShow='").append(metadataShow).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
