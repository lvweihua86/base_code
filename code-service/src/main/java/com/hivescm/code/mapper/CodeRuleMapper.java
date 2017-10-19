package com.hivescm.code.mapper;

import com.hivescm.code.dto.CodeRule;
import com.mogujie.trade.db.DataSourceRouting;

import java.util.List;

@DataSourceRouting(dataSource="generated",isReadWriteSplitting=false, table = "base_code_id")
public interface CodeIdMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CodeRule record);

    int insertSelective(CodeRule record);

    List<CodeRule> getAllCodeID();

    CodeRule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CodeRule record);

    int updateByPrimaryKey(CodeRule record);

    CodeRule getCodeIdByBusinessIDAndOrgId(String orgId,String businessCode);

   String getTimeFormatStr(long id);

    int getzeroreasonByCodeId(Integer orgId, long codeId);

    CodeRule getDefaultCodeIdByBusinessID(String businessCode);

    List<CodeRule> getAllUsedCodeIds();

    CodeRule getCodeIDByCodeId(long codeId);

    int stopCodeID( long codeId);

    int reusedCodeID(long codeId);
}