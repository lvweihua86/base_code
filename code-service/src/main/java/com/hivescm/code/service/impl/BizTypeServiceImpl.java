package com.hivescm.code.service.impl;

import com.hivescm.cache.client.JedisClient;
import com.hivescm.code.bean.BizTypeBean;
import com.hivescm.code.bean.BizTypeInfoBean;
import com.hivescm.code.bean.BizTypeMetadataBean;
import com.hivescm.code.bean.BizTypeMetadataInfoBean;
import com.hivescm.code.dto.BizTypeDto;
import com.hivescm.code.dto.BizTypeMetadataDto;
import com.hivescm.code.dto.BizTypeQueryDto;
import com.hivescm.code.dto.KeyOperateDto;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.mapper.*;
import com.hivescm.code.service.BizTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

	@Autowired
	private JedisClient jedisClient;

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

		// 物理删除绑定当前当前业务类型的编码规则的组织关系
		ruleItemRelationMapper.deleteByRuleIds(ruleIds);

		// TODO
		/*// 移除业务编码在Redis中的相关缓存
		jedisClient.delete(CodeRedisUtil.codeTemplatePrefix(bizCode), CodeRedisUtil.codeSerialNumPrefix(bizCode),
				CodeRedisUtil.codeMaxSerialNumPrefix(bizCode));
		*/
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
		systemTimeMetadata.setMetadataName("systemTime");
		systemTimeMetadata.setMetadataShow("系统时间");
		bizTypeMetadataBeans.add(systemTimeMetadata);

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
}
