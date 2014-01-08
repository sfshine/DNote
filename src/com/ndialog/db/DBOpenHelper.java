package com.ndialog.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String DATABASENAME = "popnote.db"; // 数据库名称
	private static final int DATABASEVERSION = 1;// 数据库版本 如果更改了安装的时候会执行 onupgrade方法

	public DBOpenHelper(Context context) {
		super(context, DATABASENAME, null, DATABASEVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS note (noteid integer primary key autoincrement, text varchar(500), time varchar(16), mark integer, author integer)");// 执行有更改的sql语句
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("ALTER TABLE note ADD COLUMN marktes integer");//增减一项 保存用户数据
		// 但是注意调用的时候需要使用SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		// ALTER TABLE Teachers ADD COLUMN Sex text;
		//db.execSQL("DROP TABLE IF EXISTS note");
		onCreate(db);
	}

}
