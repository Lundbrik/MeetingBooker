package com.desc.meetingbooker;

import java.util.TimeZone;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;

public class EventCreate {
	
	public static final EventCreate instance = new EventCreate();
	
	private EventCreate() {
	}
	
	public void setNewEvent(CalEvent event, Context context) {
		
		Uri EVENTS_URI = Uri.parse(CalendarContract.Events.CONTENT_URI.toString());
		ContentResolver cr = context.getContentResolver();
		
		ContentValues values = new ContentValues();
		values.put("calendar_id", 1);
		values.put("title", event.getTitle());
		values.put("allDay", 0);
		values.put("dtstart", event.getStart());
		values.put("dtend", event.getEnd());
		values.put("description", event.getDescription());
		values.put("availability", 0);
		values.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());
		cr.insert(EVENTS_URI, values);
		
	}

}
