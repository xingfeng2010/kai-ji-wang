package com.wenyu.Data;

import java.io.Serializable;

public class Similar  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String regional;
	private String image;
	private String id;
	
	

	public Similar(String name, String address, String regional, String image,
			String id) {
		super();
		this.name = name;
		this.address = address;
		this.regional = regional;
		this.image = image;
		this.id = id;
	}

	public Similar(String name, String address, String regional) {
		super();
		this.name = name;
		this.address = address;
		this.regional = regional;
	}

	
	
	public Similar(String name, String address, String regional, String image) {
		super();
		this.name = name;
		this.address = address;
		this.regional = regional;
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
	public String getRegional() {
		return regional;
	}
	public void setRegional(String regional) {
		this.regional = regional;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Similar [name=" + name + ", address=" + address + ", regional="
				+ regional + ", image=" + image + ", id=" + id + "]";
	}
	

}
