package com.wenyu.Data;

import java.io.Serializable;

public class FindFour implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String address;
	private String contact;
	private String image;
	private String phone;
	
	public FindFour(String id, String name, String address, String contact,
			String image, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.image = image;
		this.phone = phone;
	}
	public FindFour(String id, String name, String address, String contact,
			String image) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.image = image;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "FindFour [id=" + id + ", name=" + name + ", address=" + address
				+ ", contact=" + contact + ", image=" + image + ", phone="
				+ phone + "]";
	}
	
	
	

}
