package com.hivescm.codeid.mapper;

import com.hivescm.codeid.pojo.CodeIDItem;
import com.mogujie.trade.db.DataSourceRouting;


import java.util.List;

@DataSourceRouting(dataSource="generated",isReadWriteSplitting=false, table = "base_code_id_item")
public interface CodeIDItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CodeIDItem record);

    int insertSelective(CodeIDItem record);

    CodeIDItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CodeIDItem record);

    int updateByPrimaryKey(CodeIDItem record);

    List<CodeIDItem> getCodeIDItemsByCodeId(long codeId);

    List<CodeIDItem> getFlowColumnByCodeId(long codeId);

    CodeIDItem selectByCodeId(Long codeId);

    int geFlowTypeByCodeId(Long codeId);
    
    int getFlowLengthByCodeId(Long codeId);
    
}