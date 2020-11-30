package com.gec.bean;

import java.util.List;

public class PageModel<T> {

	private int totalRecordSum; //总记录数
	private int pageSize = 3; 	//页面大小
	private int pageIndex;		//当前页
	private int totalPageSum;	//总页数
	private List<T> list;		//页面数据集合
	
	public int getTotalRecordSum() {
		return totalRecordSum;
	}
	public void setTotalRecordSum(int totalRecordSum) {
		this.totalRecordSum = totalRecordSum;
		//更新总页数
		this.setTotalPageSum();
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		//更新总页数
		this.setTotalPageSum();
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getTotalPageSum() {
		return totalPageSum;
	}
	public void setTotalPageSum() {
		this.totalPageSum = this.totalRecordSum%this.pageSize==0?
				this.totalRecordSum/this.pageSize:this.totalRecordSum/this.pageSize+1;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
}
