package com.desc.meetingbooker;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static ListView listView;
	private static TextView currentAvail;
	private static TextView currentUpcom;
	private static TextView currentTitle;
	private static TextView currentDesc;
	private static TextView currentStart;
	private static TextView currentEnd;
	private static View mainView;
	private static Context context;
	private static final String TAG = MainActivity.class.getSimpleName();
	private static ArrayList<CalEvent> eventlist = new ArrayList<CalEvent>();
	private static ArrayAdapter<CalEvent> adapter;
	private static CalEvent current = null;
	private static Button nextMeeting;
	private static Button endMeeting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate() called");
		setContentView(R.layout.activity_main);
		currentAvail = (TextView) findViewById(R.id.currentAvail);
		currentUpcom = (TextView) findViewById(R.id.currentUpcom);
		currentTitle = (TextView) findViewById(R.id.currentTitle);
		currentDesc = (TextView) findViewById(R.id.currentDesc);
		currentStart = (TextView) findViewById(R.id.currentStart);
		currentEnd = (TextView) findViewById(R.id.currentEnd);
		mainView = (View) findViewById(R.id.mainLay);
		nextMeeting = (Button) findViewById(R.id.nextMeetingButton);
		endMeeting = (Button) findViewById(R.id.endMeetingButton);
		listView = (ListView) findViewById(R.id.listView1);
		context = getApplicationContext();
		adapter = new ArrayAdapter<CalEvent>(MainActivity.context, 
									 		 R.layout.list_black_text, 
									 		 R.id.list_content,
									 		 eventlist) {
			@Override
			public boolean isEnabled(int position) {
				return false;
			}
		};
		listView.setAdapter(adapter);
		
		// Timer for continuous update of calendar
		Timer timer = new Timer();
		Log.d(TAG, "Timer started");
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						MainActivity.sync();
					}
				});
			}
		}, 30000, 30000);
		Log.d(TAG, "onCreate() done");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		MainActivity.sync();
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onActivityResult(int requestcode, int resultcode, Intent data) {
		startActivity(new Intent(this,MainActivity.class));
	}
	
	public void startNextMeeting(View view) {
		UpdateEvent.updateStart(current, context);
	}
	
	public void endMeeting(View view) {
		UpdateEvent.updateEnd(current, context);
	}
	
	public void startNewMeeting(View view) {
		Log.d(TAG, "New Meeting button pressed");
		// Creates NewMeetingActivity, for user input in booking a new meeting
		Intent intent = new Intent(this, NewMeetingActivity.class);
		startActivityForResult(intent, 1);
	}
	
	public static void setCurrent(CalEvent event) {
		currentTitle.setText(event.getTitle());
		currentDesc.setText(event.getDescription());
		currentStart.setText("Start : " + event.getStartTime());
		currentEnd.setText(" End : " + event.getEndTime());
	}
	
	public static void sync() {
		// The event that is currently underway
		current = null;
		
		// Reads all events from the calendar on the present day into an ArrayList
		eventlist = ReadCalendar.readCalendar(MainActivity.context);
		
		// Checks if any of the event in the ArrayList is underway, and sets it as current event
		if (!eventlist.isEmpty()) {
			current = eventlist.get(0);
		}
		
		// Sets the background color(Red if any event is underway, green if not)
		if (current != null && current.isUnderway()) {
			mainView.setBackgroundColor(Color.RED);
			currentAvail.setText("Taken");
			currentUpcom.setText("Current Meeting:");
			nextMeeting.setVisibility(Button.GONE);
			endMeeting.setVisibility(Button.VISIBLE);
			setCurrent(current);
		} else {
			mainView.setBackgroundColor(Color.GREEN);
			currentAvail.setText("Available");
			currentUpcom.setText("Upcoming Meeting:");
			if (current != null) {
				nextMeeting.setVisibility(Button.VISIBLE);
				endMeeting.setVisibility(Button.GONE);
				setCurrent(current);
			} else {
				nextMeeting.setVisibility(Button.GONE);
				endMeeting.setVisibility(Button.GONE);
			}
		}
		
		// Creates the listView
		adapter.clear();
		adapter.addAll(eventlist);
		
	}
	
	

}
