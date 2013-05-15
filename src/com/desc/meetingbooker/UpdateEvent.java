package com.desc.meetingbooker;

import java.util.Date;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CalendarContract.Events;

/**
 * A class that updates the time of an event
 * 
 * @version 0.9
 * @author Carl Johnsen, Daniel Pedersen, Emil Pedersen and Sune Bartels
 * @since 14-05-2013
 */
public class UpdateEvent {
	
	/**
	 * Changes the start time of the given event, to the current time
	 * 
	 * @param event The event that should be updated
	 * @param context The context of the app, used to extract the CONTENT_URI and the ContentResolver
	 */
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
	
	/**
	 * Changes the end time of the given event, to the current time
	 * 
	 * @param event The event that should be updated
	 * @param context The context of the app, used to extract the CONTENT_URI and the ContentResolver
	 */
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
	
	/**
	 * Changes the end time of the given event, to the given time
	 * 
	 * @param event The event that should be updated
	 * @param context The context of the app, used to extract the CONTENT_URI and the ContentResolver
	 * @param time The time the event should now end on
	 */
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
