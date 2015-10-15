package com.wenyu.Data;

import java.io.Serializable;

public class Myone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int  image;

	public Myone(int image) {
		super();
		this.image = image;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "My1 [image=" + image + "]";
	}
	
	
}
