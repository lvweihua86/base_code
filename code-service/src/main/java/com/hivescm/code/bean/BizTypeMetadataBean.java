package com.hivescm.code.bean;

/**
 * <b>Description:</b><br>
 * 业务实体域数据实体 <br><br>
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
public class BizTypeMetadataBean extends BaseBean {
	private static final long serialVersionUID = 1L;
	/**
	 * 实体ID
	 */
	private Integer entityId;
	/**
	 * 域名，传递
	 */
	private String fieldName;
	/**
	 * 域注释,展示
	 */
	private String fieldNote;

	public BizTypeMetadataBean() {
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldNote() {
		return fieldNote;
	}

	public void setFieldNote(String fieldNote) {
		this.fieldNote = fieldNote;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BizTypeMetadataBean{");
		sb.append(super.toString());
		sb.append(", entityId=").append(entityId);
		sb.append(", fieldName='").append(fieldName).append('\'');
		sb.append(", fieldNote='").append(fieldNote).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
