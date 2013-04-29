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
		
		Calendar cal = Calendar.getInstance();
		Uri EVENTS_URI = Uri.parse(CalendarContract.Events.CONTENT_URI + "events");
		ContentResolver cr = context.getContentResolver();
		
		ContentValues values = new ContentValues();
		
	}

}
