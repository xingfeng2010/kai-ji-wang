package com.wenyu.Data;

import java.io.Serializable;

public class ProductData implements Serializable {
/**
	 * 
	 */
private static final long serialVersionUID = 1L;
private String id;
private String name;
private String storename;
private String image;
public ProductData(String id, String name, String storename, String image) {
	super();
	this.id = id;
	this.name = name;
	this.storename = storename;
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
public String getStorename() {
	return storename;
}
public void setStorename(String storename) {
	this.storename = storename;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
@Override
public String toString() {
	return "ProductData [id=" + id + ", name=" + name + ", storename="
			+ storename + ", image=" + image + "]";
}

}
