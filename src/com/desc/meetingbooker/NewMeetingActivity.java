package com.desc.meetingbooker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "called onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_meeting);
		
		// Finds the TimePickers
		timeStart = (TimePicker) findViewById(R.id.timePickerStart);
		timeEnd = (TimePicker) findViewById(R.id.timePickerEnd);
		
		// Sets the TimePickers to use 24 hour
		timeStart.setIs24HourView(true);
		timeStart.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
		timeStart.setCurrentMinute(cal.get(Calendar.MINUTE));
		timeEnd.setIs24HourView(true);
		timeEnd.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
		timeEnd.setCurrentMinute(cal.get(Calendar.MINUTE) + 15);
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
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		Log.d(TAG, "event inserted");
	}
	
	public void cancel(View view) {
		Log.d(TAG, "Cancel button pressed");
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
