package com.hivescm.code.controller;

import com.hivescm.cache.client.JedisClient;
import com.hivescm.code.common.Constants;
import com.hivescm.code.controller.doc.ICodeRuleDoc;
import com.hivescm.code.dto.AllocateCodeRuleDto;
import com.hivescm.code.dto.CodeRuleDto;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.service.CodeRuleService;
import com.hivescm.code.validator.AddCodeRuleValidator;
import com.hivescm.code.validator.AllocateCodeRuleValidator;
import com.hivescm.common.domain.DataResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <b>Description:</b><br>
 * 编码规则控制器 <br><br>
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
@RestController
public class CodeRuleController implements ICodeRuleDoc {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeController.class);

    @Resource
    private AddCodeRuleValidator addCodeRuleValidator;

    @Resource
    private AllocateCodeRuleValidator allocateCodeRuleValidator;

    @Resource
    private CodeRuleService codeRuleService;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public DataResult<Boolean> addCodeRule(@RequestBody CodeRuleDto reqParam) {
        LOGGER.info("add code rule request,param:{}.", reqParam);
        try {
            addCodeRuleValidator.validate(reqParam);

            codeRuleService.addCodeRule(reqParam);

            return DataResult.success(Boolean.TRUE, Boolean.class);
        } catch (CodeException ce) {
            LOGGER.error("add code rule failed,param:" + reqParam, ce);
            return DataResult.faild(ce.getErrorCode(), ce.getMessage());
        } catch (Exception ex) {
            LOGGER.error("add code rule error,param:" + reqParam, ex);
            return DataResult.faild(CodeErrorCode.CODE_SYSTEM_ERROR_CODE, Constants.CODE_SERVICE_ERROR);
        }
    }

    @Override
    public DataResult<Boolean> allocateCodeRule(@RequestBody AllocateCodeRuleDto reqParam) {
        LOGGER.info("allocate code rule request,param:{}.", reqParam);
        try {
            allocateCodeRuleValidator.validate(reqParam);

            codeRuleService.allocateCodeRule(reqParam);

            return DataResult.success(Boolean.TRUE, Boolean.class);
        } catch (CodeException ce) {
            LOGGER.error("allocate code rule failed,param:" + reqParam, ce);
            return DataResult.faild(ce.getErrorCode(), ce.getMessage());
        } catch (Exception ex) {
            LOGGER.error("allocate code rule error,param:" + reqParam, ex);
            return DataResult.faild(CodeErrorCode.CODE_SYSTEM_ERROR_CODE, Constants.CODE_SERVICE_ERROR);
        }
    }

    @Override
    public DataResult<Boolean> codeRuleExist(String ruleName, String groupId) {
        LOGGER.info("check code rule exist|ruleName:{}|groupId:{}", ruleName, groupId);
        try {
            if (StringUtils.isBlank(ruleName)) {
                throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "ruleName参数为空");
            }
            int gid = NumberUtils.toInt(groupId, -1);
            if (gid == -1) {
                throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "groupId参数错误");
            }
            boolean exist = codeRuleService.codeRuleExist(ruleName, gid);
            return DataResult.success(exist, Boolean.class);
        } catch (CodeException ce) {
            LOGGER.error("check code rule exist failed|ruleName:" + ruleName + "|groupId:" + groupId, ce);
            return DataResult.faild(ce.getErrorCode(), ce.getMessage());
        } catch (Exception ex) {
            LOGGER.error("check code rule exist error|ruleName:" + ruleName + "|groupId:" + groupId, ex);
            return DataResult.faild(CodeErrorCode.CODE_SYSTEM_ERROR_CODE, Constants.CODE_SERVICE_ERROR);
        }
    }

}
