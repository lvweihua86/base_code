package com.hivescm.code.service.impl;

import com.hivescm.cache.client.JedisClient;
import com.hivescm.code.bean.CodeItemBean;
import com.hivescm.code.bean.CodeRuleBean;
import com.hivescm.code.cache.CodeCacheDataInfo;
import com.hivescm.code.cache.RedisCodeCache;
import com.hivescm.code.cache.TemplateCache;
import com.hivescm.code.cache.TemplateCacheData;
import com.hivescm.code.common.Constants;
import com.hivescm.code.dto.CodeResult;
import com.hivescm.code.dto.GenerateCode;
import com.hivescm.code.enums.CoverWayEnum;
import com.hivescm.code.enums.CutWayEnum;
import com.hivescm.code.enums.ItemTypeEnum;
import com.hivescm.code.enums.SerialTypeEnum;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.code.exception.CodeException;
import com.hivescm.code.mapper.CodeItemMapper;
import com.hivescm.code.mapper.CodeRuleMapper;
import com.hivescm.code.mapper.RuleItemRelationMapper;
import com.hivescm.code.service.CodeService;
import com.hivescm.code.task.SerialNumIncrTask;
import com.hivescm.code.utils.DateUtil;
import com.hivescm.code.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <b>Description:</b><br>
 * 编码服务为实现 <br><br>
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
@Service("codeService")
public class CodeServiceImpl implements CodeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeServiceImpl.class);
    private static final Logger RedisKey = LoggerFactory.getLogger("redisKeyLog");

    @Autowired
    private CodeRuleMapper codeRuleMapper;

    @Autowired
    private CodeItemMapper codeItemMapper;
    @Autowired
    private RuleItemRelationMapper ruleItemRelationMapper;

    @Resource
    private RedisCodeCache redisCodeCache;

    @Resource
    private TemplateCache templateCache;

    @Autowired
    private JedisClient jedisClient;

    /**
     * 生成编码
     *
     * @param reqParam 生成编码请求参数
     * @return 编码结果
     */
    public CodeResult generateCode(final GenerateCode reqParam) {
        final CodeCacheDataInfo cacheData = templateCache.getCodeInfo(reqParam);
        if (!cacheData.hasCache()) {
            throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "无对应的编码规则");
        }
        return generateCodeByCache(cacheData, reqParam);
    }

    /**
     * 依据 Redis 缓存数据生成编码
     *
     * @param cacheData 缓存数据
     * @return 编码结果
     */
    private CodeResult generateCodeByCache(final CodeCacheDataInfo cacheData, final GenerateCode reqParam) {
        CodeResult codeResult = new CodeResult();

        final TemplateCacheData templateData = cacheData.getTemplateData();
        final CodeRuleBean codeRule = templateData.getCodeRule();
        final List<CodeItemBean> codeItems = templateData.getCodeItems();

        StringBuilder codeBuilder = new StringBuilder();
        for (CodeItemBean codeItem : codeItems) {
            final Integer itemType = codeItem.getItemType();
            final ItemTypeEnum itemTypeEnum = ItemTypeEnum.getItemTypeEnum(itemType);
            switch (itemTypeEnum) {
                case CONSTANT:
                    constantItem(codeBuilder, codeItem, reqParam);
                    break;
                case STRING:
                    stringItem(codeBuilder, codeItem, reqParam);
                    break;
                case TIME:
                    timeItem(codeBuilder, codeItem, reqParam, codeRule);
                    break;
                case SERIAL:
                    serialItem(codeBuilder, codeItem, reqParam, codeRule, cacheData);
                    break;
                default:
                    throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "不支持的编码项类型");
            }
        }
        codeResult.setCode(codeBuilder.toString());
        codeResult.setRuleId(codeRule.getId());
        return codeResult;
    }

    /**
     * 常量处理
     *
     * @param codeBuilder 编码构建
     * @param codeItem    编码项
     */
    private void constantItem(StringBuilder codeBuilder, CodeItemBean codeItem, final GenerateCode reqParam) {
        final String itemValue = codeItem.getItemValue();
        final Integer itemLength = codeItem.getItemLength();
        if (StringUtils.isEmpty(itemValue)) {
            return;
        }
        final String constantValue = StringUtils.coverLength(itemLength, CutWayEnum.CUT_RIGHT, itemValue, "0", CoverWayEnum
                .NO);

        codeBuilder.append(constantValue);
    }

    /**
     * 字符串处理
     *
     * @param codeBuilder 编码构建
     * @param codeItem    编码项
     */
    private void stringItem(StringBuilder codeBuilder, CodeItemBean codeItem, final GenerateCode reqParam) {
        final Map<String, String> entityAttr = reqParam.getBizAttr();
        final String itemValue = codeItem.getItemValue();
        final String attrValue = entityAttr.get(itemValue);
        if (StringUtils.isEmpty(attrValue)) {
            return;
			/*LOGGER.warn("generate code illegal param:{},itemValue:{} is null.", reqParam, itemValue);
			throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "编码请求，缺失字符值【" + itemValue + "】");*/
        }
        final String stringItemValue = StringUtils
                .coverLength(codeItem.getItemLength(), CutWayEnum.CUT_RIGHT, attrValue, "0", CoverWayEnum.NO);
        codeBuilder.append(stringItemValue);

    }

    /**
     * 字符串处理
     *
     * @param codeBuilder 字符构建
     * @param codeItem    编码项
     * @param reqParam    编码请求参数
     * @param codeRule    编码规则
     */
    private void timeItem(StringBuilder codeBuilder, CodeItemBean codeItem, final GenerateCode reqParam,
                          final CodeRuleBean codeRule) {
        final Map<String, String> bizAttr = reqParam.getBizAttr();
        if (bizAttr == null) {
            return;
        }

        final String itemValue = codeItem.getItemValue();
        String bizDate = bizAttr.get(itemValue);
        final String timeFormat = codeRule.getTimeFormat();

        if (Constants.PLATFORM_DEFAULT_METADATA_SYSTEM_TIME.equals(itemValue)) {
            if (StringUtils.isEmpty(bizDate)) {
                SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
                bizDate = sdf.format(new Date());
            }
        }
        String formateDate;
        try {
            formateDate = DateUtil.formateDate(bizDate, timeFormat);
        } catch (Exception e) {
            LOGGER.warn("时间类型格式错误，param:{}.", bizAttr);
            throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "非法时间格式");
        }
        codeBuilder.append(formateDate);
    }

    /**
     * 字符串处理
     *
     * @param codeBuilder 字符构建
     * @param codeItem    编码项
     * @param reqParam    编码请求参数
     * @param codeRule    编码规则
     */
    private void serialItem(StringBuilder codeBuilder, CodeItemBean codeItem, final GenerateCode reqParam,
                            final CodeRuleBean codeRule, final CodeCacheDataInfo cacheData) {

        Long serialNum = serialTypeKey(codeItem.getSerialType(), cacheData.getSerialNumKey());

        RedisKey.info("(" + cacheData.getSerialNumKey() + ":" + serialNum + ")");
        String newSerialNum = Long.toString(serialNum);
        if (Long.toString(serialNum).length() < codeItem.getItemLength()) {
            newSerialNum = StringUtils.coverLength(codeItem.getItemLength(), CutWayEnum.CUT_LEFT, serialNum + "", "0", CoverWayEnum.LEFT);
        }
        String maxSerialNum = jedisClient.get(cacheData.getMaxSerialNumKey());
        final long cacheMaxSerialNum = null == maxSerialNum ? 0l : Long.valueOf(maxSerialNum);
        String cacheSerial = jedisClient.get(cacheData.getSerialNumKey());
        final long cacheSerialNum = null == cacheSerial ? 0l : Long.valueOf(cacheSerial);
        // 启动任务处理Mysql 缓存的流水号
        if (SerialNumIncrTask.reachThresholdValue(cacheMaxSerialNum, cacheSerialNum)) {
            new SerialNumIncrTask(cacheData, jedisClient, ruleItemRelationMapper).execute();
        }

        codeBuilder.append(newSerialNum);
    }

    /**
     * 流水依据
     * 0:不流水;
     * 1:按日流水;
     * 2:按月流水;
     * 3:按年流水;
     * 4:按字符串流水;
     */
    public Long serialTypeKey(Integer serialType, String serialNumKey) {
        Long serialNum;
        String today = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
        final SerialTypeEnum serialTypeEnum = SerialTypeEnum.getItemTypeEnum(serialType);
        switch (serialTypeEnum) {
            case NOT_SERIAL:
                serialNum = jedisClient.incrOneByKey(serialNumKey);
                break;
            case DAY_SERIAL:
                serialNum = jedisClient.incrOneByKey(serialNumKey + ":" + today) - 1;
                break;
            case MONTH_SERIAL:
                serialNum = jedisClient.incrOneByKey(serialNumKey + ":" + today.substring(0,6)) - 1;
                break;
            case YEAR_SERIAL:
                serialNum = jedisClient.incrOneByKey(serialNumKey + ":" + today.substring(0,4)) - 1;
                break;
            case STRING_SERIAL:
                serialNum = jedisClient.incrOneByKey(serialNumKey);
                break;
            default:
                throw new CodeException(CodeErrorCode.REQ_PARAM_ERROR_CODE, "不支持的流水类型");
        }
        return serialNum;
    }
}

