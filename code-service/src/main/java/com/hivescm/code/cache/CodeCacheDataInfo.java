package com.hivescm.code.cache;

import com.google.gson.Gson;
import com.hivescm.code.common.Constants;

/**
 * <b>Description:</b><br>
 * Redis 缓存数据信息 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.cache
 * <br><b>Date:</b> 2017/10/20 20:40
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public class CodeCacheDataInfo {
    private static final Gson GSON = new Gson();
    /**
     * 是否有缓存
     */
    private boolean hasCache;

    /**
     * 集团ID
     */
    private Integer groupId;
    /**
     * 组织ID
     */
    private Integer orgId;
    /**
     * 业务编码
     */
    private String bizCode;

    /**
     * 编码模板
     */
    private String cacheTemplate;

    /**
     * 规则缓存
     */
    private TemplateCacheData templateData;

    /**
     * 流水号缓存Key
     */
    private String serialNumKey;

    /**
     * 最大流水号Key
     */
    private String maxSerialNumKey;

    public CodeCacheDataInfo() {
    }

    /**
     * 创建平台级缓存
     */
    public static CodeCacheDataInfo newPlatformCache(String bizCode, String cacheTemplate,
                                                     TemplateCacheData templateData, String serialNumKey,
                                                     String maxSerialNumKey) {
        return new CodeCacheDataInfo(true, Constants.PLATFORM_GROUP_ID, Constants.NO_ORG_ID, bizCode, cacheTemplate,
                templateData, serialNumKey, maxSerialNumKey);
    }

    /**
     * 创建平台级缓存
     */
    public static CodeCacheDataInfo newPlatformCache(String bizCode, String cacheTemplate, String serialNumKey,
                                                     String maxSerialNumKey) {
        return newPlatformCache(bizCode, cacheTemplate, null, serialNumKey, maxSerialNumKey);
    }

    /**
     * 创建集团级缓存
     */
    public static CodeCacheDataInfo newGroupCache(Integer groupId, String bizCode, String cacheTemplate,
                                                  TemplateCacheData templateData, String serialNumKey,
                                                  String maxSerialNumKey) {
        return new CodeCacheDataInfo(true, groupId, 0, bizCode, cacheTemplate, templateData, serialNumKey,
                maxSerialNumKey);
    }

    /**
     * 创建集团级缓存
     */
    public static CodeCacheDataInfo newGroupCache(Integer groupId, String bizCode, String cacheTemplate,
                                                  String serialNumKey, String maxSerialNumKey) {
        return newGroupCache(groupId, bizCode, cacheTemplate, null, serialNumKey, maxSerialNumKey);
    }

    /**
     * 创建业务单元级缓存
     */
    public static CodeCacheDataInfo newOrgCache(Integer groupId, Integer orgId, String bizCode, String cacheTemplate,
                                                TemplateCacheData templateData, String serialNumKey,
                                                String maxSerialNumKey) {
        return new CodeCacheDataInfo(true, groupId, orgId, bizCode, cacheTemplate, templateData, serialNumKey,
                maxSerialNumKey);
    }

    /**
     * 创建业务单元级缓存
     */
    public static CodeCacheDataInfo newOrgCache(Integer groupId, Integer orgId, String bizCode, String cacheTemplate,
                                                String serialNumKey, String maxSerialNumKey) {
        return newOrgCache(groupId, orgId, bizCode, cacheTemplate, null, serialNumKey, maxSerialNumKey);
    }

    /**
     * 创建缓存
     */
    public static CodeCacheDataInfo newCache(Integer groupId, String bizCode, String cacheTemplate,
                                             TemplateCacheData templateData, String serialNumKey,
                                             String maxSerialNumKey) {
        return new CodeCacheDataInfo(true, groupId, 0, bizCode, cacheTemplate, templateData, serialNumKey,
                maxSerialNumKey);
    }

    private CodeCacheDataInfo(boolean hasCache, Integer groupId, Integer orgId, String bizCode, String cacheTemplate,
                              TemplateCacheData templateData, String serialNumKey, String maxSerialNumKey) {
        this.hasCache = hasCache;
        this.groupId = groupId;
        this.orgId = orgId;
        this.bizCode = bizCode;
        this.cacheTemplate = cacheTemplate;
        this.templateData = templateData;
        this.serialNumKey = serialNumKey;
        this.maxSerialNumKey = maxSerialNumKey;
    }

    public boolean hasCache() {
        return hasCache;
    }

    public void setHasCache(boolean hasCache) {
        this.hasCache = hasCache;
    }

    /**
     * 改写get();确保后续取值时为空
     *
     * @return 缓存模板json数据
     */
    public String getCacheTemplate() {
        if (cacheTemplate != null) {
            return cacheTemplate;
        }

        if (templateData != null) {
            return GSON.toJson(templateData);
        }
        return null;
    }

    public void setCacheTemplate(String cacheTemplate) {
        this.cacheTemplate = cacheTemplate;
    }

    /**
     * 改写get();确保后续取值时不为空
     *
     * @return 缓存模板实体数据
     */
    public TemplateCacheData getTemplateData() {
        if (templateData != null) {
            return templateData;
        }

        if (cacheTemplate != null) {
            return GSON.fromJson(cacheTemplate, TemplateCacheData.class);
        }
        return null;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public void setTemplateData(TemplateCacheData templateData) {
        this.templateData = templateData;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CodeCacheDataInfo{");
        sb.append("hasCache=").append(hasCache);
        sb.append(", groupId=").append(groupId);
        sb.append(", orgId=").append(orgId);
        sb.append(", bizCode='").append(bizCode).append('\'');
        sb.append(", cacheTemplate='").append(cacheTemplate).append('\'');
        sb.append(", templateData=").append(templateData);
        sb.append(", serialNumKey='").append(serialNumKey).append('\'');
        sb.append(", maxSerialNumKey='").append(maxSerialNumKey).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
