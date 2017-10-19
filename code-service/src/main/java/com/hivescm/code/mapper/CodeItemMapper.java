package com.hivescm.code.mapper;

import com.hivescm.code.dto.CodeItem;
import com.mogujie.trade.db.DataSourceRouting;


import java.util.List;
import java.util.Map;

@DataSourceRouting(dataSource="generated",isReadWriteSplitting=false, table = "base_code_id_item")
public interface CodeIDItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CodeItem record);

    int insertSelective(CodeItem record);

    CodeItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CodeItem record);

    int updateByPrimaryKey(CodeItem record);

    List<CodeItem> getCodeIDItemsByCodeId(long codeId);

    List<CodeItem> getFlowColumnByCodeId(long codeId);

    CodeItem selectByCodeId(Long codeId);

    int geFlowTypeByCodeId(Long codeId);
    
    int getFlowLengthByCodeId(Long codeId);
    int batchUpdateBycodeId(Long codeId,Map<Integer,Integer> map);
    int batchDelete(long[] ids);
    
}