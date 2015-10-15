package com.wenyu.Data;

import java.util.List;

public class ReFindVistData {
private List<String> years;
private List<String> names;
private List<String> positions;
private List<String>filmtype ;


public ReFindVistData(List<String> years, List<String> names,
		List<String> positions, List<String> filmtype) {
	super();
	this.years = years;
	this.names = names;
	this.positions = positions;
	this.filmtype = filmtype;
}


public List<String> getFilmtype() {
	return filmtype;
}


public void setFilmtype(List<String> filmtype) {
	this.filmtype = filmtype;
}


public List<String> getYears() {
	return years;
}
public void setYears(List<String> years) {
	this.years = years;
}
public List<String> getNames() {
	return names;
}
public void setNames(List<String> names) {
	this.names = names;
}
public List<String> getPositions() {
	return positions;
}
public void setPositions(List<String> positions) {
	this.positions = positions;
}
@Override
public String toString() {
	return "ReFindVistData [years=" + years + ", names=" + names
			+ ", positions=" + positions + ", filmtype=" + filmtype + "]";
}

}
