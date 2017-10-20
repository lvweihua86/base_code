package com.hivescm.code.mapper;

import com.hivescm.code.bean.CodeItemBean;
import com.mogujie.trade.db.DataSourceRouting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b>Description:</b><br>
 * 编码项 mapper <br><br>
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
@DataSourceRouting(dataSource = "generated", isReadWriteSplitting = false, table = "base_code_item")
public interface CodeItemMapper {

	void batchAddCodeItem(@Param(value = "beans") List<CodeItemBean> beans ,@Param(value = "ruleId")Integer ruleId);

	/**
	 * 依据规则ID删除编码规则项
	 *
	 * @param ruleIds 规则ID集合
	 */
	Integer deleteByRuleIds(@Param(value = "ruleIds") List<Integer> ruleIds);
}