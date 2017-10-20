package com.hivescm.code.controller;

import com.hivescm.code.common.Constants;
import com.hivescm.code.controller.doc.IBizEntityDoc;
import com.hivescm.code.dto.BizTypeDetailDto;
import com.hivescm.code.dto.BizTypeInfoDto;
import com.hivescm.code.dto.BizTypeQueryDto;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.common.domain.DataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <b>Description:</b><br>
 * 业务实体控制器 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.controller
 * <br><b>Date:</b> 2017/10/19 19:59
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@RestController
public class BizEntityController implements IBizEntityDoc{
	private static final Logger LOGGER = LoggerFactory.getLogger(BizEntityController.class);


	@Override
	public DataResult<Boolean> addBizEntity(BizTypeDetailDto reqParam) {
		LOGGER.info("add code rule request,param:{}.", reqParam);
		try {
			// TODO

			return DataResult.success(Boolean.TRUE, Boolean.class);
		} catch (CodeException ce) {
			LOGGER.error("add code rule failed,param:" + reqParam, ce);
			return DataResult.faild(ce.getErrorCode(), ce.getMessage());
		} catch (Exception ex) {
			LOGGER.error("add code rule error,param:" + reqParam, ex);
			return DataResult.faild(CodeErrorCode.CODE_SYSTEM_ERROR_CODE, Constants.CODE_SERVICE_ERROR);
		}
	}

	@Override
	public DataResult<List<BizTypeInfoDto>> queryAllAavailableBizEntities(BizTypeQueryDto queryParam) {
		return null;
	}
}
