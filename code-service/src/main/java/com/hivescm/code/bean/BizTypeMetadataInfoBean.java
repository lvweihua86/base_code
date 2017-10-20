package com.hivescm.code.bean;

/**
 * <b>Description:</b><br>
 * 业务类型元数据简要信息 <br><br>
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
public class BizTypeMetadataInfoBean {
	private static final long serialVersionUID = 1L;
	/**
	 * 域名，用于传递
	 */
	private String metadataName;
	/**
	 * 域注释,用于展示
	 */
	private String metadataShow;


	public BizTypeMetadataInfoBean() {
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
		final StringBuilder sb = new StringBuilder("BizTypeMetadataInfoBean{");
		sb.append("metadataName='").append(metadataName).append('\'');
		sb.append(", metadataShow='").append(metadataShow).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
