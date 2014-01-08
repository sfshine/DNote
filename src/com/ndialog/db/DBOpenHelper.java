package com.ndialog.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String DATABASENAME = "popnote.db"; // ���ݿ�����
	private static final int DATABASEVERSION = 1;// ���ݿ�汾 ��������˰�װ��ʱ���ִ�� onupgrade����

	public DBOpenHelper(Context context) {
		super(context, DATABASENAME, null, DATABASEVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS note (noteid integer primary key autoincrement, text varchar(500), time varchar(16), mark integer, author integer)");// ִ���и��ĵ�sql���
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("ALTER TABLE note ADD COLUMN marktes integer");//����һ�� �����û�����
		// ����ע����õ�ʱ����Ҫʹ��SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		// ALTER TABLE Teachers ADD COLUMN Sex text;
		//db.execSQL("DROP TABLE IF EXISTS note");
		onCreate(db);
	}

}
