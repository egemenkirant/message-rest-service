package com.sweagle.message.model;

public class MessageCountEntity {
	
	
	private int dayOfYear;
	private int week;
	private int count;
	
	public MessageCountEntity() {
		super();
	}

	public int getDayOfYear() {
		return dayOfYear;
	}

	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
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
		return "MessageCountEntity [dayOfMonth=" + dayOfYear + ", week=" + week + ", count=" + count + "]";
	}

	
	

}
