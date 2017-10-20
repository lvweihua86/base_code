package com.hivescm.code.mapper;

import com.hivescm.code.bean.CodeRuleBean;
import com.mogujie.trade.db.DataSourceRouting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b>Description:</b><br>
 * 编码规则 mapper <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.bean
 * <br><b>Date:</b> 2017/10/19 17:17
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@DataSourceRouting(dataSource = "generated", isReadWriteSplitting = false, table = "base_code_rule")
public interface CodeRuleMapper {

	/**
	 * 新增编码规则
	 * @param bean 编码规则实体数据
	 * @return 编码规则自增ID
	 */
	int addCodeRule(@Param(value = "bean") CodeRuleBean bean);


	/**
	 * 依据业务编码删除编码规则
	 *
	 * @param ruleIds 规则ID集合
	 */
	void deleteByIds(@Param(value = "ruleIds") List<Integer> ruleIds);

	/**
	 * 根据业务编码查询编码规则ID集合
	 * @param bizCode 业务编码
	 * @return
	 */
	List<Integer> queryRuleIdsByBizCode(@Param(value = "bizCode") String bizCode);

}