package com.hivescm.codeid.mapper;


import com.hivescm.codeid.pojo.BusinessModul;
import com.mogujie.trade.db.DataSourceRouting;

@DataSourceRouting(dataSource="generated",isReadWriteSplitting=false, table = "base_system_business_name")
public interface BusinessModulMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusinessModul record);

    int insertSelective(BusinessModul record);

    BusinessModul selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusinessModul record);

    int updateByPrimaryKey(BusinessModul record);
}