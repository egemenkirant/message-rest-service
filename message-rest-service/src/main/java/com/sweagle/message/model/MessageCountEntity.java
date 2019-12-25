package com.sweagle.message.model;

public class MessageCountEntity {
	
	
	private int dayOfMonth;
	private int week;
	private int count;
	
	public MessageCountEntity() {
		super();
	}

	public int getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "MessageCountEntity [dayOfMonth=" + dayOfMonth + ", week=" + week + ", count=" + count + "]";
	}

	
	

}
