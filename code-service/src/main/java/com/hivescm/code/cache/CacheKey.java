package com.hivescm.code.cache;

import com.google.common.collect.Lists;
import com.hivescm.code.common.Constants;

import java.util.List;

/**
 * @Author ZHJ
 * @Date 2018/1/13
 */
public class CacheKey {

    private String templateKey;
    private String serialNumKey;
    private String maxSerialNumKey;

    /**
     * 获取平台级缓存keys
     */
    public static CacheKey getPlatformCacheKey(String bizCode) {
        CacheKey key = new CacheKey();
        key.setTemplateKey(Constants.PLATFORM_CODE_TEMPLATE_REDIS_PREFIX + bizCode);
        key.setSerialNumKey(Constants.PLATFORM_CODE_SERIAL_NUM_REDIS_PREFIX + bizCode);
        key.setMaxSerialNumKey(Constants.PLATFORM_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + bizCode);
        return key;
    }

    /**
     * 获取平台级缓存keys
     */
    public static List<String> getPlatformCacheKeyList(String bizCode) {
        CacheKey key = getPlatformCacheKey(bizCode);
        return Lists.newArrayList(key.getTemplateKey(), key.getSerialNumKey(), key.getMaxSerialNumKey());
    }

    /**
     * 获取集团级缓存keys
     */
    public static CacheKey getGroupCacheKey(Integer groupId, String bizCode) {
        CacheKey key = new CacheKey();
        key.setTemplateKey(Constants.GROUP_CODE_TEMPLATE_REDIS_PREFIX + groupId + ":" + bizCode);
        key.setSerialNumKey(Constants.GROUP_CODE_SERIAL_NUM_REDIS_PREFIX + groupId + ":" + bizCode);
        key.setMaxSerialNumKey(Constants.GROUP_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + groupId + ":" + bizCode);
        return key;
    }

    /**
     * 获取集团级缓存keys
     */
    public static List<String> getGroupCacheKeyList(Integer groupId, String bizCode) {
        CacheKey key = getGroupCacheKey(groupId, bizCode);
        return Lists.newArrayList(key.getTemplateKey(), key.getSerialNumKey(), key.getMaxSerialNumKey());
    }

    /**
     * 获取业务单元级缓存keys
     */
    public static CacheKey getOrgCacheKey(Integer orgId, String bizCode) {
        CacheKey key = new CacheKey();
        key.setTemplateKey(Constants.BIZ_UNIT_CODE_TEMPLATE_REDIS_PREFIX + orgId + ":" + bizCode);
        key.setSerialNumKey(Constants.BIZ_UNIT_CODE_SERIAL_NUM_REDIS_PREFIX + orgId + ":" + bizCode);
        key.setMaxSerialNumKey(Constants.BIZ_UNIT_CODE_MAX_SERIAL_NUM_REDIS_PREFIX + orgId + ":" + bizCode);
        return key;
    }

    /**
     * 获取业务单元级缓存keys
     */
    public static List<String> getOrgCacheKeyList(Integer orgId, String bizCode) {
        CacheKey key = getOrgCacheKey(orgId, bizCode);
        return Lists.newArrayList(key.getTemplateKey(), key.getSerialNumKey(), key.getMaxSerialNumKey());
    }

    public String getTemplateKey() {
        return templateKey;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }

    public String getSerialNumKey() {
        return serialNumKey;
    }

    public void setSerialNumKey(String serialNumKey) {
        this.serialNumKey = serialNumKey;
    }

    public String getMaxSerialNumKey() {
        return maxSerialNumKey;
    }

    public void setMaxSerialNumKey(String maxSerialNumKey) {
        this.maxSerialNumKey = maxSerialNumKey;
    }
}
