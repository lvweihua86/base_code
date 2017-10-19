package com.hivescm.code.mapper;

import com.mogujie.trade.db.DataSourceRouting;


/**
 * <b>Description:</b><br>
 * 编码项 mapper <br><br>
 * <p>
 * <b>Note</b><br>
 * <b>ProjectName:</b> base-code
 * <br><b>PackageName:</b> com.hivescm.code.bean
 * <br><b>Date:</b> 2017/10/19 17:17
 *
 * @author DongChunfu
 * @version 1.0
 * @since JDK 1.8
 */
@DataSourceRouting(dataSource = "base_code", isReadWriteSplitting = false, table = "base_code_item")
public interface CodeItemMapper {
}