package com.ndialog.entity;

public class DetailEntity {
	private String time;
	private String text;
	private int layoutID;
	private int id;
	private int mark;

	public String getTime() {
		return time;
	}

	public int getId() {
		return id;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getLayoutID() {
		return layoutID;
	}

	public void setLayoutID(int layoutID) {
		this.layoutID = layoutID;
	}

	public DetailEntity() {
	}

	public DetailEntity(String text, String time, int mark,int layoutID) {
		super();
		// setId(id);
		this.mark = mark;
		this.time = time;
		this.text = text;
		this.layoutID = layoutID;

	}

}
