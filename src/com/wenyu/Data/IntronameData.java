package com.wenyu.Data;

import java.io.Serializable;

public class IntronameData  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	public IntronameData(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "IntroData [name=" + name + "]";
	}
	

}
