package com.hivescm.code.controller;

import com.hivescm.cache.client.JedisClient;
import com.hivescm.code.common.Constants;
import com.hivescm.code.dto.CodeItem;
import com.hivescm.code.dto.CodeRule;
import com.hivescm.code.dto.VO;
import com.hivescm.code.exception.ErrorCode;
import com.hivescm.code.service.CodeItemService;
import com.hivescm.code.service.CodeRuleService;
import com.hivescm.code.service.GenerateIDService;
import com.hivescm.common.domain.DataResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CodeRuleController {

    @Autowired
    private CodeRuleService codeRuleService;

    @Autowired
    private CodeItemService codeItemService;

    @Autowired
    private GenerateIDService generateIDService;


    @Autowired
    private JedisClient jc;


    @ApiOperation(value="新增编码id",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeID", value = "编码id实体类", required = true, paramType = "query", dataType = "pojo"),
    })
    @RequestMapping(value="/addCodeID",method = RequestMethod.POST)
    @CrossOrigin
    public DataResult addCodeId(@RequestBody CodeRule codeId){
        long id = codeRuleService.addCodeId(codeId);
        if(id==-1L){
            return DataResult.success(ErrorCode.NO_ID);
        }else{

            /* 具体的编码规则 对应的 业务实体+组织id
		     *  key -- DYBM_orgId_businessCode
		     *  value -- codeid:time_format:code_type:zero_reason:is_default:is_break_code
		     */

            String key = Constants.CODEID_BUSINESSCODE_ORGID+"_"+codeId.getOrgId()
                    +"_"+codeId.getBusinessCode();

            String value = codeId.getId()+":"+codeId.getTimeFormat()+":"+codeId.getCodeType()
                    +":"+codeId.getZeroReason()+":"+codeId.getIsDefault()+":"+codeId.getIsBreakCode();

            jc.set(key,value);

            return DataResult.success(id);
        }
    }

    @ApiOperation(value="修改编码id",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeID", value = "编码id实体类", required = true, paramType = "query", dataType = "pojo"),
    })
    @RequestMapping(value = "/updateCodeID",method = RequestMethod.POST)
    @CrossOrigin
    public DataResult updateCodeID(@RequestBody CodeRule codeId){
        int i = codeRuleService.updateByPrimaryKey(codeId);
        if(i>0){

             /* 具体的编码规则 对应的 业务实体+组织id
		     *  key -- DYBM_orgId_businessCode
		     *  value -- codeid:time_format:code_type:zero_reason:is_default:is_break_code
		     */

            String key = Constants.CODEID_BUSINESSCODE_ORGID+"_"+codeId.getOrgId()
                    +"_"+codeId.getBusinessCode();

            String value = codeId.getId()+":"+codeId.getTimeFormat()+":"+codeId.getCodeType()
                    +":"+codeId.getZeroReason()+":"+codeId.getIsDefault()+":"+codeId.getIsBreakCode();

            jc.set(key,value);

            return DataResult.success("success");
        }else{
            return DataResult.success("failure");
        }
    }

    @ApiOperation(value="通过编码id，获取所有的编码详情",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编码id的主键", required = true, paramType = "query", dataType = "long"),
    })
    @RequestMapping(value = "/showCodeID/{id}",method = RequestMethod.GET)
    @CrossOrigin
    public DataResult getCodeIDItemsByCodeId(@PathVariable Long id){
        CodeRule codeID = codeRuleService.selectByPrimaryKey(id);
        if(null == codeID){
            return DataResult.success(ErrorCode.NO_CODEID);
        }else{
            return DataResult.success(codeID);
        }
    }

    @ApiOperation(value="获取编码类型",httpMethod = "GET")
    @RequestMapping(value = "/getCodeTypes",method = RequestMethod.GET)
    @CrossOrigin
    public DataResult geDataResultCodeTypes(){
        List<VO> list =  codeRuleService.getCodeTypes();
        return DataResult.success(list);
    }

    @ApiOperation(value="获取无流水类型",httpMethod = "GET")
    @RequestMapping(value = "/getNoSerialTypes",method = RequestMethod.GET)
    @CrossOrigin
    public DataResult getNoSerialTypes(){
        List<VO> list =  codeRuleService.getNoSerialTypes();
        return DataResult.success(list);
    }

    @ApiOperation(value="获取字符串流水类型",httpMethod = "GET")
    @RequestMapping(value = "/getStringSerialTypes",method = RequestMethod.GET)
    @CrossOrigin
    public DataResult getStringSerialTypes(){
        List<VO> list =  codeRuleService.getStringSerialTypes();
        return DataResult.success(list);
    }

    @ApiOperation(value="获取时间流水类型",httpMethod = "GET")
    @RequestMapping(value = "/getDateSerialTypes",method = RequestMethod.GET)
    @CrossOrigin
    public DataResult getDateSerialTypes(){
        List<VO> list =  codeRuleService.getDateSerialTypes();
        return DataResult.success(list);
    }

    @ApiOperation(value="停用编码id",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeId", value = "编码id主键", required = true, paramType = "query", dataType = "long"),
    })
    @RequestMapping(value = "/stopCodeID/{codeId}",method = RequestMethod.GET)
    @CrossOrigin
    public DataResult stopCodeID(@PathVariable long codeId){
        String s = codeRuleService.stopCodeID(codeId);
        if("success".equals(s)){
            CodeRule codeID = codeRuleService.selectByPrimaryKey(codeId);
            generateIDService.deleteKey(codeID.getOrgId(),codeID.getBusinessCode());
            return DataResult.success(s);
        }else{
            return DataResult.success(s);
        }
    }

    @ApiOperation(value="启用编码id",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeId", value = "编码id主键", required = true, paramType = "query", dataType = "long"),
    })
    @RequestMapping(value = "/reusedCodeID/{codeId}",method = RequestMethod.GET)
    @CrossOrigin
    public DataResult reusedCodeID(@PathVariable long codeId){
        String s = codeRuleService.reusedCodeID(codeId);
        if("success".equals(s)){
            generateIDService.updateCodeIDTemplate(codeId);
            return DataResult.success(s);
        }else{
            return DataResult.success(s);
        }
    }

    @ApiOperation(value="删除编码id",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编码id主键", required = true, paramType = "query", dataType = "long"),
    })
    @RequestMapping(value = "/deleteCodeID/{id}",method = RequestMethod.GET)
    @CrossOrigin
    public DataResult deleteCodeID(@PathVariable long id){
        try{
            CodeRule codeId = codeRuleService.selectByPrimaryKey(id);
            List<CodeItem> codeIDItems = codeItemService.getCodeIDItemsByCodeId(id);
            long[] ids = new long[codeIDItems.size()];
            for(int i=0;i<codeIDItems.size();i++){
                ids[i] = codeIDItems.get(i).getId();
            }
            codeItemService.batchDeleteBycodeId(ids);
            jc.delete(codeId.getOrgId()+"_"+codeId.getBusinessCode());
            codeRuleService.deleteCodeID(id);
            String key = Constants.CODEID_BUSINESSCODE_ORGID+"_"+codeId.getOrgId()
                    +"_"+codeId.getBusinessCode();
            jc.delete(key);
            return DataResult.success("success");
        }catch (Exception e){
            e.printStackTrace();
            return DataResult.success("failure");
        }
    }

    @ApiOperation(value="获取归零依据",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "组织id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "businessId", value = "业务id", required = true, paramType = "query", dataType = "String"),
    })
    @RequestMapping(value = "/getZeroReason/{orgId}/{businessId}",method = RequestMethod.GET)
    @CrossOrigin
    public DataResult  getZeroReason(@PathVariable String orgId,@PathVariable String businessId){
        List<String> s = codeRuleService.getZeroReason(orgId,businessId);
        if(null==s || "".equals(s)){
            VO vo = new VO("failure","-1");
            return DataResult.success(vo);
        }else{
            List<VO> VOS = new ArrayList<>();
            for(String sx : s){
                String[] sxx = sx.split(":");
                VO vo = new VO(sxx[1],sxx[0]);
                VOS.add(vo);
            }
            return DataResult.success(VOS);
        }
    }
}
