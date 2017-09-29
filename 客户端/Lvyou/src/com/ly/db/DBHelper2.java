package com.ly.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper2 extends SQLiteOpenHelper {
	//旅游记忆保存本地
	private static String datebase_name = "memory_datebase";
	public static String table_name = "memory_table2";
	public DBHelper2(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
	
	}
	 public DBHelper2(Context c){
	    	this(c,datebase_name,null,1);
	 }

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("create table "+table_name+" (memory_id integer primary key autoincrement,memory_title text,memory_content text,memory_address text,memory_time text,m_u_id text)");
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table "+table_name);
		this.onCreate(db);
	}

}
