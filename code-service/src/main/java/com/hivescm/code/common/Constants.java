package com.hivescm.code.common;

/**
 * <b>Description:</b><br>
 * 编码常量 <br><br>
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
public class Constants {

    public static final String CODE_SERVICE_ERROR="编码系统错误";

    /**
     * 编码规则编码，业务编码
     */
    public static final String CODE_RULE_CODE_BIZ_CODE="CODE_RULE_CODE";

    //编码id  对应的 业务单元+组织id
    public static final String CODEID_BUSINESSCODE_ORGID = "DYBM";
    public static final String GLOBAL_FLOW_NUMBER = "QJLS";
    public static final String GLOBAL_LETTER_FLOW_NUMBER = "QJLS_initial";
    public static final String BLOC_FLOW_NUMBER = "JTLS";
    public static final String BLOC_LETTER_FLOW_NUMBER = "JTLS_initial";
    public static final String ORG_FLOW_NUMBER = "ZZLS";
    public static final String ORG_LETTER_FLOW_NUMBER = "ZZLS_initial";


    // Redis 编码规则模板 后追加编码规则ID
    public static final String REDIS_CODE_RULE_PREFIX = "code:code_rule:";

    // Redis 存储断号前缀 后追加编码规则ID
    public static final String REDIS_BREAK_NUM_PREFIX = "code:break_num:";
}
