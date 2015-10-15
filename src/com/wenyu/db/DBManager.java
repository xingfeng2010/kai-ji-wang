package com.wenyu.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wenyu.Data.Customer;

public class DBManager {
	private CustomerSQLiteOpenHelper helper;
	private SQLiteDatabase db;

	public DBManager(Context context){
		helper = new CustomerSQLiteOpenHelper(context);
		db = helper.getWritableDatabase();	 	  
	}
	public void add(Customer customer){
		db.beginTransaction();
		try{
			db.execSQL("INSERT INTO customer(_id,phonenumber,password,active,certify) VALUES(?,?,?,?,?)",new Object[]{customer.getId(),customer.getPhonenumber(),customer.getPassword(),customer.getActive(),customer.getCertify()});
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();		 
		}	 
	}
	public List<Customer> query(){
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Cursor c = queryCursor();
		while(c.moveToNext()){
			Customer customer = new Customer();
			customer.setId(c.getInt(c.getColumnIndex("_id")));
			customer.setPhonenumber(c.getString(c.getColumnIndex("phonenumber")));
			customer.setPassword(c.getString(c.getColumnIndex("password")));
			customer.setActive(c.getInt(c.getColumnIndex("active")));
			customer.setCertify(c.getInt(c.getColumnIndex("certify")));
			customers.add(customer);		  
		}
		c.close();
		return customers;
	}
	public void updateActive(Customer customer){
		db.beginTransaction();
		try{
			db.execSQL("UPDATE customer set active = ? where phonenumber = ?;",new Object[]{customer.getActive(),customer.getPhonenumber()});
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();		 
		}	 

	}
	public void updatePassword(Customer customer) {
		db.beginTransaction();
		try{
			db.execSQL("UPDATE customer set password = ? where phonenumber = ?;",new Object[]{customer.getPassword(),customer.getPhonenumber()});
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();		 
		}	 

	}
	public void updateCertify(Customer customer){
		db.beginTransaction();
		try{
			db.execSQL("UPDATE customer set certify = ? where phonenumber = ?;",new Object[]{customer.getCertify(),customer.getPhonenumber()});
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();		 
		}	 

	}
	public Customer queryItem(String phonenumber){

		Cursor c = db.rawQuery("select _id,phonenumber, password,active,certify from customer where phonenumber = ?;",  new String[]{phonenumber + ""});
		if(c != null && c.moveToFirst()) {
			int _id = c.getInt(0);
			String phone = c.getString(1);
			String password = c.getString(2);
			int active = c.getInt(3);
			int certify = c.getInt(4);
			return new Customer(_id, phone, password,active,certify);

		}
		return null;

	}
	public Cursor queryCursor(){
		Cursor c = db.rawQuery("SELECT * FROM customer", null);
		return c;	  
	}
	public void closeDB(){
		db.close();
	}

}
