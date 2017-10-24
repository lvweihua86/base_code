package com.hivescm.code.service;

import com.hivescm.code.dto.CodeResult;
import com.hivescm.code.dto.GenerateCode;

/**
 * <b>Description:</b><br>
 * 编码服务 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.controller.doc
 * <br><b>Date:</b> 2017/10/12 17:39
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public interface CodeService {

	/**
	 * 生成编码服务
	 *
	 * @param reqParam 生成编码请求参数
	 * @return 编码
	 */
	CodeResult generateCode(final GenerateCode reqParam);
}
