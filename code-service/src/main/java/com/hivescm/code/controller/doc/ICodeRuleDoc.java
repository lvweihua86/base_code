package com.hivescm.code.controller.doc;

import com.hivescm.code.dto.CodeRule;
import com.hivescm.common.domain.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
/**
 * <b>Description:</b><br>
 * 编码规则 <br><br>
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
@Api(value = "编码规则")
public interface ICodeRuleDoc {

	@ApiOperation(value = "新增编码规则", httpMethod = "POST")
	DataResult addCodeRule(@RequestBody CodeRule codeRule);
}
