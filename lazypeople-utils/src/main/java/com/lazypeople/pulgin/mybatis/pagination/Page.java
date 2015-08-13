package com.lazypeople.pulgin.mybatis.pagination;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Page information
 * 
 * @author twu
 * 
 */
public class Page implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2635651952307048652L;

	private int rowCount = -1;
	private int currentPage = 0;
	private int pageSize = Integer.MAX_VALUE;
	private String[] orderBys;

	public Page() {
	}

	public Page(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public Page(int currentPage, int pageSize, String... orderBy) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		setOrderBys(orderBy);
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String[] getOrderBys() {
		return orderBys;
	}

	public void setOrderBys(String[] orderBys) {
		this.orderBys = orderBys;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPage;
		result = prime * result + Arrays.hashCode(orderBys);
		result = prime * result + pageSize;
		result = prime * result + rowCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page other = (Page) obj;
		if (currentPage != other.currentPage)
			return false;
		if (!Arrays.equals(orderBys, other.orderBys))
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (rowCount != other.rowCount)
			return false;
		return true;
	}
}
