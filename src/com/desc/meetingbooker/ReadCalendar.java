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

public class ReadCalendar {
	
	private static final String[] COLS = new String[] {
		CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, 
		CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION
	};
	
	public static Cursor cursor;
	
	public static ArrayList<CalEvent> readCalendar(Context context) {
		
		ArrayList<CalEvent> eventlist = new ArrayList<CalEvent>();
		
		ContentResolver contentResolver = context.getContentResolver();
		cursor = contentResolver.query(CalendarContract.Events.CONTENT_URI, COLS, null, null, null);
		cursor.moveToFirst();
		
		Format df = DateFormat.getDateFormat(context);
		Format tf = DateFormat.getTimeFormat(context);
		
		Long start = 0L;

		Date dat = new Date();
		String today = df.format(dat.getTime());
		
		while (!cursor.isAfterLast()) {
			start = cursor.getLong(0);
			String st = df.format(start);
			int stat = 0;
			if (start < new Date().getTime()) {
				stat = 1;
			}
			if (cursor.getLong(1) < new Date().getTime()) {
				stat = -1;
			}
			if(today.equals(st)) {
				eventlist.add(new CalEvent(cursor.getLong(0), cursor.getLong(1), 
										   cursor.getString(2), cursor.getString(3),
										   tf, stat));
			}
			cursor.moveToNext();
			
		}

		Collections.sort(eventlist, new CustomComparator());
		
		return eventlist;
	}

}
