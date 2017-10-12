package com.hivescm.codeid.utils;

import java.util.List;


public class PageUtil {
	//页面展示所需数据
	private List<?> list;//当前分页所要展示的数据
	private int currentPageIndex;//当前页
	private int totalPageNum;//总页数
	private int pageSize;//每页显示的记录数
	private int nextPageIndex;//下一页
	private int previousPageIndex;//上一页

	//数据库查询所需数据
	private int startIndex;//分页查询的开始索引位置
	private long totalRecodesNum;//当前表的总记录数

	public PageUtil(int currentPageIndex,long totalRecodesNum,int pageSize){
		//传入你要查询那一页
		this.currentPageIndex = currentPageIndex;
		//获取当前所要查询的表的总记录数
		this.totalRecodesNum = totalRecodesNum;
		//传入每页显示的记录数
		this.pageSize = pageSize;

		//计算总页数
		this.totalPageNum =(int) (this.totalRecodesNum%this.pageSize==0?this.totalRecodesNum/this.pageSize:this.totalRecodesNum/this.pageSize+1);
		//计算分页查询的开始索引位置
		this.startIndex = (this.currentPageIndex-1)*this.pageSize;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getNextPageIndex() {
		this.nextPageIndex = this.currentPageIndex +1;
		if(this.nextPageIndex > this.totalPageNum){
			this.nextPageIndex = this.totalPageNum;
		}
		return this.nextPageIndex;
	}

	public void setNextPageIndex(int nextPageIndex) {
		this.nextPageIndex = nextPageIndex;
	}

	public int getPreviousPageIndex() {
		this.previousPageIndex = this.currentPageIndex -1;
		if(this.previousPageIndex < 1){
			this.previousPageIndex = 1;
		}
		return this.previousPageIndex;
	}

	public void setPreviousPageIndex(int previousPageIndex) {
		this.previousPageIndex = previousPageIndex;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public long getTotalRecodesNum() {
		return totalRecodesNum;
	}

	public void setTotalRecodesNum(long totalRecodesNum) {
		this.totalRecodesNum = totalRecodesNum;
	}
}
