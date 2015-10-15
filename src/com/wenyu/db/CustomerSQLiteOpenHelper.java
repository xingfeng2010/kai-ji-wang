package com.wenyu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CustomerSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String TAG = "CustomerSQLiteOpenHelper";

	public CustomerSQLiteOpenHelper(Context context) {
		super(context, "filmon.db1", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// �������ݿ�
		String sql = "create table customer(_id integer , phonenumber varchar(20), password varchar(40),active int,certify int);";
		db.execSQL(sql);		// ����person��
		String connect = "create table connect(_id integer unique  primary key autoincrement );";
		db.execSQL(connect);
	}
	/**
	 * ���ݿ�İ汾�Ÿ���ʱ�ص��˷���,
	 * �������ݿ������(ɾ����, ��ӱ�, �޸ı�)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion == 1 && newVersion == 2) {
			Log.i(TAG, "���ݿ������");

		}

	}

}
