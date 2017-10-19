package com.hivescm.codeid.service;

import com.hivescm.codeid.pojo.CodeId;
import com.hivescm.codeid.pojo.VO;

import java.util.List;

public interface CodeIDService {

    Long addCodeId(CodeId codeId);

    List<VO> getCodeTypes();

    List<VO> getNoSerialTypes();

    List<VO> getStringSerialTypes();

    List<VO> getDateSerialTypes();

    String stopCodeID(long codeId);

    String reusedCodeID(long codeId);

    String deleteCodeID(long id);

    CodeId selectByPrimaryKey(Long id);

    List<String> getZeroReason(String orgId,String businessId);

    int updateByPrimaryKey(CodeId record);
}
