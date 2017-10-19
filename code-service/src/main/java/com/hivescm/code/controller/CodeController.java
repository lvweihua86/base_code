package com.hivescm.code.controller;

import com.hivescm.code.service.CodeIdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hivescm.common.domain.DataResult;

/**
 * Created by Administrator on 2017/7/9.
 */
@RestController
@Api(value = "生成编码id")
public class CodeIdController {

   @Autowired
   @Qualifier("codeIdService")
   private CodeIdService codeIdService;

   @RequestMapping(value = "/getID", method = RequestMethod.GET)
   @ApiOperation(value="生成id",httpMethod = "GET")
   @ApiImplicitParams({
       @ApiImplicitParam(name = "businessCode", value = "业务id", required = true, paramType = "query", dataType = "String"),
       @ApiImplicitParam(name = "orgId", value = "组织id", required = true, paramType = "query", dataType = "String"),
       @ApiImplicitParam(name = "json", value = "前台数据,通过该数据组织生成id", required = true, paramType = "query", dataType = "String"),
   })
   public DataResult <String> generatorId(@RequestParam("businessCode") String
                                                    businessCode,
                                         @RequestParam("orgId")String orgId,
                                         @RequestParam("json")String json) throws Exception{

       String id = codeIdService.generateID(businessCode,orgId,json);
       if(null==id || "".equals(id)){
          return DataResult.faild(3456,"创建编码id失败");
       }else{
           return DataResult.success(id,String.class);
       }
   }
   
   @RequestMapping(value = "/init", method = RequestMethod.GET)
   @ApiOperation(value="初始化数据",httpMethod = "GET")
   public DataResult<String> initCodeIDTemplate() {
       try{
           codeIdService.initCodeIDTemplate();
       }catch (Exception e){
           return DataResult.faild(5354,"初始化编码id模版失败");
       }
       return DataResult.success("success",String.class);
   }
}
