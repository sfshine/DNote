package com.ndialog.db;

import java.util.ArrayList;
import java.util.List;

import com.ndialog.entity.DetailEntity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DetailEntityDb {
	private DBOpenHelper dbOpenHelper;

	public DetailEntityDb(Context context) {
		this.dbOpenHelper = new DBOpenHelper(context);
	}

	public void save(DetailEntity detailEntity) {
		// 如果要对数据进行更改，就调用此方法得到用于操作数据库的实例,该方法以读和写方式打开数据库
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("insert into note (text,time,mark,author) values(?,?,?,?)",
				new Object[] { detailEntity.getText(), detailEntity.getTime(),
						detailEntity.getMark(), detailEntity.getLayoutID() });
	}

	public void update(DetailEntity detailEntity) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("update note set text=? where time=?", new Object[] {
				detailEntity.getText(), detailEntity.getTime() });
	}

	public void updateMark(DetailEntity detailEntity) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("update note set mark=? where time=?", new Object[] {
				detailEntity.getMark(), detailEntity.getTime() });
	}

	public void delete(String time) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("delete from note where time=?", new Object[] { time });
	}

	// CREATE TABLE note (noteid integer primary key autoincrement,
	// content varchar(500), date varchar(16), author integer)"
	public List<DetailEntity> getScrollData(Integer offset, Integer maxResult) {
		List<DetailEntity> detailEntitys = new ArrayList<DetailEntity>();
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from note limit ?,?",
				new String[] { offset.toString(), maxResult.toString() });
		while (cursor.moveToNext()) {
			// int id = cursor.getInt(cursor.getColumnIndex("noteid"));
			int mark = cursor.getInt(cursor.getColumnIndex("mark"));
			int authorid = cursor.getInt(cursor.getColumnIndex("author"));
			String text = cursor.getString(cursor.getColumnIndex("text"));
			String time = cursor.getString(cursor.getColumnIndex("time"));
			DetailEntity detailEntity = new DetailEntity(text, time, mark,
					authorid);
			detailEntitys.add(detailEntity);
		}
		cursor.close();
		return detailEntitys;
	}

	public long getCount() {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(*) from note", null);
		cursor.moveToFirst();
		// cursor.close();
		return cursor.getLong(0);
	}
}
