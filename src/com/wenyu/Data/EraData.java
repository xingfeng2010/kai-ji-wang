package com.wenyu.Data;

import java.io.Serializable;
import java.util.List;

public class EraData implements Serializable {
private List<String> era;

public EraData(List<String> era) {
	super();
	this.era = era;
}

public List<String> getEra() {
	return era;
}

public void setEra(List<String> era) {
	this.era = era;
}

@Override
public String toString() {
	return "EraData [era=" + era + "]";
}

}
