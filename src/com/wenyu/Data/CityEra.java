package com.wenyu.Data;

import java.util.List;

public class CityEra {
	private String city;
	private List<EraData> arr;
	public CityEra(String city, List<EraData> arr) {
		super();
		this.city = city;
		this.arr = arr;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<EraData> getArr() {
		return arr;
	}
	public void setArr(List<EraData> arr) {
		this.arr = arr;
	}
	@Override
	public String toString() {
		return "CityEra [city=" + city + ", arr=" + arr + "]";
	}

}
