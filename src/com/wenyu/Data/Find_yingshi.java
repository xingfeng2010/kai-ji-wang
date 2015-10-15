package com.wenyu.Data;

import java.io.Serializable;

public class Find_yingshi implements Serializable {

	/**
	 * test
	 */
	private static final long serialVersionUID = 1L;
	private String picture;
	private String name;
	private String id;
	private String countV;
	
	public Find_yingshi(String picture, String name, String id) {
		super();
		this.picture = picture;
		this.name = name;
		this.id = id;
	}

	public Find_yingshi(String picture, String name, String id, String countV) {
		super();
		this.picture = picture;
		this.name = name;
		this.id = id;
		this.countV = countV;
	}
	
	public Find_yingshi(String picture, String name) {
		super();
		this.picture = picture;
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCountV() {
		return countV;
	}
	public void setCountV(String countV) {
		this.countV = countV;
	}
	@Override
	public String toString() {
		return "Find_yingshi [picture=" + picture + ", name=" + name + ", id="
				+ id + ", countV=" + countV + "]";
	}




}
