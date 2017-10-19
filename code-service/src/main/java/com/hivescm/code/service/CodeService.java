package com.hivescm.code.service;

import com.hivescm.code.pojo.CodeIDItem;

import java.util.List;

/**
 * 编码id API
 */
public interface CodeIdService {

	List<CodeIDItem> getCodeIDItemsByCodeId(long codeId) throws Exception;

	String getCodeIDTemplateFields(String businessCode, String orgId,
			List<CodeIDItem> list);

	String generateID(String businessCode, String orgId, String json) throws
			Exception;

	String digitFillZero(String value, int length) throws Exception;

	String letterDigitIncr(String k1, String k2, int length) throws Exception;

	void initCodeIDTemplate();
}
