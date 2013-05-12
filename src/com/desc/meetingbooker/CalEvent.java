package com.desc.meetingbooker;

import java.text.Format;

public class CalEvent {

	private Long startTime;
	private Long endTime;
	private String title;
	private String description;
	private String organizer;
	private Format datF;
	private boolean isUnderway;
	private long id;
	private boolean isOverTime = false;
	
	public CalEvent(long sT, long eT, String tit, String desc, Format tf, boolean iU, long id, String organizer) {
		startTime = sT;
		endTime = eT;
		title = tit;
		description = desc;
		datF = tf;
		isUnderway = iU;
		this.id = id;
		this.organizer = organizer;
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
	
	public String getOrganizer() {
		return organizer;
	}
	
	public String toString() {
		return "Title : " + title + "\n" +
			   "Start : " + datF.format(startTime) + "\n" +
			   "End : " + datF.format(endTime) + "\n" + 
			   "Description : " + description + "\n" +
			   "isUnderway : " + isUnderway + "\n" + 
			   "Organizer : " + organizer;
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
	
	public String getStartTime() {
		return datF.format(startTime);
	}
	
	public String getEndTime() {
		return datF.format(endTime);
	}
	
	public void setOverTime() {
		isOverTime = true;
	}
	
	public boolean getOverTime() {
		return isOverTime;
	}
	
}
