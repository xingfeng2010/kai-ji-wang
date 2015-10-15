package com.wenyu.Data;

public class MyMessage {
	private  String name;
	private  String desc;
	public MyMessage(String name, String desc) {
		super();
		this.name = name;
		this.desc = desc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setImag(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "MyMessage [name=" + name + ", desc=" + desc + "]";
	}

}
