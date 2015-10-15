package com.wenyu.Data;

import java.io.Serializable;

public class SearchData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String history;
	
	
	public SearchData(String history) {
		super();
		this.history = history;
	}


	public String getHistory() {
		return history;
	}


	public void setHistory(String history) {
		this.history = history;
	}


	

}
