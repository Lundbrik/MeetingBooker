package com.desc.meetingbooker;

import java.text.Format;

public class CalEvent {

	private Long startTime;
	private Long endTime;
	private String title;
	private String description;
	private Format datF;
	// status : 0 not started yet, 1 underway, -1 done
	private int status;
	private String id;
	
	public CalEvent(long sT, long eT, String tit, String desc, Format tf, int stat, String id) {
		startTime = sT;
		endTime = eT;
		title = tit;
		description = desc;
		datF = tf;
		status = stat;
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
			   "status : " + status;
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getId() {
		return this.id;
	}
	
	public boolean equals(CalEvent e) {
		return this.id.equals(e.getId());	
	}
	
}
