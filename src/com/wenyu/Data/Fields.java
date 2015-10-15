package com.wenyu.Data;

import java.io.Serializable;
import java.util.List;

public class Fields implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String gid;
	private List<ArrayData> ads;
	public Fields(String name, String gid, List<ArrayData> ads) {
		super();
		this.name = name;
		this.gid = gid;
		this.ads = ads;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public List<ArrayData> getAds() {
		return ads;
	}
	public void setAds(List<ArrayData> ads) {
		this.ads = ads;
	}
	@Override
	public String toString() {
		return "Fields [name=" + name + ", gid=" + gid + ", ads=" + ads + "]";
	}
	

}
