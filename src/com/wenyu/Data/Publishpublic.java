package com.wenyu.Data;

import java.io.Serializable;

public class Publishpublic implements Serializable {
private String idqi;
private String idshop;
private String name;
private String coverimage;
private String price;
private String number;
private String type;
private String category;
private String contact;
private String telephone;

public Publishpublic(String idqi, String name, String coverimage) {
	super();
	this.idqi = idqi;
	this.name = name;
	this.coverimage = coverimage;
}

public Publishpublic(String idqi, String idshop, String name,
		String coverimage, String price, String number, String type,
		String category, String contact, String telephone) {
	super();
	this.idqi = idqi;
	this.idshop = idshop;
	this.name = name;
	this.coverimage = coverimage;
	this.price = price;
	this.number = number;
	this.type = type;
	this.category = category;
	this.contact = contact;
	this.telephone = telephone;
}

public String getIdqi() {
	return idqi;
}
public void setIdqi(String idqi) {
	this.idqi = idqi;
}
public String getIdshop() {
	return idshop;
}
public void setIdshop(String idshop) {
	this.idshop = idshop;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getCoverimage() {
	return coverimage;
}
public void setCoverimage(String coverimage) {
	this.coverimage = coverimage;
}
public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
}
public String getNumber() {
	return number;
}
public void setNumber(String number) {
	this.number = number;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
public String getContact() {
	return contact;
}
public void setContact(String contact) {
	this.contact = contact;
}
public String getTelephone() {
	return telephone;
}
public void setTelephone(String telephone) {
	this.telephone = telephone;
}
@Override
public String toString() {
	return "Publishpublic [idqi=" + idqi + ", idshop=" + idshop + ", name="
			+ name + ", coverimage=" + coverimage + ", price=" + price
			+ ", number=" + number + ", type=" + type + ", category="
			+ category + ", contact=" + contact + ", telephone=" + telephone
			+ "]";
}

}
