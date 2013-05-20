package com.desc.meetingbooker;

import java.util.ArrayList;

public class CalendarChecker {
	
	public static boolean isFree(CalEvent event) {
		ArrayList<CalEvent> eventlist = MainActivity.eventlist;
		eventlist.add(MainActivity.current);
		for (CalEvent ev : eventlist) {
			if (ev.getStart() > event.getStart() && ev.getStart() < event.getEnd()) {
				return false;
			}
			if (ev.getEnd() > event.getStart() && ev.getEnd() < event.getEnd()) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isUpdatable(CalEvent event, int index) {
		ArrayList<CalEvent> eventlist = MainActivity.eventlist;
		if (!(index == -1)) {
			eventlist.add(MainActivity.current);
			eventlist.remove(index);
		} 
		if (eventlist.isEmpty()) {
			return true;
		}
		for (CalEvent ev : eventlist) {
			if (ev.getStart() > event.getStart() && ev.getStart() < event.getEnd()) {
				return false;
			}
			if (ev.getEnd() > event.getStart() && ev.getEnd() < event.getEnd()) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isBefore(CalEvent event) {
		return event.getEnd() < event.getStart();
	}

}
