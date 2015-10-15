package com.wenyu.Data;

import java.io.Serializable;
import java.util.List;

public class EquipDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	private String tempid;
	private  String count;
	private List<String> images;
	private List<Fields> fields;
	public EquipDetail(String name, String id, String tempid, String count,
			List<String> images, List<Fields> fields) {
		super();
		this.name = name;
		this.id = id;
		this.tempid = tempid;
		this.count = count;
		this.images = images;
		this.fields = fields;
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
	public String getTempid() {
		return tempid;
	}
	public void setTempid(String tempid) {
		this.tempid = tempid;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public List<Fields> getFields() {
		return fields;
	}
	public void setFields(List<Fields> fields) {
		this.fields = fields;
	}
	@Override
	public String toString() {
		return "EquipDetail [name=" + name + ", id=" + id + ", tempid="
				+ tempid + ", count=" + count + ", images=" + images
				+ ", fields=" + fields + "]";
	}
	
	
}
