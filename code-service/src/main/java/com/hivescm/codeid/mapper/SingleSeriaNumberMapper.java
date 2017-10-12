package com.hivescm.codeid.mapper;


import com.hivescm.codeid.pojo.SeriaNumber;
import com.mogujie.trade.db.DataSourceRouting;

import java.util.List;

@DataSourceRouting(dataSource="generated",isReadWriteSplitting=false, table = "base_seria_number")
public interface SingleSeriaNumberMapper {
    
	long getSingleTableTotalCount(int tableIndex);
	
	List<SeriaNumber> getSingleTableEntitysByPages(int tableIndex,int startIndex,int pageSize);

    int updateFlowNum(int shardingNum,String flowNum,Long id);
}