package com.hivescm.codeid.service;

/**
 * Created by MaGuowei on 2017/6/30.
 */
public interface OrgService {
	//获取集团的组织id
	String getJTID(String orgId);

	//全局蜂网的组织id
	String getFWID();
}
