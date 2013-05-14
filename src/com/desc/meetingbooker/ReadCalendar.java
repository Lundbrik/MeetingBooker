package com.desc.meetingbooker;

import java.text.Format;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.util.Log;

public class ReadCalendar {
	
	// The query used to get the events from the Android calendar
	private static final String[] COLS = new String[] {
		CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, 
		CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION,
		CalendarContract.Events._ID, CalendarContract.Events.ORGANIZER
	};
	
	public static Cursor cursor;
	
	public static ArrayList<CalEvent> readCalendar(Context context) {
		// The ArrayList to hold the events
		ArrayList<CalEvent> eventlist = new ArrayList<CalEvent>();
		
		
		ContentResolver contentResolver = context.getContentResolver();
		
		// Calling the query
		cursor = contentResolver.query(CalendarContract.Events.CONTENT_URI, COLS, "CALENDAR_ID = 1", null, null);
		cursor.moveToFirst();
		
		// Getting the used DateFormat from the Android device
		Format df = DateFormat.getDateFormat(context);
		Format tf = DateFormat.getTimeFormat(context);
		
		Long start = 0L;

		// Getting the current Date and Time
		Date dat = new Date();
		String today = df.format(dat.getTime());
		
		// Writing all the events to the eventlist
		while (!cursor.isAfterLast()) {
			start = cursor.getLong(0);
			String st = df.format(start);
			boolean isUnderway = false;
			if (start < new Date().getTime()) {
				isUnderway = true;
			}
			if(today.equals(st) && !(cursor.getLong(1) < new Date().getTime())) {
				eventlist.add(new CalEvent(
											cursor.getLong(0), 		// Start time
											cursor.getLong(1), 		// End Time
											cursor.getString(2), 	// Title
											cursor.getString(3),	// Description
											tf, 					// TimeFormat
											isUnderway, 			// Is underway
											cursor.getLong(4), 		// Event ID
											cursor.getString(5)		// Organizer
											));
			}
			cursor.moveToNext();
			
		}
		cursor.close();
		
		// Sorts eventlist by start time
		Collections.sort(eventlist, new CustomComparator());
		
		return eventlist;
	}
	
	public static String getCalendarName(Context context) {
		String[] que = { CalendarContract.Calendars.CALENDAR_DISPLAY_NAME };
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(CalendarContract.Calendars.CONTENT_URI, que, null, null, null);
		cursor.moveToFirst();
		String result = cursor.getString(0);
		cursor.close();
		return result;
	}

}
