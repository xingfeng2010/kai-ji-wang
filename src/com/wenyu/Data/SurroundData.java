package com.wenyu.Data;

import java.io.Serializable;

public class SurroundData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int image;
	private String name;
	public SurroundData(int image, String name) {
		super();
		this.image = image;
		this.name = name;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "SurroundData [image=" + image + ", name=" + name + "]";
	}

}
