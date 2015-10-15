package com.wenyu.Data;

import java.io.Serializable;

public class IntrovalueData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String value;
	public IntrovalueData(String value) {
		super();
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "IntrovalueData [value=" + value + "]";
	}
	
	

}
