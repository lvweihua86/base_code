package com.hivescm.code.validator;

import com.hivescm.code.dto.CodeItemDto;
import com.hivescm.code.dto.CodeRuleDto;
import com.hivescm.code.enums.ItemTypeEnum;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.utils.NumberUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <b>Description:</b><br>
 * 新增编码规则请求参数校验器 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.validator
 * <br><b>Date:</b> 2017/10/13 20:00
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@Component(value = "addCodeRuleValidator")
public class AddCodeRuleValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddCodeRuleValidator.class);

	public void validate(CodeRuleDto reqParam) {
		if (reqParam == null) {
			LOGGER.warn("add code rule req param is null.");
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增编码规则请求参数为空");
		}

		final Integer groupId = reqParam.getGroupId();
		if (NumberUtil.nullOrlessThanOrEqualToZero(groupId)) {
			LOGGER.warn("add code rule req ilegall param,[groupId]={}.", groupId);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增编码规则,非法请求参数【groupId】");
		}

		final String bizCode = reqParam.getBizCode();
		if (StringUtils.isEmpty(bizCode)) {
			LOGGER.warn("add code rule req ilegall param,[bizCode]={}.", bizCode);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增编码规则,非法请求参数【bizCode】");
		}

		final String ruleName = reqParam.getRuleName();
		if (StringUtils.isEmpty(ruleName)) {
			LOGGER.warn("add code rule req ilegall param,[ruleName]={}.", ruleName);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增编码规则,非法请求参数【ruleName】");
		}

		codeItemValidator(reqParam);
	}

	/**
	 * 编码项请求参数校验器
	 *
	 * @param reqParam
	 */
	private void codeItemValidator(CodeRuleDto reqParam) {
		final List<CodeItemDto> codeItems = reqParam.getCodeItems();
		if (null == codeItems || codeItems.size() < 1 || codeItems.size() > 5) {
			LOGGER.warn("add code rule req ilegall param,[codeItems]={}.", codeItems);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增编码规则,非法请求参数【codeItems】");
		}

		int serialNumCount = 0;

		for (CodeItemDto codeItem : codeItems) {
			if (serialNumCount > 1) {
				LOGGER.warn("add code rule item req ilegall param,[codeItems]={}.", codeItems);
				throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增编码项,流水号类型有且只有一个");
			}

			final Integer itemType = codeItem.getItemType();
			ItemTypeEnum itemTypeEnum;
			try {
				itemTypeEnum = ItemTypeEnum.getItemTypeEnum(itemType);
			} catch (Exception e) {
				LOGGER.warn("add code rule item req ilegall param,[itemType]={}.", itemType);
				throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增编码项,非法请求参数【itemType】");
			}

			if (itemTypeEnum == ItemTypeEnum.SERIAL) {
				++serialNumCount;
			}

			final Integer orderNum = codeItem.getOrderNum();
			if (NumberUtil.nullOrlessThanOrEqualToZero(orderNum)) {
				LOGGER.warn("add code rule item req ilegall param,[orderNum]={}.", orderNum);
				throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增编码项,非法请求参数【orderNum】");
			}

			final String itemValue = codeItem.getItemValue();
			if (StringUtils.isEmpty(itemValue)) {
				LOGGER.warn("add code rule item req ilegall param,[itemValue]={}.", itemValue);
				throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增编码规则,非法请求参数【itemValue】");
			}

			final Integer itemLength = codeItem.getItemLength();
			if (NumberUtil.nullOrlessThanOrEqualToZero(itemLength) || itemLength > 10) {
				LOGGER.warn("add code rule item req ilegall param,[itemLength]={}.", itemLength);
				throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增编码项,非法请求参数【itemLength】");
			}
		}

		if (serialNumCount == 0) {
			LOGGER.warn("add code rule item req ilegall param,[codeItems]={}.", codeItems);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "新增编码项,流水号类型有且只有一个");
		}
	}

}
