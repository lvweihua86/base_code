package com.hivescm.codeid.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.hivescm.cache.client.JedisClient;
import com.hivescm.codeid.common.Constants;
import com.hivescm.codeid.mapper.CodeIDItemMapper;
import com.hivescm.codeid.mapper.CodeIdMapper;
import com.hivescm.codeid.mapper.SeriaNumberMapper;
import com.hivescm.codeid.mapper.SingleSeriaNumberMapper;
import com.hivescm.codeid.pojo.SeriaNumber;
import com.hivescm.codeid.service.CodeIdService;
import com.hivescm.codeid.service.OrgService;
import com.hivescm.codeid.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2017/7/
 */
public class SeriaNumberMapperWriteToDatabaseJob implements SimpleJob {

    @Autowired
    private SeriaNumberMapper seriaNumberMapper;

    @Autowired
    private CodeIdMapper codeIdMapper;

    @Autowired
    private CodeIDItemMapper codeIdItemMapper;

    @Autowired
    private OrgService orgService;

    @Autowired
    private CodeIdService codeIdService;

    @Autowired
    private JedisClient jc;

    @Autowired
    private SingleSeriaNumberMapper singleSeriaNumberMapper;

    private String[] getKeys(int zeroreasonValue, int flowType, String flow_by1, String flow_by2, long codeId,
                             String orgId) {
        String key = null;
        String initialKey = null;
        if (1 == zeroreasonValue && 4 == flowType) {
            orgId = orgService.getFWID();
            key = Constants.GLOBAL_FLOW_NUMBER + "_" + codeId + "_" + orgId + "_" + flow_by1 + "_" + flow_by2;
        }

        if (1 == zeroreasonValue && 5 == flowType) {
            orgId = orgService.getFWID();
            key = Constants.GLOBAL_FLOW_NUMBER + "_" + codeId + "_" + orgId + "_" + flow_by1 + "_" + flow_by2;

            initialKey = Constants.GLOBAL_LETTER_FLOW_NUMBER + "_" + codeId + "_" + orgId + "_" + flow_by1 + "_"
                    + flow_by2;
        }

        if (2 == zeroreasonValue && 4 == flowType) {
            orgId = orgService.getJTID(orgId);
            key = Constants.GLOBAL_FLOW_NUMBER + "_" + codeId + "_" + orgId + "_" + flow_by1 + "_" + flow_by2;
        }

        if (2 == zeroreasonValue && 5 == flowType) {
            orgId = orgService.getJTID(orgId);
            key = Constants.GLOBAL_FLOW_NUMBER + "_" + codeId + "_" + orgId + "_" + flow_by1 + "_" + flow_by2;

            initialKey = Constants.GLOBAL_LETTER_FLOW_NUMBER + "_" + codeId + "_" + orgId + "_" + flow_by1 + "_"
                    + flow_by2;
        }

        if (3 == zeroreasonValue && 4 == flowType) {
            key = Constants.GLOBAL_FLOW_NUMBER + "_" + codeId + "_" + orgId + "_" + flow_by1 + "_" + flow_by2;
        }

        if (3 == zeroreasonValue && 5 == flowType) {
            key = Constants.GLOBAL_FLOW_NUMBER + "_" + codeId + "_" + orgId + "_" + flow_by1 + "_" + flow_by2;

            initialKey = Constants.GLOBAL_LETTER_FLOW_NUMBER + "_" + codeId + "_" + orgId + "_" + flow_by1 + "_"
                    + flow_by2;
        }
        return new String[]{key, initialKey};
    }

    @Override
    public void execute(ShardingContext shardingContext) {

        System.out.println("-------------------------------------------------------");
        System.out.println("---------------- SeriaNumberJob 开始执行 ----------------");
        System.out.println("-------------------------------------------------------");

        for (int i = 0; i < 5; i++) {
            long totalRecodesNum = singleSeriaNumberMapper.getSingleTableTotalCount(i);
            PageUtil pu = new PageUtil(1, totalRecodesNum, 100);
            for (int j = 1; j <= pu.getTotalPageNum(); j++) {
                pu = new PageUtil(j, totalRecodesNum, 100);
                List<SeriaNumber> shardingFlowNums = singleSeriaNumberMapper.getSingleTableEntitysByPages(i,
                        pu.getStartIndex(), pu.getPageSize());
                if (null != shardingFlowNums && shardingFlowNums.size() > 0) {
                    for (SeriaNumber seriaNumber : shardingFlowNums) {
                        int length = codeIdItemMapper.getFlowLengthByCodeId(seriaNumber.getCodeId());
                        final int zeroreasonValue = codeIdMapper.getzeroreasonByCodeId(seriaNumber.getOrgId(),
                                seriaNumber.getCodeId());
                        int flowType = codeIdItemMapper.geFlowTypeByCodeId(seriaNumber.getCodeId());
                        String flow_by1 = seriaNumber.getFlowBy1();
                        String flow_by2 = seriaNumber.getFlowBy2();

                        String[] doubles = getKeys(zeroreasonValue, flowType, flow_by1, flow_by2,
                                seriaNumber.getCodeId(), seriaNumber.getOrgId());

                        String key = doubles[0];
                        String initialKey = doubles[1];


                        String flowNum = null;
                        if (null != initialKey && null != key) {
                            try {
                                flowNum = codeIdService.letterDigitIncr(key, initialKey, length);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            singleSeriaNumberMapper.updateFlowNum(i, flowNum, seriaNumber.getId());
                        } else if (null != key && null == initialKey) {
                            String value = jc.get(key);
                            try {
                                flowNum = codeIdService.digitFillZero(value, length);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            singleSeriaNumberMapper.updateFlowNum(i, flowNum, seriaNumber.getId());
                        }
                    }
                }
            }

            System.out.println("-------------------------------------------------------");
            System.out.println("---------------- SeriaNumberJob 执行结束 ---------------");
            System.out.println("-------------------------------------------------------");
        }
    }
}
