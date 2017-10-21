package com.hivescm.code.mapper;

import com.hivescm.code.bean.RuleItemRelationBean;
import com.mogujie.trade.db.DataSourceRouting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b>Description:</b><br>
 * 规则与项关系mapper <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.mapper
 * <br><b>Date:</b> 2017/10/20 15:58
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@DataSourceRouting(dataSource = "generated", isReadWriteSplitting = false, table = "base_code_rule_item_relation")
public interface RuleItemRelationMapper {

	/**
	 * 查询组织对应的业务编码绑定的默认编码规则（锁）
	 *
	 * @param orgId   业务单元ID
	 * @param bizCode 业务编码
	 * @return 绑定关系
	 */
	RuleItemRelationBean queryDefaultBandingRuleLock(@Param(value = "orgId") Integer orgId,
			@Param(value = "bizCode") String bizCode);

	/**
	 * 依据规则ID删除规则与项关系表
	 *
	 * @param ruleIds 规则ID集合
	 */
	Integer deleteByRuleIds(@Param(value = "ruleIds") List<Integer> ruleIds);
}
