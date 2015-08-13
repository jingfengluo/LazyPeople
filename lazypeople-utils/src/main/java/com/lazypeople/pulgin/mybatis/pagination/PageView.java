package com.lazypeople.pulgin.mybatis.pagination;

import java.io.Serializable;
import java.util.Collection;

/**
 * This class use for combine collection output and row count together, mostly
 * in service interface
 * 
 * @author twu
 * 
 * @param <T>
 */
public class PageView<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6839969541735946792L;

	private Collection<T> records;
	private Page page;
	
	public PageView(){	
	}
	
	public PageView(Collection<T> records, Page page){
		this.records = records;
		this.page = page;
	}

	public Collection<T> getRecords() {
		return records;
	}

	public void setRecords(Collection<T> records) {
		this.records = records;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
