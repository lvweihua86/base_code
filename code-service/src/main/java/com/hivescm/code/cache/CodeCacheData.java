package com.hivescm.code.cache;

/**
 * <b>Description:</b><br>
 * Redis 缓存实体 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.cache
 * <br><b>Date:</b> 2017/10/20 20:40
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class CodeCacheData {
	/**
	 * 是否有缓存
	 */
	private boolean hasCaceh;

	/**
	 * 编码模板
	 */
	private String codTemplate;

	/**
	 * 流水号缓存Key
	 */
	private String serialNumKey;

	/**
	 * 最大流水号Key
	 */
	private String maxSerialNumKey;

	public CodeCacheData() {
	}

	public boolean hasCaceh() {
		return hasCaceh;
	}

	public void setHasCaceh(boolean hasCaceh) {
		this.hasCaceh = hasCaceh;
	}

	public String getCodTemplate() {
		return codTemplate;
	}

	public void setCodTemplate(String codTemplate) {
		this.codTemplate = codTemplate;
	}

	public String getSerialNumKey() {
		return serialNumKey;
	}

	public void setSerialNumKey(String serialNumKey) {
		this.serialNumKey = serialNumKey;
	}

	public String getMaxSerialNumKey() {
		return maxSerialNumKey;
	}

	public void setMaxSerialNumKey(String maxSerialNumKey) {
		this.maxSerialNumKey = maxSerialNumKey;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("CodeCacheData{");
		sb.append("hasCaceh=").append(hasCaceh);
		sb.append(", codTemplate='").append(codTemplate).append('\'');
		sb.append(", serialNumKey='").append(serialNumKey).append('\'');
		sb.append(", maxSerialNumKey='").append(maxSerialNumKey).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
