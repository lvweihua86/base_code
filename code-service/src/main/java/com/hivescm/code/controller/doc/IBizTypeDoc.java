package com.hivescm.code.controller.doc;

import com.hivescm.code.dto.*;
import com.hivescm.common.domain.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
@Api(value = "业务类型")
public interface IBizTypeDoc {
	/**
	 * 新增业务类型
	 *
	 * @param reqParam 业务类型
	 * @return <code>true</code> 新增成功
	 */
	@ApiOperation(value = "新增业务类型", httpMethod = "POST")
	@RequestMapping(value = "addBizType", method = RequestMethod.POST)
	DataResult<Boolean> addBizType(@RequestBody BizTypeDto reqParam);

	/**
	 * 业务类型简要信息查询
	 *
	 * @param reqParam 查询参数
	 * @return 业务类型集合
	 */
	@ApiOperation(value = "业务类型简要信息查询", httpMethod = "POST")
	@RequestMapping(value = "queryAllBizTypes", method = RequestMethod.POST)
	DataResult<List<BizTypeInfoDto>> queryAllBizTypes(@RequestBody BizTypeQueryDto reqParam);

	/**
	 * 删除业务类型
	 *
	 * @param reqParam 主键操作请求参数
	 * @return <code>true</code> 删除成功
	 */
	@ApiOperation(value = "删除业务类型", httpMethod = "POST")
	@RequestMapping(value = "deleteBizType", method = RequestMethod.POST)
	DataResult<Boolean> deleteBizType(@RequestBody KeyOperateDto reqParam);

	/**
	 * 查询业务类型简要信息
	 *
	 * @param reqParam 查询参数
	 * @return 业务类型集合
	 */
	@ApiOperation(value = "业务类型简要信息查询", httpMethod = "POST")
	@RequestMapping(value = "queryBizTypeMetadas", method = RequestMethod.POST)
	DataResult<List<BizTypeMetadataInfoDto>> queryBizTypeMetadas(@RequestBody KeyOperateDto reqParam);
}
