package com.hivescm.code.controller.doc;

import com.hivescm.code.bean.RedisKeyBean;
import com.hivescm.code.dto.CodeResult;
import com.hivescm.code.dto.GenerateCode;
import com.hivescm.common.domain.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Description:
 * User: luca
 * Date: 2018-08-20
 * Time: 下午4:05
 * Company：蜂网供应链（上海）有限公司
 */
@Api(value = "Redis编码赋值")
public interface IRedisCodeDoc {

    /**
     * 批量更新redis keys
     *
     * @param redisKeyBeanList 更新redis keys 请求参数
     * @return 成功or失败
     */
    @ApiOperation(value = "修改redis keys", httpMethod = "POST")
    @RequestMapping(value = "/updateRedisKeys", method = RequestMethod.POST)
    DataResult<Boolean> updateRedisKeys(@RequestBody List<RedisKeyBean> redisKeyBeanList);

}
