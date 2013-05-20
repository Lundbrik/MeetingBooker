package com.desc.meetingbooker;

import java.util.ArrayList;
import java.util.Date;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * An Activity that displays todays agenda, current/next meeting and all the links to the other activities
 * 
 * @author Carl Johnsen, Daniel Pedersen, Emil Pedersen and Sune Bartels
 * @version 0.9
 * @since 04-04-2013
 */
public class MainActivity extends Activity {
	
	private static ListView listView;
	private static TextView calendarName;
	private static TextView currentAvail;
	private static TextView currentUpcom;
	private static TextView currentTitle;
	private static TextView currentOrganizer;
	private static TextView currentDesc;
	private static TextView currentStart;
	private static TextView currentEnd;
	private static View mainView;
	private static Context context;
	private static final String TAG = MainActivity.class.getSimpleName();
	protected static ArrayList<CalEvent> eventlist = new ArrayList<CalEvent>();
	private static ArrayAdapter<CalEvent> adapter;
	protected static CalEvent current = null;
	private static Button nextMeeting;
	private static Button endMeeting;
	private static boolean isOverTime = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate() called");
		
		// Hide Status bar and App title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		context = getApplicationContext();
		
		// Casting all the Views
		calendarName 		= (TextView) findViewById(R.id.calendarName);
		currentAvail 		= (TextView) findViewById(R.id.currentAvail);
		currentUpcom 		= (TextView) findViewById(R.id.currentUpcom);
		currentTitle 		= (TextView) findViewById(R.id.currentTitle);
		currentOrganizer 	= (TextView) findViewById(R.id.currentOrganizer);
		currentDesc 		= (TextView) findViewById(R.id.currentDesc);
		currentStart 		= (TextView) findViewById(R.id.currentStart);
		currentEnd 			= (TextView) findViewById(R.id.currentEnd);
		mainView 			= (View) findViewById(R.id.mainLay);
		nextMeeting 		= (Button) findViewById(R.id.nextMeetingButton);
		endMeeting 			= (Button) findViewById(R.id.endMeetingButton);
		listView 			= (ListView) findViewById(R.id.listView1);
		
		// Set the name of the Calendar
		calendarName.setText(ReadCalendar.getCalendarName(context));
		
		// ArrayAdapter for the ListView of events
		adapter = new ArrayAdapter<CalEvent>(MainActivity.context, 
									 		 R.layout.list_black_text, 
									 		 R.id.list_content,
									 		 eventlist);
		// Setting the ListView
		listView.setAdapter(adapter);
		
		// Setting a custom ItemClickListener
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(MainActivity.this, EditActivity.class);
				intent.putExtra("event", position);
				startActivityForResult(intent, 1);
			}
		});
		
		
		
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
		}, 10, 5000);
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
		Log.d(TAG, "onResume()");
		super.onResume();
		MainActivity.sync();
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onActivityResult(int requestcode, int resultcode, Intent data) {
		//startActivity(new Intent(this,MainActivity.class));
	}
	
	/**
	 * The method called by the "StartNextMeeting button". Changes the start time of the next
	 * event, to the current time.
	 * 
	 * @param view The View from the button
	 */
	public void startNextMeeting(View view) {
		UpdateEvent.updateStart(current, context);
	}
	
	/**
	 * The method called by the "EndMeeting" button. Changes the end time of the current event,
	 * to the current time
	 * 
	 * @param view The View from the button
	 */
	public void endMeeting(View view) {
		UpdateEvent.updateEnd(current, context);
	}
	
	/**
	 * The method called by the "NewMeeting" button. Starts the NewMeetingActivity
	 * 
	 * @param view The View from the button
	 */
	public void startNewMeeting(View view) {
		Log.d(TAG, "New Meeting button pressed");
		// Creates NewMeetingActivity, for user input in booking a new meeting
		Intent intent = new Intent(this, NewMeetingActivity.class);
		startActivityForResult(intent, 1);
	}
	
	/**
	 * Changes the current event, to the given event
	 * 
	 * @param event The event which should be set as current
	 */
	public static void setCurrent(CalEvent event) {
		currentTitle.setText(event.getTitle());
		currentOrganizer.setText(event.getOrganizer());
		currentDesc.setText(event.getDescription());
		currentStart.setText("Start : " + event.getStartTime());
		currentEnd.setText(" End : " + event.getEndTime());
	}
	
	// Shows and hides the TextViews for current event
	private static void curShow(boolean val) {
		if (val) {
			currentUpcom.setVisibility(TextView.VISIBLE);
			currentTitle.setVisibility(TextView.VISIBLE);
			currentOrganizer.setVisibility(TextView.VISIBLE);
			currentDesc.setVisibility(TextView.VISIBLE);
			currentStart.setVisibility(TextView.VISIBLE);
			currentEnd.setVisibility(TextView.VISIBLE);
		} else {
			currentUpcom.setVisibility(TextView.GONE);
			currentTitle.setVisibility(TextView.GONE);
			currentOrganizer.setVisibility(TextView.GONE);
			currentDesc.setVisibility(TextView.GONE);
			currentStart.setVisibility(TextView.GONE);
			currentEnd.setVisibility(TextView.GONE);
			nextMeeting.setVisibility(Button.GONE);
			endMeeting.setVisibility(Button.GONE);
		}
	}
	
	// Pushes the current event forward by, up to 15 minutes if nobody pressed End Meeting
	private static void currentOvertime() {
		Long currentTime = new Date().getTime() + 60000;
		if (current != null && !isOverTime && current.getEnd() <= currentTime) {
			Log.d(TAG, "update current! " + isOverTime);
			UpdateEvent.updateEnd(current, context, findExtendedTimeWindow());
			isOverTime = true;
			Log.d(TAG, "update current! " + isOverTime);
		}
	}

	// Gives up to 15 minutes to extend current event(Uses "16" because it seems to round down one minute)
	private static long findExtendedTimeWindow() {
		if (!eventlist.isEmpty()) {
			long interval = eventlist.get(0).getStart() - current.getEnd();
			if (interval < (60000 * 16)) {
				return eventlist.get(0).getStart();
			}
		}
		return current.getEnd() + (60000 * 16);
	}
	
	/**
	 * The method called by the Timer every 5 seconds. It reads the calendar, and updates
	 * the UI if changes have been made
	 */
	protected static void sync() {
		currentOvertime();
		
		// The event that is currently underway
		current = null;
		
		// Reads all events from the calendar on the present day into an ArrayList
		eventlist = ReadCalendar.readCalendar(MainActivity.context);
		
		// Checks if any of the event in the ArrayList is underway,
		// and sets it as current event and removes it from the list
		if (!eventlist.isEmpty()) {
			current = eventlist.get(0);
			eventlist.remove(0);
		}
		
		// Sets the background color(Red if any event is underway, green if not)
		if (current != null && current.isUnderway()) {
			mainView.setBackgroundColor(Color.RED);
			currentAvail.setText("Unavailable");
			currentUpcom.setText("Current Meeting:");
			nextMeeting.setVisibility(Button.GONE);
			endMeeting.setVisibility(Button.VISIBLE);
			setCurrent(current);
			curShow(true);
		} else {
			mainView.setBackgroundColor(Color.GREEN);
			currentAvail.setText("Available");
			currentUpcom.setText("Upcoming Meeting:");
			isOverTime = false;
			if (current != null) {
				nextMeeting.setVisibility(Button.VISIBLE);
				endMeeting.setVisibility(Button.GONE);
				setCurrent(current);
				curShow(true);
			} else {
				curShow(false);
			}
		}
		
		// Creates the listView
		adapter.clear();
		adapter.addAll(eventlist);
		
	}
	
	

}
