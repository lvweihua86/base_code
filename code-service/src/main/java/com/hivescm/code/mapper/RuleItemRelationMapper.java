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
@DataSourceRouting(dataSource = "generated", isReadWriteSplitting = false, table = "base_code_rule_org_relation")
public interface RuleItemRelationMapper {
	/**
	 * 新增绑定关系
	 *
	 * @param bean 规则与组织关系数据实体
	 * @return
	 */
	int addRelation(@Param(value = "bean") RuleItemRelationBean bean);

	/**
	 * 增加缓存步数
	 *
	 * @param id  关系ID
	 * @param num 递增数
	 * @return
	 */
	void incrCacheStepNum(@Param(value = "id") Integer id, @Param(value = "num") Integer num);

	/**
	 * 更新关系的默认状态
	 *
	 * @param ids        关系ID集合
	 * @param defaulted 默认状态
	 * @return
	 */
	void updateRelationDefaultState(@Param(value = "ids") List<Integer> ids, @Param(value = "defaulted") Integer defaulted);

	/**
	 * 更新组织关系的默认状态
	 *
	 * @param groupId   集团ID
	 * @param orgId     业务单元ID
	 * @param bizCode   业务编码
	 * @param defaulted 默认状态
	 * @param defaulted 默认状态
	 * @return
	 */
	void updateOrgRelationDefaultState(@Param(value = "groupId") Integer groupId,
			@Param(value = "orgId") Integer orgId,
			@Param(value = "bizCode") String bizCode, @Param(value = "defaulted") Integer defaulted);

	/**
	 * 查询组织对应的业务编码绑定的默认编码规则（锁）
	 *
	 * @param groupId 集团ID
	 * @param orgId   业务单元ID
	 * @param bizCode 业务编码
	 * @return 绑定关系
	 */
	RuleItemRelationBean queryDefaultBandingRuleLock(@Param(value = "groupId") Integer groupId,
			@Param(value = "orgId") Integer orgId,
			@Param(value = "bizCode") String bizCode);

	/**
	 * 查询组织对应的业务编码绑定的默认编码规则（锁）
	 *
	 * @param bizCode 业务编码
	 * @param groupId 集团ID
	 * @param orgId   业务单元ID
	 * @return 绑定关系
	 */
	List<RuleItemRelationBean> queryBandingRuleLock(@Param(value = "bizCode") String bizCode,
			@Param(value = "groupId") Integer groupId,
			@Param(value = "orgId") Integer orgId);

	/**
	 * 查询业务编码绑定的所有编码规则
	 *
	 * @param bizCode 业务编码
	 * @return 绑定关系集合
	 */
	List<RuleItemRelationBean> queryBizCodeAllBandingRelations(@Param(value = "bizCode") String bizCode);

	/**
	 * 依据规则ID删除规则与项关系表
	 *
	 * @param ruleIds 规则ID集合
	 */
	Integer deleteByRuleIds(@Param(value = "ruleIds") List<Integer> ruleIds);
}
