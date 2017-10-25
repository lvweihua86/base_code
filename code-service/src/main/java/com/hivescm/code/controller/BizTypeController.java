package com.hivescm.code.controller;

import com.hivescm.code.bean.BizTypeInfoBean;
import com.hivescm.code.bean.BizTypeMetadataInfoBean;
import com.hivescm.code.common.Constants;
import com.hivescm.code.controller.doc.IBizTypeDoc;
import com.hivescm.code.dto.*;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.service.BizTypeService;
import com.hivescm.code.validator.AddBizTypeValidator;
import com.hivescm.code.validator.BizTypeQueryValidator;
import com.hivescm.code.validator.KeyOperateValidator;
import com.hivescm.common.domain.DataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class BizTypeController implements IBizTypeDoc {
	private static final Logger LOGGER = LoggerFactory.getLogger(BizTypeController.class);

	@Resource
	private AddBizTypeValidator addBizTypeValidator;

	@Resource
	private BizTypeQueryValidator bizTypeQueryValidator;

	@Resource
	private KeyOperateValidator keyOperateValidator;

	@Resource
	private BizTypeService bizTypeService;

	@Override
	public DataResult<Boolean> addBizType(@RequestBody BizTypeDto reqParam) {
		LOGGER.info("add biz type req , param:{}.", reqParam);
		try {
			addBizTypeValidator.validate(reqParam);

			bizTypeService.addBizType(reqParam);

			return DataResult.success(Boolean.TRUE, Boolean.class);
		} catch (CodeException ce) {
			LOGGER.error("add biz type failed,param:" + reqParam, ce);
			return DataResult.faild(ce.getErrorCode(), ce.getMessage());
		} catch (Exception ex) {
			LOGGER.error("add biz type error,param:" + reqParam, ex);
			return DataResult.faild(CodeErrorCode.CODE_SYSTEM_ERROR_CODE, Constants.CODE_SERVICE_ERROR);
		}
	}

	@Override
	public DataResult<List<BizTypeInfoDto>> queryAllBizTypes(@RequestBody BizTypeQueryDto reqParam) {
		LOGGER.info("query biz type req,aram:{}.", reqParam);
		try {
			bizTypeQueryValidator.validate(reqParam);

			final List<BizTypeInfoBean> bizTypeInfoBeans = bizTypeService.queryAllAavailableBizTypes(reqParam);

			return wrapBizTypeInfoDto(bizTypeInfoBeans);
		} catch (CodeException ce) {
			LOGGER.error("query biz type failed,param:" + reqParam, ce);
			return DataResult.faild(ce.getErrorCode(), ce.getMessage());
		} catch (Exception ex) {
			LOGGER.error("query biz type error,param:" + reqParam, ex);
			return DataResult.faild(CodeErrorCode.CODE_SYSTEM_ERROR_CODE, Constants.CODE_SERVICE_ERROR);
		}
	}

	@Override
	public DataResult<Boolean> deleteBizType(@RequestBody KeyOperateDto reqParam) {
		LOGGER.info("delete biz type req,aram:{}.", reqParam);
		try {
			keyOperateValidator.validate(reqParam);

			bizTypeService.deleteBizType(reqParam);

			return DataResult.success(Boolean.TRUE, Boolean.class);
		} catch (CodeException ce) {
			LOGGER.error("delete biz type failed,param:" + reqParam, ce);
			return DataResult.faild(ce.getErrorCode(), ce.getMessage());
		} catch (Exception ex) {
			LOGGER.error("delete biz type error,param:" + reqParam, ex);
			return DataResult.faild(CodeErrorCode.CODE_SYSTEM_ERROR_CODE, Constants.CODE_SERVICE_ERROR);
		}
	}

	@Override
	public DataResult<List<BizTypeMetadataInfoDto>> queryBizTypeMetadas(@RequestBody KeyOperateDto reqParam) {
		LOGGER.info("query biz type metadata req,aram:{}.", reqParam);
		try {
			keyOperateValidator.validate(reqParam);

			final List<BizTypeMetadataInfoBean> bizTypeMetadataInfoBeans = bizTypeService.queryBizTypeMetadas(reqParam);

			return wrapBizTypeMetadataInfoDto(bizTypeMetadataInfoBeans);
		} catch (CodeException ce) {
			LOGGER.error("query biz type metadata failed,param:" + reqParam, ce);
			return DataResult.faild(ce.getErrorCode(), ce.getMessage());
		} catch (Exception ex) {
			LOGGER.error("query biz type metadata error,param:" + reqParam, ex);
			return DataResult.faild(CodeErrorCode.CODE_SYSTEM_ERROR_CODE, Constants.CODE_SERVICE_ERROR);
		}
	}

	/**
	 * 封装业务类型查询结果集
	 *
	 * @param bizTypeInfoBeans 业务类型bean
	 * @return 业务类型 dto
	 */
	private DataResult<List<BizTypeInfoDto>> wrapBizTypeInfoDto(final List<BizTypeInfoBean> bizTypeInfoBeans) {
		DataResult<List<BizTypeInfoDto>> dataResult = new DataResult<>();
		if (!CollectionUtils.isEmpty(bizTypeInfoBeans)) {
			List<BizTypeInfoDto> bizTypeInfoDtos = new ArrayList<>(bizTypeInfoBeans.size());
			for (BizTypeInfoBean bizTypeInfoBean : bizTypeInfoBeans) {
				BizTypeInfoDto bizTypeInfoDto = new BizTypeInfoDto();
				BeanUtils.copyProperties(bizTypeInfoBean, bizTypeInfoDto);
				bizTypeInfoDtos.add(bizTypeInfoDto);
			}
			dataResult.setResult(bizTypeInfoDtos);
		}
		return dataResult;
	}

	/**
	 * 封装业务类型y元数据查询结果集
	 *
	 * @param bizTypeMetadataInfoBeans 业务类型元数据 bean
	 * @return 业务类型元数据 dto
	 */
	private DataResult<List<BizTypeMetadataInfoDto>> wrapBizTypeMetadataInfoDto(
			final List<BizTypeMetadataInfoBean> bizTypeMetadataInfoBeans) {
		DataResult<List<BizTypeMetadataInfoDto>> dataResult = new DataResult<>();
		if (!CollectionUtils.isEmpty(bizTypeMetadataInfoBeans)) {
			List<BizTypeMetadataInfoDto> bizTypeMetadataInfoDtos = new ArrayList<>(bizTypeMetadataInfoBeans.size());
			for (BizTypeMetadataInfoBean bizTypeMetadataInfoBean : bizTypeMetadataInfoBeans) {
				BizTypeMetadataInfoDto bizTypeMetadataInfoDto = new BizTypeMetadataInfoDto();
				BeanUtils.copyProperties(bizTypeMetadataInfoBean, bizTypeMetadataInfoDto);
				bizTypeMetadataInfoDtos.add(bizTypeMetadataInfoDto);
			}
			dataResult.setResult(bizTypeMetadataInfoDtos);
		}
		return dataResult;
	}
}
