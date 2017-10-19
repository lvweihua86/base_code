package com.hivescm.code.controller.doc;

import com.hivescm.code.dto.CodeResult;
import com.hivescm.code.dto.GenerateCode;
import com.hivescm.code.dto.RecycleCode;
import com.hivescm.common.domain.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <b>Description:</b><br>
 * 编码 <br><br>
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
@Api(value = "编码")
public interface ICodeDoc {
	/**
	 * 生成编码
	 *
	 * @param reqParam 生成编码请求参数
	 * @return 编码
	 */
	@ApiOperation(value = "生成编码", httpMethod = "POST")
	DataResult<CodeResult> generateCode(@RequestBody GenerateCode reqParam);

	/**
	 * 回收编码
	 *
	 * @param reqParam 回收编码请求参数
	 * @return <code>true</code>回收成功
	 */
	@ApiOperation(value = "回收编码", httpMethod = "POST")
	DataResult<Boolean> recycleCode(@RequestBody RecycleCode reqParam);

	/**
	 * 初始化 Redis 缓存
	 *
	 * @return <code>true</code>回收成功
	 */
	@ApiOperation(value = "初始化", httpMethod = "GET")
	DataResult<Boolean> initCodeTemplate();
}
