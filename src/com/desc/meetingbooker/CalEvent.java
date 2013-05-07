package com.desc.meetingbooker;

import java.text.Format;

public class CalEvent {

	private Long startTime;
	private Long endTime;
	private String title;
	private String description;
	private Format datF;
	// status : 0 not started yet, 1 underway, -1 done
	private boolean isUnderway;
	private long id;
	
	public CalEvent(long sT, long eT, String tit, String desc, Format tf, boolean iU, long id) {
		startTime = sT;
		endTime = eT;
		title = tit;
		description = desc;
		datF = tf;
		isUnderway = iU;
		this.id = id;
	}
	
	public CalEvent(long sT, long eT, String tit, String desc) {
		startTime = sT;
		endTime = eT;
		title = tit;
		description = desc;
	}
	
	public Long getStart() {
		return startTime;
	}
	
	public Long getEnd() {
		return endTime;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String toString() {
		return "Title : " + title + "\n" +
			   "Start : " + datF.format(startTime) + "\n" +
			   "End : " + datF.format(endTime) + "\n" + 
			   "Description : " + description + "\n" +
			   "isUnderway : " + isUnderway;
	}
	
	public boolean isUnderway() {
		return isUnderway;
	}
	
	public long getId() {
		return this.id;
	}
	
	public boolean equals(CalEvent e) {
		return this.id == e.getId();	
	}
	
}
