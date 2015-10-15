package com.wenyu.Data;

public class FindFilm {
private String name ;

public FindFilm(String name) {
	super();
	this.name = name;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

@Override
public String toString() {
	return "FindFilm [name=" + name + "]";
}

}
