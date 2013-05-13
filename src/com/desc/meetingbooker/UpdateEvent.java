package com.desc.meetingbooker;

import java.util.Date;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CalendarContract.Events;

public class UpdateEvent {
	
	public static void updateStart(CalEvent event, Context context) {
		// Update events start time
		ContentResolver cr = context.getContentResolver();
		ContentValues cv = new ContentValues();
		Uri uri = null;
		cv.put(Events.DTSTART, new Date().getTime());
		uri = ContentUris.withAppendedId(Events.CONTENT_URI, event.getId());
		cr.update(uri, cv, null, null);
		MainActivity.sync();
	}
	
	public static void updateEnd(CalEvent event, Context context) {
		// Update events end time
		ContentResolver cr = context.getContentResolver();
		ContentValues cv = new ContentValues();
		Uri uri = null;
		cv.put(Events.DTEND, new Date().getTime());
		uri = ContentUris.withAppendedId(Events.CONTENT_URI, event.getId());
		cr.update(uri, cv, null, null);
		MainActivity.sync();
	}
	
	public static void updateEnd(CalEvent event, Context context, long time) {
		// Update events end time
		ContentResolver cr = context.getContentResolver();
		ContentValues cv = new ContentValues();
		Uri uri = null;
		cv.put(Events.DTEND, time);
		uri = ContentUris.withAppendedId(Events.CONTENT_URI, event.getId());
		cr.update(uri, cv, null, null);
	}

}
