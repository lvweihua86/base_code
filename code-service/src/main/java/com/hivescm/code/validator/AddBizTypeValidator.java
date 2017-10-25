package com.hivescm.code.validator;

import com.hivescm.code.dto.BizTypeDto;
import com.hivescm.code.dto.BizTypeMetadataDto;
import com.hivescm.code.enums.LevelEnum;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <b>Description:</b><br>
 * 新增编码业务类型请求参数校验器 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.validator
 * <br><b>Date:</b> 2017/10/20 09:52
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@Component(value = "addBizTypeValidator")
public class AddBizTypeValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddBizTypeValidator.class);

	public void validate(BizTypeDto reqParam) {
		if (reqParam == null) {
			LOGGER.warn("add biz type req param is null.");
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增业务类型请求参数为空");
		}

		final Integer typeLevel = reqParam.getTypeLevel();
		try {
			LevelEnum.getLevel(typeLevel);
		} catch (CodeException ce) {
			LOGGER.warn("add biz type req ilegall param,[typeLevel]={}.", typeLevel);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增业务类型,非法请求参数【typeLevel】");
		}

		final String systemName = reqParam.getSystemName();
		if (StringUtils.isEmpty(systemName)) {
			LOGGER.warn("add biz type req ilegall param,[systemName]={}.", systemName);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增业务类型,非法请求参数【systemName】");
		}

		final String bizName = reqParam.getBizName();
		if (StringUtils.isEmpty(bizName)) {
			LOGGER.warn("add biz type req ilegall param,[bizName]={}.", bizName);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增业务类型,非法请求参数【bizName】");
		}

		final String bizCode = reqParam.getBizCode();
		if (StringUtils.isEmpty(bizCode)) {
			LOGGER.warn("add biz type req ilegall param,[bizCode]={}.", bizCode);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增业务类型,非法请求参数【bizCode】");
		}

		final String customPrefix = reqParam.getCustomPrefix();
		if (!StringUtils.isEmpty(customPrefix)) {
			if (customPrefix.length() > 10) {
				LOGGER.warn("add biz type req ilegall param,[customPrefix]={}.", customPrefix);
				throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增业务类型,非法请求参数【customPrefix】");
			}
		}

		final List<BizTypeMetadataDto> metadatas = reqParam.getMetadatas();
		bizTypeMeatadataValidate(metadatas);

	}

	/**
	 * 业务类型元数据校验
	 *
	 * @param metadatas 业务类型元数据
	 */
	public void bizTypeMeatadataValidate(final List<BizTypeMetadataDto> metadatas) {
		if (CollectionUtils.isEmpty(metadatas)) {
			return;
		}

		Set<String> metadataNames = filterMetaName(metadatas);
		if (metadataNames.size() < metadatas.size()) {
			LOGGER.warn("add biz type metadate req ilegall param,[metadataName]={}.", metadataNames);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增业务类型元数据,非法请求参数【metadataNames】");
		}

		for (BizTypeMetadataDto metadata : metadatas) {
			final String metadataName = metadata.getMetadataName();
			if (StringUtils.isEmpty(metadataName)) {
				LOGGER.warn("add biz type metadate req ilegall param,[metadataName]={}.", metadataName);
				throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增业务类型元数据,非法请求参数【metadataName】");
			}

			final String metadataShow = metadata.getMetadataShow();
			if (StringUtils.isEmpty(metadataShow)) {
				LOGGER.warn("add biz type metadate req ilegall param,[metadataShow]={}.", metadataShow);
				throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增业务类型元数据,非法请求参数【metadataShow】");
			}
		}
	}

	private Set<String> filterMetaName(final List<BizTypeMetadataDto> metadatas) {
		Set<String> metadataNames = new HashSet<>();
		for (BizTypeMetadataDto metadata : metadatas) {
			metadataNames.add(metadata.getMetadataName());
		}
		return metadataNames;
	}

}
