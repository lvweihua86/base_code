package com.hivescm.code.service.impl;

import com.hivescm.code.bean.*;
import com.hivescm.code.cache.RedisCodeCache;
import com.hivescm.code.common.Constants;
import com.hivescm.code.dto.*;
import com.hivescm.code.enums.BooleanEnum;
import com.hivescm.code.enums.ItemTypeEnum;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.mapper.*;
import com.hivescm.code.service.BizTypeService;
import com.hivescm.code.service.CodeRuleService;
import com.hivescm.code.utils.StringUtils;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <b>Description:</b><br>
 * 业务类型实现类 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.service.impl
 * <br><b>Date:</b> 2017/10/20 10:30
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@Service(value = "bizTypeService")
public class BizTypeServiceImpl implements BizTypeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BizTypeServiceImpl.class);

	@Autowired
	private BizTypeMapper bizTypeMapper;

	@Autowired
	private BizTypeMetadataMapper bizTypeMetadataMapper;

	@Autowired
	private CodeRuleMapper codeRuleMapper;

	@Autowired
	private CodeItemMapper codeItemMapper;

	@Autowired
	private RuleItemRelationMapper ruleItemRelationMapper;

	@Resource
	private RedisCodeCache redisCodeCache;

	@Resource
	private CodeRuleService codeRuleService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void addBizType(final BizTypeDto bizTypeDto) {
		final BizTypeInfoBean bizTypeInfoBean = bizTypeMapper.queryBizTypeByBizCode(bizTypeDto.getBizCode());
		if (null != bizTypeInfoBean) {
			throw new CodeException(CodeErrorCode.DATE_CONFLIC_ERROR_CODE, "业务类型编码重复");
		}

		final BizTypeBean newBizTypeBean = initBizTypeBean(bizTypeDto);
		LOGGER.debug("insert new biz type into db,bean:{}.", newBizTypeBean);

		try {
			bizTypeMapper.addBizType(newBizTypeBean);
		} catch (Exception e) {
			LOGGER.warn("insert new biz type into db warn.", e);
			throw new CodeException(CodeErrorCode.DATE_CONFLIC_ERROR_CODE, "业务类型编码重复");
		}

		final List<BizTypeMetadataBean> bizTypeMetadataBeans = initTypeMetadata(bizTypeDto, newBizTypeBean);
		LOGGER.debug("insert new biz type metadata into db,beans:{}.", bizTypeMetadataBeans);
		if (!CollectionUtils.isEmpty(bizTypeMetadataBeans)) {
			try {
				bizTypeMetadataMapper.batchAddBizTypeMetadata(bizTypeMetadataBeans);
			} catch (Exception e) {
				LOGGER.warn("insert new biz type metadata into db warn.", e);
				throw new CodeException(CodeErrorCode.DATE_CONFLIC_ERROR_CODE, "同一业务类型元数据名称重复");
			}
		}
		// 初始化业务类型默认平台级的编码规则
		final CodeRuleDto codeRuleDto = initPlatform(bizTypeDto);
		codeRuleService.addCodeRule(codeRuleDto);
	}

	@Override
	public List<BizTypeInfoBean> queryAllAavailableBizTypes(BizTypeQueryDto queryParam) {
		return bizTypeMapper.queryAllAavailableBizTypes(queryParam);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deleteBizType(KeyOperateDto reqParam) {
		final Integer bizTypeId = reqParam.getDataId();
		final BizTypeInfoBean bizTypeInfoBean = bizTypeMapper.queryBizTypeById(bizTypeId);
		if (null == bizTypeInfoBean) {
			LOGGER.info("delete biz type not exist,param:{}.", reqParam);
			throw new CodeException(CodeErrorCode.DATE_CONFLIC_ERROR_CODE, "业务类型编码重复");
		}

		// 物理删除当前业务类型
		bizTypeMapper.deleteBizTypeById(bizTypeId);
		// 物理删除当前业务类型的元数据
		bizTypeMetadataMapper.deleteBizTypeMetadataByTypeId(bizTypeId);

		// 绑定当前当前业务类型的编码规则ID集合
		final String bizCode = bizTypeInfoBean.getBizCode();
		final List<Integer> ruleIds = codeRuleMapper.queryRuleIdsByBizCode(bizCode);
		if (CollectionUtils.isEmpty(ruleIds)) {
			return;
		}

		// 物理删除绑定当前当前业务类型的编码规则
		codeRuleMapper.deleteByIds(ruleIds);
		// 物理删除绑定当前当前业务类型的编码规则项
		codeItemMapper.deleteByRuleIds(ruleIds);
		final List<RuleItemRelationBean> bindingRelations = ruleItemRelationMapper
				.queryBizCodeAllBandingRelations(bizCode);
		// 物理删除绑定当前当前业务类型的编码规则的组织关系
		ruleItemRelationMapper.deleteByRuleIds(ruleIds);
		// 删除该编码规则的所有缓存
		redisCodeCache.batchDeleteCache(bindingRelations);
	}

	@Override
	public List<BizTypeMetadataInfoBean> queryBizTypeMetadas(KeyOperateDto reqParam) {
		return bizTypeMetadataMapper.queryMetadatasByTypeId(reqParam.getDataId());
	}

	/**
	 * 初始化业务类型实体
	 *
	 * @param bizTypeDto 业务类型数据
	 * @return 业务类型实体
	 */
	private BizTypeBean initBizTypeBean(final BizTypeDto bizTypeDto) {
		BizTypeBean bizTypeBean = new BizTypeBean();
		BeanUtils.copyProperties(bizTypeDto, bizTypeBean);
		bizTypeBean.setCreateTime(System.currentTimeMillis());
		return bizTypeBean;
	}

	/**
	 * 初始化类型元数据
	 *
	 * @param bizTypeDto     业务类型数据
	 * @param newBizTypeBean 业务类型实体
	 * @return null or 业务类型元数据集合
	 */
	private List<BizTypeMetadataBean> initTypeMetadata(final BizTypeDto bizTypeDto, final BizTypeBean newBizTypeBean) {

		final List<BizTypeMetadataDto> metadatas = bizTypeDto.getMetadatas();
		final long currentTimeMillis = System.currentTimeMillis();

		Set<BizTypeMetadataBean> bizTypeMetadataBeans = new HashSet<>();

		// 初始化默认配置
		BizTypeMetadataBean systemTimeMetadata = new BizTypeMetadataBean();
		systemTimeMetadata.setTypeId(newBizTypeBean.getId());
		systemTimeMetadata.setCreateTime(currentTimeMillis);
		systemTimeMetadata.setCreateUser(newBizTypeBean.getCreateUser());
		systemTimeMetadata.setMetadataName(Constants.PLATFORM_DEFAULT_METADATA_SYSTEM_TIME);
		systemTimeMetadata.setMetadataShow(Constants.PLATFORM_DEFAULT_METADATA_SYSTEM_TIME_SHOW_NAME);
		bizTypeMetadataBeans.add(systemTimeMetadata);

		// 初始化默认配置
		BizTypeMetadataBean prefixMetadata = new BizTypeMetadataBean();
		prefixMetadata.setTypeId(newBizTypeBean.getId());
		prefixMetadata.setCreateTime(currentTimeMillis);
		prefixMetadata.setCreateUser(newBizTypeBean.getCreateUser());
		prefixMetadata.setMetadataName(Constants.PLATFORM_DEFAULT_METADATA_CODE_PREFIX);
		prefixMetadata.setMetadataShow(Constants.PLATFORM_DEFAULT_METADATA_CODE_PREFIX_SHOW_NAME);
		bizTypeMetadataBeans.add(prefixMetadata);

		if (!CollectionUtils.isEmpty(metadatas)) {
			for (BizTypeMetadataDto metadata : metadatas) {
				BizTypeMetadataBean bizTypeMetadataBean = new BizTypeMetadataBean();
				BeanUtils.copyProperties(metadata, bizTypeMetadataBean);
				bizTypeMetadataBean.setTypeId(newBizTypeBean.getId());
				bizTypeMetadataBean.setCreateTime(currentTimeMillis);
				bizTypeMetadataBean.setCreateUser(newBizTypeBean.getCreateUser());
				bizTypeMetadataBeans.add(bizTypeMetadataBean);
			}
		}

		return new ArrayList<>(bizTypeMetadataBeans);
	}

	private CodeRuleDto initPlatform(final BizTypeDto bizTypeDto) {
		CodeRuleDto codeRule = new CodeRuleDto();
		codeRule.setGroupId(Constants.PLATFORM_GROUP_ID);
		codeRule.setDefaulted(BooleanEnum.TRUE.getTruth());
		codeRule.setBizCode(bizTypeDto.getBizCode());
		codeRule.setRuleName(bizTypeDto.getBizName() + Constants.PLATFORM_DEFAULT_CODE_PREFIX_SHOW);
		codeRule.setTotalLenght(4 + 8 + 6);

		List<CodeItemDto> codeItemDtos = new ArrayList<>(3);
		codeRule.setCodeItems(codeItemDtos);

		CodeItemDto constantItem = new CodeItemDto();
		constantItem.setItemType(ItemTypeEnum.CONSTANT.getType());
		constantItem.setOrderNum(1);
		final String customPrefix = bizTypeDto.getCustomPrefix();
		// 自定义前缀处理
		String prefix = StringUtils.isEmpty(customPrefix) ? Constants.PLATFORM_DEFAULT_CODE_PREFIX : customPrefix;
		constantItem.setItemValue(prefix);
		constantItem.setItemLength(prefix.length());
		codeItemDtos.add(constantItem);

		CodeItemDto timeItem = new CodeItemDto();
		timeItem.setItemType(ItemTypeEnum.TIME.getType());
		timeItem.setOrderNum(2);
		timeItem.setItemLength(8);
		timeItem.setItemValue(Constants.PLATFORM_DEFAULT_METADATA_SYSTEM_TIME);
		codeItemDtos.add(timeItem);

		CodeItemDto serialItem = new CodeItemDto();
		serialItem.setItemType(ItemTypeEnum.SERIAL.getType());
		serialItem.setOrderNum(3);
		serialItem.setItemLength(6);
		serialItem.setItemValue("999999");
		codeItemDtos.add(serialItem);

		return codeRule;
	}
}
