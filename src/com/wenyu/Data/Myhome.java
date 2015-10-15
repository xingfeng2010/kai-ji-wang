package com.wenyu.Data;

import java.io.Serializable;

public class Myhome implements Serializable{
	private  String name;
	private  String imag;
	private  String type;
	private String id;
	private String introduction;
	private String record;

	public Myhome(String name, String imag, String id) {
		super();
		this.name = name;
		this.imag = imag;
		this.id = id;
	}
	public Myhome(String name, String imag, String type, String id,String introduction,String record) {
		super();
		this.name = name;
		this.imag = imag;
		this.type = type;
		this.id = id;
		this.introduction = introduction;
		this.record = record;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImag() {
		return imag;
	}
	public void setImag(String imag) {
		this.imag = imag;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	@Override
	public String toString() {
		return "Myhome [name=" + name + ", imag=" + imag + "]";
	}

}
