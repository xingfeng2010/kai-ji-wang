package com.wenyu.Data;

import java.io.Serializable;
import java.util.List;

public class Reera implements Serializable{
private List<String> type;
private List<String> era;
public Reera(List<String> type, List<String> era) {
	super();
	this.type = type;
	this.era = era;
}
public List<String> getType() {
	return type;
}
public void setType(List<String> type) {
	this.type = type;
}
public List<String> getEra() {
	return era;
}
public void setEra(List<String> era) {
	this.era = era;
}
@Override
public String toString() {
	return "Reera [type=" + type + ", era=" + era + "]";
}



}
