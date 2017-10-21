package com.hivescm.code.service.impl;

import com.hivescm.cache.client.JedisClient;
import com.hivescm.code.bean.CodeItemBean;
import com.hivescm.code.bean.CodeRuleBean;
import com.hivescm.code.cache.RedisCodeCache;
import com.hivescm.code.common.Constants;
import com.hivescm.code.dto.CodeItemDto;
import com.hivescm.code.dto.CodeResult;
import com.hivescm.code.dto.CodeRuleDto;
import com.hivescm.code.dto.GenerateCode;
import com.hivescm.code.enums.CacheLevelEnum;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.mapper.CodeItemMapper;
import com.hivescm.code.mapper.CodeRuleMapper;
import com.hivescm.code.service.CodeRuleService;
import com.hivescm.code.service.CodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

	@Autowired
	private JedisClient jedisClient;

	@Resource
	private CodeService codeService;

	@Autowired
	public CodeRuleMapper codeRuleMapper;

	@Autowired
	private CodeItemMapper codeItemMapper;

	@Resource
	private RedisCodeCache redisCodeCache;

	@Override
	public void addCodeRule(final CodeRuleDto codeRuleDto) {

		final CodeRuleBean codeRuleBean = initCodeRule(codeRuleDto);
		LOGGER.debug("insert code rule into db,bean:{}.", codeRuleBean);

		final List<CodeItemBean> codeItems = initCodeItem(codeRuleDto, codeRuleBean);
		LOGGER.debug("insert rule item into db,beans:{}.", codeItems);

		codeRuleMapper.addCodeRule(codeRuleBean);
		final Integer ruleId = codeRuleBean.getId();

		codeItemMapper.batchAddCodeItem(codeItems, ruleId);

		// 初始化Redis缓存
		redisCodeCache.initCache(codeRuleBean, codeItems, null, CacheLevelEnum.NEW);
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
		codeRuleBean.setCodeWay(2);
		codeRuleBean.setBreakCode(0);
		codeRuleBean.setCover(1);
		codeRuleBean.setTimeFormat("yyyyMMdd");
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
	 * @param codeRuleDto  编码规则请求参数
	 * @param codeRuleBean 编码规则实体数据（设置编码长度，别无它用）
	 * @return 规则项集合
	 */
	private List<CodeItemBean> initCodeItem(final CodeRuleDto codeRuleDto, final CodeRuleBean codeRuleBean) {
		final List<CodeItemDto> codeItems = codeRuleDto.getCodeItems();

		List<CodeItemBean> codeItemBeans = new ArrayList<>(codeItems.size());
		for (CodeItemDto codeItemDto : codeItems) {
			CodeItemBean codeItemBean = new CodeItemBean();
			BeanUtils.copyProperties(codeItemDto, codeItemBean);
			codeItemBeans.add(codeItemBean);

			Integer oldBeanLength = codeRuleBean.getTotalLenght();
			int oldLength = oldBeanLength == null ? 0 : oldBeanLength;
			codeRuleBean.setTotalLenght(oldLength + codeItemBean.getItemLength());
		}
		return codeItemBeans;
	}
}

