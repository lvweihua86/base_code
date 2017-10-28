package com.hivescm.code.api;

import com.hivescm.code.dto.CodeResult;
import com.hivescm.code.dto.GenerateCode;
import com.hivescm.code.dto.RecycleCode;
import com.hivescm.common.domain.DataResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "code-service", path = "code-service")
public interface ICodeApi {

	/**
	 * 生成编码
	 *
	 * @param reqParam 生成编码请求参数
	 * @return 编码
	 */
	@RequestMapping(value = "/generateCode", method = RequestMethod.POST)
	DataResult<CodeResult> generateCode(@RequestBody GenerateCode reqParam);

	/**
	 * 回收编码
	 *
	 * @param reqParam 回收编码请求参数
	 * @return <code>true</code>回收成功
	 */
	@RequestMapping(value = "/recycleCode", method = RequestMethod.POST)
	DataResult<Boolean> recycleCode(@RequestBody RecycleCode reqParam);
}
