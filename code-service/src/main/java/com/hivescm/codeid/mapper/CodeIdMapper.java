package com.hivescm.codeid.mapper;

import com.hivescm.codeid.pojo.CodeId;
import com.mogujie.trade.db.DataSourceRouting;

import java.util.List;

@DataSourceRouting(dataSource="generated",isReadWriteSplitting=false, table = "base_code_id")
public interface CodeIdMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CodeId record);

    int insertSelective(CodeId record);

    List<CodeId> getAllCodeID();

    CodeId selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CodeId record);

    int updateByPrimaryKey(CodeId record);

    CodeId getCodeIdByBusinessIDAndOrgId(String orgId,String businessCode);

   String getTimeFormatStr(long id);

    int getzeroreasonByCodeId(String orgId, long codeId);

    CodeId getDefaultCodeIdByBusinessID(String businessCode);

    List<CodeId> getAllUsedCodeIds();
}