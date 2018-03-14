package com.hww.base.common.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 列表分页，包含list属性
 */
@SuppressWarnings("serial")
public class Pagination extends SimplePage implements Serializable, Paginable {

	private List<?> list; // 数据列表
	private int pageNumListSize = 9; // 页码列表大小，默认9
	private List<?> pageNumList; // 页码列表
	private List<?> ids; // 多表联查转多次单表查询使用id

	public Pagination() {

	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            页量
	 * @param totalCount
	 *            记录总数
	 */
	public Pagination(int pageNo, int pageSize, long totalCount) {
		super(pageNo, pageSize, totalCount);
		setPageNumList();
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 * @param list
	 *            分页内容
	 */
	public Pagination(int pageNo, int pageSize, long totalCount, List<?> list) {
		super(pageNo, pageSize, totalCount);
		setPageNumList();
		this.list = list;
	}

	/**
	 * 第一条数据位置
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 设置页面列表
	 */
	private void setPageNumList() {
		List<Integer> list = new ArrayList<Integer>();// 设置之前先清空
		int totalPage = getTotalPage(); // 分页总页数
		if (totalPage > this.pageNumListSize) {
			int halfSize = this.pageNumListSize / 2;
			int first = 1;
			int end = 1;
			if (this.pageNo - halfSize < 1) { // 当前页靠近最小数1
				first = 1;
				end = this.pageNumListSize;
			} else if (totalPage - this.pageNo < halfSize) { // 当前页靠近最大数
				first = totalPage - this.pageNumListSize + 1;
				end = totalPage;
			} else {
				first = this.pageNo - halfSize;
				end = this.pageNo + halfSize;
			}
			for (int i = first; i <= end; i++) {
				list.add(i);
			}
		} else {
			for (int i = 0; i < totalPage; i++) {
				list.add(i + 1);
			}
		}
		this.pageNumList = list;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getPageNumListSize() {
		return pageNumListSize;
	}

	public void setPageNumListSize(int pageNumListSize) {
		this.pageNumListSize = pageNumListSize;
	}

	public List<?> getPageNumList() {
		return pageNumList;
	}

	public void setPageNumList(List<?> pageNumList) {
		this.pageNumList = pageNumList;
	}

	public List<?> getIds() {
		return ids;
	}

	public void setIds(List<?> ids) {
		this.ids = ids;
	}
}
