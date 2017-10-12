package com.hivescm.codeid.mapper;


import com.hivescm.codeid.pojo.Metadata;
import com.mogujie.trade.db.DataSourceRouting;

@DataSourceRouting(dataSource="generated",isReadWriteSplitting=false, table = "base_metadata")
public interface MetadataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Metadata record);

    int insertSelective(Metadata record);

    Metadata selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Metadata record);

    int updateByPrimaryKey(Metadata record);
}