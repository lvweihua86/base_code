package com.hivescm.code.service.impl;

import com.hivescm.cache.client.JedisClient;
import com.hivescm.code.bean.CodeItemBean;
import com.hivescm.code.bean.CodeRuleBean;
import com.hivescm.code.dto.CodeItem;
import com.hivescm.code.dto.CodeRule;
import com.hivescm.code.mapper.CodeItemMapper;
import com.hivescm.code.mapper.CodeRuleMapper;
import com.hivescm.code.service.CodeRuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

	@Autowired
	private JedisClient jedisClient;

	@Autowired
	public CodeRuleMapper codeRuleMapper;

	@Autowired
	private CodeItemMapper codeItemMapper;

	@Override
	public void addCodeRule(CodeRule codeRule) {

	}
}

