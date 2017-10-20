package com.hivescm.code.controller.doc;

import com.hivescm.code.dto.BizTypeDetailDto;
import com.hivescm.code.dto.BizTypeInfoDto;
import com.hivescm.code.dto.BizTypeQueryDto;
import com.hivescm.common.domain.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <b>Description:</b><br>
 * 业务实体 <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.controller.doc
 * <br><b>Date:</b> 2017/10/19 20:02
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@Api(value = "业务实体")
public interface IBizEntityDoc{
	/**
	 * 新增业务实体
	 *
	 * @param reqParam 业务实体
	 * @return <code>true</code> 新增成功
	 */
	@ApiOperation(value = "新增业务实体", httpMethod = "POST")
	DataResult<Boolean> addBizEntity(@RequestBody BizTypeDetailDto reqParam);

	/**
	 * 业务实体简要信息查询
	 *
	 * @param queryParam 查询参数
	 * @return 业务实体集合
	 */
	@ApiOperation(value = "业务实体简要信息查询", httpMethod = "POST")
	DataResult<List<BizTypeInfoDto>> queryAllAavailableBizEntities(BizTypeQueryDto queryParam);
}
