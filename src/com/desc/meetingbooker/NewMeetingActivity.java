package com.desc.meetingbooker;

import java.text.SimpleDateFormat;
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
	//private Calendar cal = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "called onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_meeting);
		
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
		String startTime = Calendar.DAY_OF_MONTH + "-" + (Calendar.MONTH + 1) + "-" +Calendar.YEAR + " " + startHour + ":" + startMin;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
		try {
			date = formatter.parse(startTime);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		long start = date.getTime();
		String endTime = Calendar.DAY_OF_MONTH + "-" + Calendar.MONTH + 1 + "-" + Calendar.YEAR + " " + endHour + ":" + endMin;
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
		CalEvent current = MainActivity.current;
		int calHour = Calendar.HOUR_OF_DAY;
		int calMinute = Calendar.MINUTE;
		// Sets the TimePickers to use 24 hour
		timeStart.setIs24HourView(true);
		timeEnd.setIs24HourView(true);

		if (current == null) {
			timeStart.setCurrentHour(calHour);
			timeStart.setCurrentMinute(calMinute);
		
			timeEnd.setCurrentHour(calHour + 1);
			timeEnd.setCurrentMinute(calMinute);
		} else if (current.isUnderway()) {
			int hour = Integer.parseInt(new SimpleDateFormat("HH").format(new Date(current.getEnd())));
			int minute = Integer.parseInt(new SimpleDateFormat("mm").format(new Date(current.getEnd())));
						
			timeStart.setCurrentHour(hour);
			timeStart.setCurrentMinute(minute);
		
			timeEnd.setCurrentHour(hour + 1);
			timeEnd.setCurrentMinute(minute);
		} else {
			int hour = Integer.parseInt(new SimpleDateFormat("HH").format(new Date(current.getEnd())));
			int minute = Integer.parseInt(new SimpleDateFormat("mm").format(new Date(current.getEnd())));
			
			if ((hour * 60 + minute) >= (calHour * 60 + calMinute + 30)) {
				timeStart.setCurrentHour(calHour);
				timeStart.setCurrentMinute(calMinute);
			
				timeEnd.setCurrentHour(hour);
				timeEnd.setCurrentMinute(minute);			
			} else {
				timeStart.setCurrentHour(hour);
				timeStart.setCurrentMinute(minute);
			
				timeEnd.setCurrentHour(hour + 1);
				timeEnd.setCurrentMinute(minute);
			}
			
			
		}
			
	}

}
