package com.hivescm.code.service;

import com.hivescm.code.dto.CodeRule;
/**
 * <b>Description:</b><br>
 * 编码规则服务 <br><br>
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
public interface CodeRuleService {

	/**
	 * 新增编码规则
	 *
	 * @param codeRule 编码规则
	 * @return
	 */
	void addCodeRule(CodeRule codeRule);
}
