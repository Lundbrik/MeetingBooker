package com.desc.meetingbooker;

/**
 * A class which is used to represent a time window, i.e. A window of time
 * where there the calendar is not booked
 * 
 * @version 0.9
 * @author Carl Johnsen, Daniel Pedersen, Emil Pedersen and Sune Bartels
 * @since 14-05-2013
 */
public class TimeWindow {
	
	private long start;
	private long end;
	
	/**
	 * Creates a new TimeWindow
	 * 
	 * @param start The start time of the window
	 * @param end The end time of the window
	 */
	public TimeWindow(final long start, final long end) {
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Start time getter
	 * 
	 * @return The start time of the window
	 */
	public long getStart() {
		return this.start;
	}
	
	/**
	 * End time getter
	 * 
	 * @return The end time of the window
	 */
	public long getEnd() {
		return this.end;
	}

}
