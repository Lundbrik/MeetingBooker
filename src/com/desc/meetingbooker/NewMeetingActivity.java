package com.desc.meetingbooker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
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
	
	private void setTimePickers() {
		
		CalEvent current = MainActivity.current;
		// Sets the TimePickers to use 24 hour
		timeStart.setIs24HourView(true);
		timeEnd.setIs24HourView(true);
		
		if (current == null) {
			timeStart.setCurrentHour(Calendar.HOUR_OF_DAY);
			timeStart.setCurrentMinute(Calendar.MINUTE);
		
			timeEnd.setCurrentHour(Calendar.HOUR_OF_DAY + 1);
			timeEnd.setCurrentMinute(Calendar.MINUTE);
		} else if (current.isUnderway()) {
			String hour = new SimpleDateFormat("HH").format(new Date(current.getEnd()));
			String minute = new SimpleDateFormat("mm").format(new Date(current.getEnd()));
			timeStart.setCurrentHour(Integer.parseInt(hour));
			timeStart.setCurrentMinute(Integer.parseInt(minute));
		
			timeEnd.setCurrentHour(Integer.parseInt(hour) + 1);
			timeEnd.setCurrentMinute(Integer.parseInt(minute));
		} else {
			timeStart.setCurrentHour(Calendar.HOUR_OF_DAY);
			timeStart.setCurrentMinute(Calendar.MINUTE);
		
			timeEnd.setCurrentHour(Calendar.HOUR_OF_DAY + 1);
			timeEnd.setCurrentMinute(Calendar.MINUTE);
		}
			
	}

}
