package com.hivescm.code.service.impl;

import com.hivescm.code.bean.CodeItemBean;
import com.hivescm.code.bean.CodeRuleBean;
import com.hivescm.code.bean.CodeRuleInfoBean;
import com.hivescm.code.bean.RuleItemRelationBean;
import com.hivescm.code.cache.RedisCodeCache;
import com.hivescm.code.common.Constants;
import com.hivescm.code.dto.*;
import com.hivescm.code.enums.BooleanEnum;
import com.hivescm.code.enums.CacheLevelEnum;
import com.hivescm.code.enums.CoverWayEnum;
import com.hivescm.code.enums.ItemTypeEnum;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.mapper.CodeItemMapper;
import com.hivescm.code.mapper.CodeRuleMapper;
import com.hivescm.code.mapper.RuleItemRelationMapper;
import com.hivescm.code.service.CodeRuleService;
import com.hivescm.code.service.CodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * <b>Description:</b><br>
 * 编码规则服务实现 <br><br>
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
@Service("codeRuleService")
public class CodeRuleServiceImpl implements CodeRuleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeRuleServiceImpl.class);

    @Resource
    private CodeService codeService;

    @Autowired
    public CodeRuleMapper codeRuleMapper;

    @Autowired
    private CodeItemMapper codeItemMapper;

    @Autowired
    private RuleItemRelationMapper ruleItemRelationMapper;

    @Resource
    private RedisCodeCache redisCodeCache;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void addCodeRule(final CodeRuleDto codeRuleDto) {

        final CodeRuleBean codeRuleBean = initCodeRule(codeRuleDto);
        LOGGER.info("insert code rule into db,bean:{}.", codeRuleBean);

        final CodeRuleInfoBean codeRuleInfoBean = codeRuleMapper
                .queryConflicRuleName(codeRuleDto.getGroupId(), codeRuleDto.getRuleName());
        if (codeRuleInfoBean != null) {
            LOGGER.warn("insert code rule into db warn,param:{}.", codeRuleDto);
            throw new CodeException(CodeErrorCode.DATE_CONFLIC_ERROR_CODE, "集团内编码名称重复");
        }

        // 若当前为默认的需要更新，其它的为非默认状态
        if (codeRuleDto.getDefaulted() == BooleanEnum.TRUE.getTruth()) {
            codeRuleMapper.updateOtherNoDefault(codeRuleDto.getGroupId(), codeRuleDto.getBizCode());
        }
        try {
            codeRuleMapper.addCodeRule(codeRuleBean);
        } catch (Exception ex) {
            LOGGER.warn("insert code rule into db warn.", ex);
            throw new CodeException(CodeErrorCode.DATE_CONFLIC_ERROR_CODE, "集团内编码名称重复");
        }

        final List<CodeItemBean> codeItems = initCodeItem(codeRuleDto, codeRuleBean.getId());
        LOGGER.info("insert rule item into db,beans:{}.", codeItems);

        codeItemMapper.batchAddCodeItem(codeItems);

        // 默认分配，并初始化到Redis缓存
        defaultAllocation(codeRuleBean, codeItems);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void allocateCodeRule(AllocateCodeRuleDto allocateCodeRuleDto) {

        final Integer ruleId = allocateCodeRuleDto.getRuleId();
        final Integer groupId = allocateCodeRuleDto.getGroupId();
        final Integer orgId = allocateCodeRuleDto.getOrgId();
        final String bizCode = allocateCodeRuleDto.getBizCode();

        final List<RuleItemRelationBean> alreadyAllocateCodeRules = ruleItemRelationMapper
                .queryBandingRuleLock(bizCode, groupId, orgId);
        LOGGER.info("当前集团组织分配的编码规则：{}.", alreadyAllocateCodeRules);
        if (CollectionUtils.isEmpty(alreadyAllocateCodeRules)) {
            // 分配当前编码规则到当前业务单元，并进行redis缓存初始化工作
            manualAllocation(allocateCodeRuleDto);
        } else {
            final Map<Integer, RuleItemRelationBean> relationMap = converToMap(alreadyAllocateCodeRules);

            if (relationMap.containsKey(ruleId)) {
                final RuleItemRelationBean currentRelationBean = relationMap.get(ruleId);

                final Integer allocateDefaultState = allocateCodeRuleDto.getDefaulted();
                if (currentRelationBean.getDefaulted() == allocateDefaultState) {
                    return;//重复分配，就返回了
                }

                if (allocateDefaultState == BooleanEnum.TRUE.getTruth()) {//启用当前的编码规则
                    // 停用其它的关联关系
                    final List<Integer> relationIds = filterRelationIds(alreadyAllocateCodeRules);
                    relationIds.remove(currentRelationBean.getId());
                    ruleItemRelationMapper.updateRelationDefaultState(relationIds, BooleanEnum.FALSE.getTruth());

                    relationIds.clear();
                    relationIds.add(currentRelationBean.getId());
                    ruleItemRelationMapper.updateRelationDefaultState(relationIds, BooleanEnum.TRUE.getTruth());
                }

                if (allocateDefaultState == BooleanEnum.FALSE.getTruth()) {//停用用当前的编码规则
                    List<Integer> relationIds = new ArrayList<>(1);
                    relationIds.add(currentRelationBean.getId());
                    ruleItemRelationMapper.updateRelationDefaultState(relationIds, BooleanEnum.TRUE.getTruth());

                    redisCodeCache.deleteBizUnitCache(currentRelationBean);
                }

            } else {
                // 分配当前编码规则到当前业务单元，并进行redis缓存初始化工作
                manualAllocation(allocateCodeRuleDto);
            }
        }
    }

    @Override
    public boolean codeRuleExist(String ruleName, int groupId) {
        return codeRuleMapper.queryConflicRuleName(groupId, ruleName) != null;
    }

    /**
     * 将关系实体集合转换为Map数据结构
     *
     * @param alreadyAllocateCodeRules 规则与组织关系数据实体集合
     * @return key 为规则ID，value 为规则与组织关系数据实体；
     */
    private Map<Integer, RuleItemRelationBean> converToMap(final List<RuleItemRelationBean> alreadyAllocateCodeRules) {
        Map<Integer, RuleItemRelationBean> map = new HashMap<>();
        for (RuleItemRelationBean alreadyAllocateCodeRule : alreadyAllocateCodeRules) {
            map.put(alreadyAllocateCodeRule.getRuleId(), alreadyAllocateCodeRule);
        }
        return map;
    }

    /**
     * 过滤已分配关系ID
     *
     * @param alreadyAllocateCodeRules 规则与组织关系数据实体集合
     * @return 实体ID集合
     */
    private List<Integer> filterRelationIds(final List<RuleItemRelationBean> alreadyAllocateCodeRules) {
        List<Integer> list = new ArrayList<>();
        for (RuleItemRelationBean alreadyAllocateCodeRule : alreadyAllocateCodeRules) {
            list.add(alreadyAllocateCodeRule.getId());
        }
        return list;
    }

    /**
     * 默认编码规则分配信息
     *
     * @param codeRule  编码规则
     * @param codeItems 编码项集合
     * @return
     */
    private void defaultAllocation(final CodeRuleBean codeRule, final List<CodeItemBean> codeItems) {
        final Integer defaulted = codeRule.getDefaulted();
        if (BooleanEnum.FALSE.getTruth() == defaulted) {
            return;
        }
        final Integer groupId = codeRule.getGroupId();
        final String bizCode = codeRule.getBizCode();
        // 变更其它的为非默认状态
        ruleItemRelationMapper
                .updateOrgRelationDefaultState(groupId, Constants.NO_ORG_ID, bizCode, BooleanEnum.FALSE.getTruth());

        // 新增默认绑定关系
        RuleItemRelationBean relationBean = new RuleItemRelationBean();
        relationBean.setRuleId(codeRule.getId());
        relationBean.setGroupId(groupId);
        relationBean.setOrgId(Constants.NO_ORG_ID);
        relationBean.setBizCode(bizCode);
        relationBean.setDefaulted(BooleanEnum.TRUE.getTruth());
        relationBean.setStepNum(Constants.CACHE_SERIAL_NUM_DEFAULT_STEP_NUM);
        relationBean.setStepSize(Constants.CACHE_SERIAL_NUM_DEFAULT_STEP_SIZE);
        ruleItemRelationMapper.addRelation(relationBean);

        // 初始化Redis 缓存
        redisCodeCache.initCache(codeRule, codeItems, relationBean, CacheLevelEnum.NEW, Boolean.TRUE);
    }

    /**
     * 手动分配编码规则
     *
     * @param allocateCodeRuleDto 分配编码规则请求参数
     */
    private void manualAllocation(final AllocateCodeRuleDto allocateCodeRuleDto) {
        final Integer defaulted = allocateCodeRuleDto.getDefaulted();
        if (BooleanEnum.FALSE.getTruth() == defaulted) {
            return;
        }

        final Integer ruleId = allocateCodeRuleDto.getRuleId();
        RuleItemRelationBean relationBean = new RuleItemRelationBean();
        relationBean.setRuleId(ruleId);
        relationBean.setGroupId(allocateCodeRuleDto.getGroupId());
        relationBean.setOrgId(allocateCodeRuleDto.getOrgId());
        relationBean.setBizCode(allocateCodeRuleDto.getBizCode());
        relationBean.setDefaulted(BooleanEnum.TRUE.getTruth());
        relationBean.setStepNum(Constants.CACHE_SERIAL_NUM_DEFAULT_STEP_NUM);
        relationBean.setStepSize(Constants.CACHE_SERIAL_NUM_DEFAULT_STEP_SIZE);
        relationBean.setCreateUser(allocateCodeRuleDto.getUserId());
        relationBean.setCreateTime(System.currentTimeMillis());
        ruleItemRelationMapper.addRelation(relationBean);

        final CodeRuleBean codeRule = codeRuleMapper.queryRuleByIdLock(ruleId);
        final List<CodeItemBean> codeItems = codeItemMapper.queryItemsByRuleId(ruleId);

        // 初始化Redis 缓存
        redisCodeCache.initCache(codeRule, codeItems, relationBean, CacheLevelEnum.BIZ_UNIT, Boolean.TRUE);
    }

    /**
     * 初始化编码规则
     *
     * @param codeRuleDto 编码规则请求参数
     * @return 编码规则数据实体
     */
    private CodeRuleBean initCodeRule(final CodeRuleDto codeRuleDto) {
        CodeRuleBean codeRuleBean = new CodeRuleBean();
        BeanUtils.copyProperties(codeRuleDto, codeRuleBean);
        codeRuleBean.setCreateTime(System.currentTimeMillis());
        codeRuleBean.setRuleCode(getRuleCode());
        return codeRuleBean;
    }

    /**
     * 获取规则编码
     *
     * @return 规则编码
     */
    private String getRuleCode() {
        GenerateCode generateCode = new GenerateCode();
        generateCode.setBizCode(Constants.CODE_RULE_CODE_BIZ_CODE);
        generateCode.setGroupId(1);// 取平台级

        try {
            final CodeResult codeResult = codeService.generateCode(generateCode);
            return codeResult.getCode();
        } catch (Exception ex) {
            LOGGER.error("generate rule code failed.", ex);
            throw new CodeException(CodeErrorCode.CODE_SYSTEM_ERROR_CODE, "编码规则未初始化");
        }
    }

    /**
     * 初始化规则项
     *
     * @param codeRuleDto 编码规则请求参数
     * @param ruleId      编码规则ID
     * @return 规则项集合
     */
    private List<CodeItemBean> initCodeItem(final CodeRuleDto codeRuleDto, final int ruleId) {
        final List<CodeItemDto> codeItems = codeRuleDto.getCodeItems();

        TreeSet<CodeItemBean> codeItemsTree = new TreeSet<>();
        for (CodeItemDto codeItemDto : codeItems) {
            CodeItemBean codeItemBean = new CodeItemBean();
            codeItemBean.setRuleId(ruleId);
            codeItemBean.setItemType(codeItemDto.getItemType());
            codeItemBean.setOrderNum(codeItemDto.getOrderNum());
            codeItemBean.setItemValue(codeItemDto.getItemValue());
            codeItemBean.setItemLength(codeItemDto.getItemLength());

            final ItemTypeEnum itemTypeEnum = ItemTypeEnum.getItemTypeEnum(codeItemDto.getItemType());
            switch (itemTypeEnum) {
                case CONSTANT:
                    break;
                case STRING:
                    codeItemBean.setSerial(0);
                    codeItemBean.setCoverWay(CoverWayEnum.NO.getWay());
                    codeItemBean.setCoverChar("0");
                    break;
                case TIME:
                    codeItemBean.setSerial(0);
                    break;
                case SERIAL:
                    codeItemBean.setSerialType(codeItemDto.getSerialType());
                    break;
            }
            codeItemsTree.add(codeItemBean);
        }

        final Iterator<CodeItemBean> iterator = codeItemsTree.iterator();
        int orderNum = 1;
        while (iterator.hasNext()) {
            final CodeItemBean nextCodeItem = iterator.next();
            nextCodeItem.setOrderNum(orderNum);
            orderNum++;
        }
        return new ArrayList<>(codeItemsTree);
    }
}

