package com.hivescm.codeid.mapper;


import com.hivescm.codeid.pojo.ProjectModul;
import com.mogujie.trade.db.DataSourceRouting;

@DataSourceRouting(dataSource="generated",isReadWriteSplitting=false, table = "base_system_project")
public interface ProjectModulMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectModul record);

    int insertSelective(ProjectModul record);

    ProjectModul selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectModul record);

    int updateByPrimaryKey(ProjectModul record);
}