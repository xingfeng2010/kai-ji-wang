package com.wenyu.Data;

import java.io.Serializable;
import java.util.List;

public class ReFindXiang  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String age;
	private String address;
	private String position;
	private String telphone;
	private String send_id;
	private List<String> namelist;
	private List<String> valuelist;
	public ReFindXiang(String name, String age, String address,
			String position, String telphone, String send_id,
			List<String> namelist, List<String> valuelist) {
		super();
		this.name = name;
		this.age = age;
		this.address = address;
		this.position = position;
		this.telphone = telphone;
		this.send_id = send_id;
		this.namelist = namelist;
		this.valuelist = valuelist;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getSend_id() {
		return send_id;
	}
	public void setSend_id(String send_id) {
		this.send_id = send_id;
	}
	public List<String> getNamelist() {
		return namelist;
	}
	public void setNamelist(List<String> namelist) {
		this.namelist = namelist;
	}
	public List<String> getValuelist() {
		return valuelist;
	}
	public void setValuelist(List<String> valuelist) {
		this.valuelist = valuelist;
	}
	@Override
	public String toString() {
		return "ReFindXiang [name=" + name + ", age=" + age + ", address="
				+ address + ", position=" + position + ", telphone=" + telphone
				+ ", send_id=" + send_id + ", namelist=" + namelist
				+ ", valuelist=" + valuelist + "]";
	}
	
}
