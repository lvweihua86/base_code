package com.hivescm.code.mapper;

import com.hivescm.code.bean.BizTypeMetadataBean;
import com.hivescm.code.bean.BizTypeMetadataInfoBean;
import com.mogujie.trade.db.DataSourceRouting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b>Description:</b><br>
 * 业务类型元数据mapper <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.mapper
 * <br><b>Date:</b> 2017/10/20 11:45
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@DataSourceRouting(dataSource = "generated", isReadWriteSplitting = false, table = "base_code_biz_type_metadata")
public interface BizTypeMetadataMapper {
	/**
	 * 批量新增业务类型元数据
	 *
	 * @param beans 业务类型元数据集合
	 */
	void batchAddBizTypeMetadata(@Param(value = "beans") final List<BizTypeMetadataBean> beans);

	/**
	 * 依据业务类型ID删除元数据
	 *
	 * @param typeId 业务类型ID
	 */
	void deleteBizTypeMetadataByTypeId(@Param(value = "typeId") Integer typeId);

	/**
	 * 依据业务类型查询所有元数据
	 *
	 * @param typeId 业务类型ID
	 * @return 业务类型元数据
	 */
	List<BizTypeMetadataInfoBean> queryMetadatasByTypeId(@Param(value = "typeId") Integer typeId);
}
