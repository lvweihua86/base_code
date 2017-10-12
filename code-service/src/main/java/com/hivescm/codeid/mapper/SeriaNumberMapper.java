package com.hivescm.codeid.mapper;

import com.hivescm.codeid.pojo.SeriaNumber;
import com.mogujie.route.rule.CRC32RouteRule;
import com.mogujie.trade.db.DataSourceRouting;
import com.mogujie.trade.tsharding.annotation.ShardingExtensionMethod;
import com.mogujie.trade.tsharding.annotation.parameter.ShardingParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@DataSourceRouting(
        dataSource ="generated",//数据源，不包含分库后缀
        table = "base_seria_number",//表名称，不包含分表后缀
        tables = 5, //单个数据库中的分表数量
        databases = 1, //分库数量
        isReadWriteSplitting = false,//是否启用读写分离
        routeRule = CRC32RouteRule.class//定义路由规则
)
public interface SeriaNumberMapper {
	@ShardingExtensionMethod()
    int deleteByPrimaryKey(Long id,@ShardingParam  String orgId);
	@ShardingExtensionMethod()
    int insert(@ShardingParam("orgId") @Param("seriaNumber") SeriaNumber record);
	@ShardingExtensionMethod()
    int insertSelective(@ShardingParam("orgId") @Param("seriaNumber") SeriaNumber record);
	@ShardingExtensionMethod()
    SeriaNumber selectByPrimaryKey(Long id,@ShardingParam  String orgId);
	@ShardingExtensionMethod()
    int updateByPrimaryKeySelective(@ShardingParam("orgId") @Param("seriaNumber") SeriaNumber record);
	@ShardingExtensionMethod()
    int updateByPrimaryKey(@ShardingParam("orgId") @Param("seriaNumber") SeriaNumber record);
	@ShardingExtensionMethod()
    List<SeriaNumber> getSeriaNumberByOrgIdAndCodeId(long codeId,@ShardingParam  String orgId);
	@ShardingExtensionMethod()
    String getCurrentByNoFlowColumnMaxNum(long codeId,@ShardingParam  String orgId);
	@ShardingExtensionMethod()
    String getCurrentByOneFlowColumnMaxNum(String value,long codeId,@ShardingParam  String orgId);
	@ShardingExtensionMethod()
    String getCurrentByTwoFlowColumnMaxNum(String v1,String v2,long codeId,@ShardingParam  String orgId);
	/*@ShardingExtensionMethod()
    List<SeriaNumber> getAllSeriaNumber();*/
	@ShardingExtensionMethod()
    int updateFlowNum(String flowNum,Long id,@ShardingParam  String orgId);
	@ShardingExtensionMethod()
    String getCurrentSeriaNumber(long codeId,@ShardingParam String orgId);
}