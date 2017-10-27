package com.hivescm.code.mapper;

import com.hivescm.code.bean.BizTypeBean;
import com.hivescm.code.bean.BizTypeInfoBean;
import com.hivescm.code.dto.BizTypeQueryDto;
import com.mogujie.trade.db.DataSourceRouting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b>Description:</b><br>
 * 业务类型 mapper <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.mapper
 * <br><b>Date:</b> 2017/10/20 10:32
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@DataSourceRouting(dataSource = "generated", isReadWriteSplitting = false, table = "base_code_biz_type")
public interface BizTypeMapper {


	/**
	 * 新增业务类型
	 *
	 * @param bean 业务类型
	 */
	void addBizType(@Param("bean") BizTypeBean bean);
	/**
	 * 删除业务类型
	 *
	 * @param id 业务类型ID
	 */
	void deleteBizTypeById(@Param("id") Integer id);
	/**
	 * 根据业务类型ID查询业务类型
	 * @param id 业务类型ID
	 * @return 业务类型检验信息
	 */
	BizTypeInfoBean queryBizTypeById(@Param("id") Integer id,@Param("bizCode") String bizCode);

	/**
	 * 根据业务类型编码查询业务类型
	 *
	 * @param bizCode 业务类型编码
	 * @return 业务类型检验信息
	 */
	BizTypeInfoBean queryBizTypeByBizCode(@Param("bizCode") String bizCode);

	/**
	 * 业务类型简要信息查询
	 *
	 * @param queryParam 查询参数
	 * @return 业务类型简要信息集合
	 */
	List<BizTypeInfoBean> queryAllAavailableBizTypes(@Param("queryParam") BizTypeQueryDto queryParam);
}
