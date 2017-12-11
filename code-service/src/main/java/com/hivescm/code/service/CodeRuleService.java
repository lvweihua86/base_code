package com.hivescm.code.service;

import com.hivescm.code.dto.AllocateCodeRuleDto;
import com.hivescm.code.dto.CodeRuleDto;

/**
 * <b>Description:</b><br>
 * 编码规则服务 <br><br>
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
public interface CodeRuleService {

    /**
     * 新增编码规则
     *
     * @param codeRuleDto 编码规则
     * @return
     */
    void addCodeRule(CodeRuleDto codeRuleDto);

    void allocateCodeRule(AllocateCodeRuleDto allocateCodeRuleDto);

    boolean codeRuleExist(String ruleName, int groupId);
}
