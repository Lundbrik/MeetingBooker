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
		// UPDATE
		ContentResolver cr = context.getContentResolver();
		ContentValues cv = new ContentValues();
		Uri uri = null;
		cv.put(Events.DTSTART, new Date().getTime());
		uri = ContentUris.withAppendedId(Events.CONTENT_URI, event.getId());
		int rows = cr.update(uri, cv, null, null);
		MainActivity.sync();
	}
	
	public static void updateEnd(CalEvent event) {
		// UPDATE
	}

}
