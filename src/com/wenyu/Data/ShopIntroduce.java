package com.wenyu.Data;

import java.io.Serializable;

public class ShopIntroduce implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String name;
	private String address;
	private String regional;
	private String contact;
	private String telephone;
	private String introduce;
	private String groupname;
	private String value;
	private String peoplename;
	public ShopIntroduce(String name, String address, String regional,
			String contact, String telephone, String introduce,
			String groupname, String value, String peoplename) {
		super();
		this.name = name;
		this.address = address;
		this.regional = regional;
		this.contact = contact;
		this.telephone = telephone;
		this.introduce = introduce;
		this.groupname = groupname;
		this.value = value;
		this.peoplename = peoplename;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRegional() {
		return regional;
	}
	public void setRegional(String regional) {
		this.regional = regional;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPeoplename() {
		return peoplename;
	}
	public void setPeoplename(String peoplename) {
		this.peoplename = peoplename;
	}
	@Override
	public String toString() {
		return "ShopIntroduce [name=" + name + ", address=" + address
				+ ", regional=" + regional + ", contact=" + contact
				+ ", telephone=" + telephone + ", introduce=" + introduce
				+ ", groupname=" + groupname + ", value=" + value
				+ ", peoplename=" + peoplename + "]";
	}
	
	
	

}
