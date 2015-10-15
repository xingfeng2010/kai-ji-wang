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
		// 操作数据库
		String sql = "create table customer(_id integer , phonenumber varchar(20), password varchar(40),active int,certify int);";
		db.execSQL(sql);		// 创建person表
		String connect = "create table connect(_id integer unique  primary key autoincrement );";
		db.execSQL(connect);
	}
	/**
	 * 数据库的版本号更新时回调此方法,
	 * 更新数据库的内容(删除表, 添加表, 修改表)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion == 1 && newVersion == 2) {
			Log.i(TAG, "数据库更新啦");

		}

	}

}
