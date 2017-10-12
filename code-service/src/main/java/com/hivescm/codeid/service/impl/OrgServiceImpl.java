package com.hivescm.codeid.service.impl;

import com.hivescm.codeid.service.OrgService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/6/30.
 */
@Service("orgService")
public class OrgServiceImpl implements OrgService {
     //获取集团的组织id
     public String getJTID(String orgId){
        return "A001";
     }
     //全局蜂网的组织id
     public String getFWID(){
         return "FW001";
     }
}
