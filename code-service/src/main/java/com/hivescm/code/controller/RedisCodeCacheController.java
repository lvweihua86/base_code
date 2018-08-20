package com.hivescm.code.controller;

import com.hivescm.code.bean.RedisKeyBean;
import com.hivescm.code.cache.RedisCodeCache;
import com.hivescm.code.common.Constants;
import com.hivescm.code.controller.doc.IRedisCodeDoc;
import com.hivescm.code.exception.CodeErrorCode;
import com.hivescm.common.domain.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:
 * User: luca
 * Date: 2018-08-20
 * Time: 下午4:04
 * Company：蜂网供应链（上海）有限公司
 */
@RestController
public class RedisCodeCacheController implements IRedisCodeDoc {
    @Autowired
    private RedisCodeCache redisCodeCache;

    @Override
    public DataResult<Boolean> updateRedisKeys(@RequestBody List<RedisKeyBean> redisKeyBeanList) {

        try {
            redisCodeCache.updateRedisKeys(redisKeyBeanList);
        } catch (Exception e) {
            return DataResult.faild(CodeErrorCode.CODE_SYSTEM_ERROR_CODE, Constants.CODE_SERVICE_ERROR);
        }
        return DataResult.success(true, Boolean.class);
    }
}
