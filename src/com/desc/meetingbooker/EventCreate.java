package com.desc.meetingbooker;

import java.util.Calendar;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CalendarContract;

public class EventCreate {
	
	public static final EventCreate instance = new EventCreate();
	
	private EventCreate() {
	}
	
	public void setNewEvent(CalEvent event, Context context) {
		
		Uri EVENTS_URI = Uri.parse(CalendarContract.Events.CONTENT_URI + "events");
		ContentResolver cr = context.getContentResolver();
		
		ContentValues values = new ContentValues();
		values.put("calendar_id", 1);
		values.put("title", event.getTitle());
		values.put("allDay", 0);
		values.put("dtstart", event.getStart());
		values.put("dtend", event.getEnd());
		values.put("description", event.getDescription());
		values.put("availability", 0);
		values.put("hasalarm", 0);
		Uri ev = cr.insert(EVENTS_URI, values);
		
	}

}
