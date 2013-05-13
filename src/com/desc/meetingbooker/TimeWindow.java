package com.desc.meetingbooker;

public class TimeWindow {
	
	private long start;
	private long end;
	
	public TimeWindow(final long start, final long end) {
		this.start = start;
		this.end = end;
	}
	
	public long getStart() {
		return this.start;
	}
	
	public long getEnd() {
		return this.end;
	}

}
