package com.hivescm.code.controller;

import com.hivescm.code.common.Constants;
import com.hivescm.code.controller.doc.ICodeDoc;
import com.hivescm.code.dto.CodeResult;
import com.hivescm.code.dto.GenerateCode;
import com.hivescm.code.dto.RecycleCode;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.service.CodeService;
import com.hivescm.common.domain.DataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>Description:</b><br>
 * 编码控制器 <br><br>
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
@RestController
public class CodeController implements ICodeDoc {
	private static final Logger LOGGER = LoggerFactory.getLogger(CodeController.class);

	@Autowired
	private CodeService codeService;

	@Override
	@RequestMapping(value = "/generateCode", method = RequestMethod.POST)
	public DataResult<CodeResult> generateCode(@RequestBody GenerateCode reqParam) {
		LOGGER.info("generate code request,param:{}.", reqParam);
		try {
			CodeResult codeResult = codeService.generateCode(reqParam);
			LOGGER.info("generate code response,result:{},param:{}.", codeResult, reqParam);
			return DataResult.success(codeResult, CodeResult.class);
		} catch (CodeException ce) {
			LOGGER.error("generate code failed,param:" + reqParam, ce);
			return DataResult.faild(ce.getErrorCode(), ce.getMessage());
		} catch (Exception ex) {
			LOGGER.error("generate code error,param:" + reqParam, ex);
			return DataResult.faild(CodeErrorCode.CODE_SYSTEM_ERROR_CODE, Constants.CODE_SERVICE_ERROR);
		}
	}

	@RequestMapping(value = "/recycleCode", method = RequestMethod.POST)
	public DataResult<Boolean> recycleCode(@RequestBody RecycleCode reqParam) {
		LOGGER.info("recycle code request,param:{}.", reqParam);
		try {
			// TODO
			return DataResult.success(Boolean.TRUE, Boolean.class);
		} catch (CodeException ce) {
			LOGGER.error("recycle code failed,param:" + reqParam, ce);
			return DataResult.faild(ce.getErrorCode(), ce.getMessage());
		} catch (Exception ex) {
			LOGGER.error("recycle code error,param:" + reqParam, ex);
			return DataResult.faild(CodeErrorCode.CODE_SYSTEM_ERROR_CODE, Constants.CODE_SERVICE_ERROR);
		}

	}

	@Override
	@RequestMapping(value = "/initCodeTemplate", method = RequestMethod.GET)
	public DataResult<Boolean> initCodeTemplate() {
		LOGGER.info("init code template");
		try {
			// TODO
			return DataResult.success(Boolean.TRUE, Boolean.class);
		} catch (CodeException ce) {
			LOGGER.error("init code template failed", ce);
			return DataResult.faild(ce.getErrorCode(), ce.getMessage());
		} catch (Exception ex) {
			LOGGER.error("init code template error", ex);
			return DataResult.faild(CodeErrorCode.CODE_SYSTEM_ERROR_CODE, Constants.CODE_SERVICE_ERROR);
		}
	}
}
