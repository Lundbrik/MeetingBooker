package com.desc.meetingbooker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

public class NewMeetingActivity extends Activity {
	
	private static final String TAG = NewMeetingActivity.class.getSimpleName();
	private TimePicker timeStart;
	private TimePicker timeEnd;
	private Date date = new Date();
	private Calendar cal = Calendar.getInstance();
	private ArrayList<CalEvent> eventlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "called onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_meeting);
		
		eventlist = MainActivity.eventlist;
		
		// Finds the TimePickers
		timeStart = (TimePicker) findViewById(R.id.timePickerStart);
		timeEnd = (TimePicker) findViewById(R.id.timePickerEnd);
		
		setTimePickers();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_meeting, menu);
		return true;
	}
	
	public void add(View view) {
		Log.d(TAG, "Adding event to calendar");
		// Get the different UI fields
		EditText titleText = (EditText) findViewById(R.id.editTitle);
		EditText descText = (EditText) findViewById(R.id.editDesc);
		
		// Read the fields
		String title = titleText.getText().toString();
		String desc = descText.getText().toString();
		int startHour = timeStart.getCurrentHour();
		int startMin = timeStart.getCurrentMinute();
		int endHour = timeEnd.getCurrentHour();
		Log.d(TAG,"END " + endHour);
		int endMin = timeEnd.getCurrentMinute();
		
		// Convert timePicker readings to long
		String startTime = cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR) + " " + startHour + ":" + startMin;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
		try {
			date = formatter.parse(startTime);
			Log.d(TAG, startTime);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		long start = date.getTime();
		String endTime = cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR) + " " + endHour + ":" + endMin;
		try {
			date = formatter.parse(endTime);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		long end = date.getTime();
		
		// Create a new CalEvent
		CalEvent event = new CalEvent(start,end,title,desc);
		Context context = getApplicationContext();
		EventCreate.instance.setNewEvent(event, context);
		Log.d(TAG, "event inserted");
		finish();
	}
	
	public void cancel(View view) {
		Log.d(TAG, "Cancel button pressed");
		finish();
	}
	
	@SuppressLint("SimpleDateFormat")
	private void setTimePickers() {
		
		// Sets the TimePickers to use 24 hour
		timeStart.setIs24HourView(true);
		timeEnd.setIs24HourView(true);
		
		TimeWindow window = findTimeWindow();
		
		int hour = Integer.parseInt(new SimpleDateFormat("HH").format(new Date(window.getStart())));
		int minute = Integer.parseInt(new SimpleDateFormat("mm").format(new Date(window.getStart())));
		
		timeStart.setCurrentHour(hour);
		timeStart.setCurrentMinute(minute);
		
		hour = Integer.parseInt(new SimpleDateFormat("HH").format(new Date(window.getEnd())));
		minute = Integer.parseInt(new SimpleDateFormat("mm").format(new Date(window.getEnd())));
		
		timeEnd.setCurrentHour(hour);
		timeEnd.setCurrentMinute(minute);
			
	}
	
	private TimeWindow findTimeWindow() {
		CalEvent current = MainActivity.current;
		long time = new Date().getTime();
		long fiveMin = 60000 * 5;
		long oneHour = 60000 * 60;
		
		if (current == null) {
			return new TimeWindow(time, time + oneHour);
		}
		if (!current.isUnderway()) {
			long interval = current.getStart() - time;
			if (interval >= fiveMin) {
				return new TimeWindow(time,current.getStart());
			}
		}
		if (!eventlist.isEmpty()) {
			long interval = eventlist.get(0).getStart() - current.getEnd();
			if (interval >= fiveMin) {
				return new TimeWindow(current.getEnd(), eventlist.get(0).getStart());
			} else {
				for (int i = 0; i < eventlist.size()-1; i++) {
					interval = eventlist.get(i+1).getStart() - eventlist.get(i).getEnd();
					if (interval >= fiveMin) {
						return new TimeWindow(eventlist.get(i).getEnd(), eventlist.get(i+1).getStart());
					}
				}
			}
			long eventlistLast = eventlist.get(eventlist.size()-1).getEnd();
			return new TimeWindow(eventlistLast, eventlistLast + oneHour);
		}
		return new TimeWindow(current.getEnd(), current.getEnd() + oneHour);
	}

}
