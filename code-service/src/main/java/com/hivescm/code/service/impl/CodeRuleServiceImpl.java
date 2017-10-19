package com.hivescm.codeid.service.impl;

import com.hivescm.codeid.mapper.CodeIdMapper;
import com.hivescm.codeid.mapper.ZeroReasonSpecialMapper;
import com.hivescm.codeid.pojo.CodeId;
import com.hivescm.codeid.pojo.VO;
import com.hivescm.codeid.service.CodeIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("codeIDService")
public class CodeIDServiceImpl implements CodeIDService {

    @Autowired
    public CodeIdMapper codeIdMapper;

    @Autowired
    public ZeroReasonSpecialMapper zeroReasonSpecialMapper;

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public CodeId selectByPrimaryKey(Long id){
        return codeIdMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Long addCodeId(CodeId codeId){
         Long id = codeIdMapper.insert(codeId);
         if(null != id && id > 0){
             return codeId.getId();
         }else{
             return -1L;
         }
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<VO> getCodeTypes(){
         VO vo1 = new VO("常量","1");
         VO vo2 = new VO("字符串","2");
         VO vo3 = new VO("时间","3");
         VO vo4 = new VO("纯数字流水号","4");
         VO vo5 = new VO("字母数字流水号","5");
         List<VO> list = new ArrayList<>();
         list.add(vo1);list.add(vo2);list.add(vo3);list.add(vo4);list.add(vo5);
         return list;
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<VO> getNoSerialTypes(){
        VO vo1 = new VO("不流水","0");
        List<VO> list = new ArrayList<>();
        list.add(vo1);
        return list;
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<VO> getDateSerialTypes(){
        VO vo2 = new VO("按日流水","1");
        VO vo3 = new VO("按月流水","2");
        VO vo4 = new VO("按年流水","3");
        List<VO> list = new ArrayList<>();
        list.add(vo2);list.add(vo3);list.add(vo4);
        return list;
    }

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<VO> getStringSerialTypes(){
        VO vo5 = new VO("按字符串流水","4");
        List<VO> list = new ArrayList<>();
        list.add(vo5);
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String stopCodeID(long codeId){
          int i = codeIdMapper.stopCodeID(codeId);
          if(i>0){
              return "success";
          }else{
              return "failure";
          }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String reusedCodeID(long codeId){
        int i = codeIdMapper.reusedCodeID(codeId);
        if(i>0){
            return "success";
        }else{
            return "failure";
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String deleteCodeID(long id){
        int i = codeIdMapper.deleteByPrimaryKey(id);
        if(i>0){
            return "success";
        }else{
            return "failure";
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<String>  getZeroReason(String orgId,String businessId){
        List<String> list = zeroReasonSpecialMapper.getZeroReason(orgId,businessId);
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateByPrimaryKey(CodeId record){
        return codeIdMapper.updateByPrimaryKey(record);
    }
}

