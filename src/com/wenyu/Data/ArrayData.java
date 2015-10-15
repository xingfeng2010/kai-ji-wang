package com.wenyu.Data;

import java.io.Serializable;
import java.util.List;

public class ArrayData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> name;
	private List<String> value;
	public ArrayData(List<String> name, List<String> value) {
		super();
		this.name = name;
		this.value = value;
	}
	public List<String> getName() {
		return name;
	}
	public void setName(List<String> name) {
		this.name = name;
	}
	public List<String> getValue() {
		return value;
	}
	public void setValue(List<String> value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "ArrayData [name=" + name + ", value=" + value + "]";
	}
	
}
