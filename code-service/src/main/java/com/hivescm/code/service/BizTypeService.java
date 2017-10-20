package com.hivescm.code.service;

import com.hivescm.code.bean.BizTypeInfoBean;
import com.hivescm.code.bean.BizTypeMetadataInfoBean;
import com.hivescm.code.dto.BizTypeDto;
import com.hivescm.code.dto.BizTypeMetadataInfoDto;
import com.hivescm.code.dto.BizTypeQueryDto;
import com.hivescm.code.dto.KeyOperateDto;
import com.hivescm.common.domain.DataResult;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * <b>Description:</b><br>
 * 业务类型服务 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.service
 * <br><b>Date:</b> 2017/10/20 10:01
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
public interface BizTypeService {
	/**
	 * 新增业务类型
	 *
	 * @param bizTypeDto 业务类型
	 */
	void addBizType(BizTypeDto bizTypeDto);

	/**
	 * 业务类型简要信息查询
	 *
	 * @param queryParam 查询参数
	 * @return 业务类型简要信息集合
	 */
	List<BizTypeInfoBean> queryAllAavailableBizTypes(BizTypeQueryDto queryParam);

	/**
	 * 删除业务类型
	 * @param reqParam 请求参数
	 */
	void deleteBizType(KeyOperateDto reqParam);

	/**
	 * 查询业务类型简要信息
	 *
	 * @param reqParam 查询参数
	 * @return 业务类型集合
	 */
	List<BizTypeMetadataInfoBean> queryBizTypeMetadas(KeyOperateDto reqParam);
}
