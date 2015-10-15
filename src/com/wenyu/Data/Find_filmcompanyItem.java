package com.wenyu.Data;

import java.io.Serializable;

public class Find_filmcompanyItem  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String project;
	private String address;
	private String time;
	private String id;
	
	public Find_filmcompanyItem(String project, String address, String time,
			String id) {
		super();
		this.project = project;
		this.address = address;
		this.time = time;
		this.id = id;
	}
	public Find_filmcompanyItem(String project, String address, String time) {
		super();
		this.project = project;
		this.address = address;
		this.time = time;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Find_filmcompanyItem [project=" + project + ", address="
				+ address + ", time=" + time + ", id=" + id + "]";
	}
	

}
