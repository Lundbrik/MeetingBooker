package com.desc.meetingbooker;

import java.util.Comparator;

public class CustomComparator implements Comparator<CalEvent> {
	
	@Override
	public int compare(CalEvent e1, CalEvent e2) {
		return e1.getStart().compareTo(e2.getStart());
	}
	
}