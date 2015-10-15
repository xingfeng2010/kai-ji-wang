package com.wenyu.Data;

import java.io.Serializable;
import java.util.List;

public class Pscd implements Serializable {
private  List<String> type;
private List<String> regional;
private List<String> orderby;


public Pscd(List<String> type1, List<String> regional1, List<String> orderby1) {
	this.type = type1;
	this.regional = regional1;
	this.orderby = orderby1;
}



public Pscd(List<String> type, List<String> orderby) {
	super();
	this.type = type;
	this.orderby = orderby;
}



public List<String> getType() {
	return type;
}


public void setType(List<String> type) {
	this.type = type;
}


public List<String> getRegional() {
	return regional;
}


public void setRegional(List<String> regional) {
	this.regional = regional;
}


public List<String> getOrderby() {
	return orderby;
}


public void setOrderby(List<String> orderby) {
	this.orderby = orderby;
}


@Override
public String toString() {
	return "Pscd [type=" + type + ", regional=" + regional + ", orderby="
			+ orderby + "]";
}


}
