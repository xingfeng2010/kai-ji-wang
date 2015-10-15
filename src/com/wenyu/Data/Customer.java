package com.wenyu.Data;

public class Customer {
 private int id;
 private String phonenumber;
 private String password;
 private int active;
 private int certify;
 public Customer(){}
public Customer(int id,String phonenumber, String password,int active,int certify) {
	super();
	this.id = id;
	this.phonenumber = phonenumber;
	this.password = password;
	this.active = active;
	this.certify = certify;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getPhonenumber() {
	return phonenumber;
}
public void setPhonenumber(String phonenumber) {
	this.phonenumber = phonenumber;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

public int getActive() {
	return active;
}
public void setActive(int active) {
	this.active = active;
}

public int getCertify() {
	return certify;
}
public void setCertify(int certify) {
	this.certify = certify;
}
@Override
public String toString() {
	return "Customer [phonenumber=" + phonenumber + ", password=" + password
			+ "]";
}
 
}
