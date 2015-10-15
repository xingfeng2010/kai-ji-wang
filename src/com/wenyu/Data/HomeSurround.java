package com.wenyu.Data;

import java.io.Serializable;

public class HomeSurround implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lat;
	private String Ing;
	private String name;
	private String image;
	private String value;
	//	public HomeSurround(String lat, String ing, String name, String image) {
	//		super();
	//		this.lat = lat;
	//		Ing = ing;
	//		this.name = name;
	//		this.image = image;
	//	}

	public HomeSurround(String lat, String ing, String name, String value) {
		super();
		this.lat = lat;
		Ing = ing;
		this.name = name;
		this.value = value;
	}

	public HomeSurround(String lat, String ing, String name) {
		super();
		this.lat = lat;
		Ing = ing;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getIng() {
		return Ing;
	}
	public void setIng(String ing) {
		Ing = ing;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "HomeSurround [lat=" + lat + ", Ing=" + Ing + ", name=" + name
				+ ", image=" + image + ", value=" + value + "]";
	}




}
