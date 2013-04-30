package com.desc.meetingbooker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

public class NewMeetingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_meeting);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_meeting, menu);
		return true;
	}
	
	public void add(View view) {
		// Get the different UI fields
		EditText titleText = (EditText) findViewById(R.id.editTitle);
		EditText descText = (EditText) findViewById(R.id.editDesc);
		TimePicker timeStart = (TimePicker) findViewById(R.id.timePickerStart);
		TimePicker timeEnd = (TimePicker) findViewById(R.id.timePickerEnd);
		
		// Read the fields
		String title = titleText.getText().toString();
		String desc = descText.getText().toString();
		int startHour = timeStart.getCurrentHour();
		int startMin = timeStart.getCurrentMinute();
		int endHour = timeEnd.getCurrentHour();
		int endMin = timeEnd.getCurrentMinute();
		
		// Convert timePicker readings to long
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		String startTime = cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR) + " " + startHour + ":" + startMin;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.getDefault());
		try {
			date = formatter.parse(startTime);
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		long start = date.getTime();
		String endTime = cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR) + " " + endHour + ":" + endMin;
		try {
			date = formatter.parse(endTime);
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		long end = date.getTime();
		
		// Create a new CalEvent
		CalEvent event = new CalEvent(start,end,title,desc);
		Context context = getApplicationContext();
		EventCreate.instance.setNewEvent(event, context);
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void cancel(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
