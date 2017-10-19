package com.hivescm.code.dto;

import java.io.Serializable;

/**
 * <b>Description:</b><br>
 * 回收编码请求参数 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.dto
 * <br><b>Date:</b> 2017/10/12 17:41
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class RecycleCodeReq implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 编码规则ID
	 */
	private Long codeRuleId;

	/**
	 * 待回收编码
	 */
	private String code;

	public RecycleCodeReq() {
	}

}
