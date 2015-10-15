package com.wenyu.Data;

import java.io.Serializable;
import java.util.List;

public class IntroData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<IntronameData> listname;
	private List<IntrovalueData> listvalue;
	private String contact;
	private String telephone;
	private String address;
	private String regional;
	private String tempid;
	private String cdname;
	private String id;
	private String x,y;
	private String send_to_id;
	private String type;
	private String count;
	public IntroData(List<IntronameData> listname,
			List<IntrovalueData> listvalue, String contact, String telephone,
			String address, String regional, String tempid, String cdname,
			String id) {
		super();
		this.listname = listname;
		this.listvalue = listvalue;
		this.contact = contact;
		this.telephone = telephone;
		this.address = address;
		this.regional = regional;
		this.tempid = tempid;
		this.cdname = cdname;
		this.id = id;
	}


	

	public IntroData(List<IntronameData> listname,
			List<IntrovalueData> listvalue, String contact, String telephone,
			String address, String regional, String tempid, String cdname,
			String id, String x, String y, String send_to_id, String type) {
		super();
		this.listname = listname;
		this.listvalue = listvalue;
		this.contact = contact;
		this.telephone = telephone;
		this.address = address;
		this.regional = regional;
		this.tempid = tempid;
		this.cdname = cdname;
		this.id = id;
		this.x = x;
		this.y = y;
		this.send_to_id = send_to_id;
		this.type = type;
	}




	public IntroData(List<IntronameData> listname,
			List<IntrovalueData> listvalue, String contact, String telephone,
			String address, String regional, String tempid, String cdname,
			String id, String x, String y, String send_to_id) {
		super();
		this.listname = listname;
		this.listvalue = listvalue;
		this.contact = contact;
		this.telephone = telephone;
		this.address = address;
		this.regional = regional;
		this.tempid = tempid;
		this.cdname = cdname;
		this.id = id;
		this.x = x;
		this.y = y;
		this.send_to_id = send_to_id;
	}




	public IntroData(List<IntronameData> listname,
			List<IntrovalueData> listvalue, String contact, String telephone,
			String address, String regional, String tempid, String cdname,
			String id, String x, String y) {
		super();
		this.listname = listname;
		this.listvalue = listvalue;
		this.contact = contact;
		this.telephone = telephone;
		this.address = address;
		this.regional = regional;
		this.tempid = tempid;
		this.cdname = cdname;
		this.id = id;
		this.x = x;
		this.y = y;
	}




	public IntroData(List<IntronameData> listname,
			List<IntrovalueData> listvalue, String contact, String telephone,
			String address, String regional, String tempid, String cdname) {
		super();
		this.listname = listname;
		this.listvalue = listvalue;
		this.contact = contact;
		this.telephone = telephone;
		this.address = address;
		this.regional = regional;
		this.tempid = tempid;
		this.cdname = cdname;
	}
	
	
	
	public IntroData(List<IntronameData> listname,
			List<IntrovalueData> listvalue, String tempid, String cdname,
			String x, String y) {
		super();
		this.listname = listname;
		this.listvalue = listvalue;
		this.tempid = tempid;
		this.cdname = cdname;
		this.x = x;
		this.y = y;
	}


	public IntroData(List<IntronameData> listname,
			List<IntrovalueData> listvalue, String tempid, String cdname) {
		super();
		this.listname = listname;
		this.listvalue = listvalue;
		this.tempid = tempid;
		this.cdname = cdname;
	}


	public IntroData(List<IntronameData> listname,
			List<IntrovalueData> listvalue, String tempid, String cdname,
			String count) {
		super();
		this.listname = listname;
		this.listvalue = listvalue;
		this.tempid = tempid;
		this.cdname = cdname;
		this.count = count;
	}




	public IntroData(List<IntrovalueData> listvalue) {
		super();
		this.listvalue = listvalue;
	}


	public IntroData(List<IntronameData> listname,
			List<IntrovalueData> listvalue) {
		super();
		this.listname = listname;
		this.listvalue = listvalue;
	}

	
	
	public String getSend_to_id() {
		return send_to_id;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public void setSend_to_id(String send_to_id) {
		this.send_to_id = send_to_id;
	}




	public String getCount() {
		return count;
	}




	public void setCount(String count) {
		this.count = count;
	}




	public String getX() {
		return x;
	}


	public void setX(String x) {
		this.x = x;
	}


	public String getY() {
		return y;
	}


	public void setY(String y) {
		this.y = y;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public List<IntronameData> getListname() {
		return listname;
	}
	public void setListname(List<IntronameData> listname) {
		this.listname = listname;
	}
	public List<IntrovalueData> getListvalue() {
		return listvalue;
	}
	public void setListvalue(List<IntrovalueData> listvalue) {
		this.listvalue = listvalue;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRegional() {
		return regional;
	}
	public void setRegional(String regional) {
		this.regional = regional;
	}
	public String getTempid() {
		return tempid;
	}
	public void setTempid(String tempid) {
		this.tempid = tempid;
	}
	public String getCdname() {
		return cdname;
	}
	public void setCdname(String cdname) {
		this.cdname = cdname;
	}




	@Override
	public String toString() {
		return "IntroData [listname=" + listname + ", listvalue=" + listvalue
				+ ", contact=" + contact + ", telephone=" + telephone
				+ ", address=" + address + ", regional=" + regional
				+ ", tempid=" + tempid + ", cdname=" + cdname + ", id=" + id
				+ ", x=" + x + ", y=" + y + ", send_to_id=" + send_to_id
				+ ", type=" + type + ", count=" + count + "]";
	}
	
	
	
	

}
