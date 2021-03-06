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
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static ListView listView;
	private static TextView textView;
	private static View mainView;
	private static Context context;
	private static final String TAG = MainActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate() called");
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		textView = (TextView) findViewById(R.id.textView1);
		mainView = (View) findViewById(R.id.mainLay);
		context = getApplicationContext();
		
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
		finish();
	}
	
	public void startNewMeeting(View view) {
		Log.d(TAG, "New Meeting button pressed");
		Intent intent = new Intent(this, NewMeetingActivity.class);
		startActivity(intent);
	}
	
	public static void sync() {
		ArrayList<CalEvent> eventlist = ReadCalendar.readCalendar(MainActivity.context);
		
		CalEvent current = null;
		
		for (CalEvent ev : eventlist) {
			if (ev.getStatus() >= 0) {
				current = ev;
				break;
			}
		}
		
		if (current != null && current.getStatus() == 1) {
			mainView.setBackgroundColor(Color.RED);
		} else {
			mainView.setBackgroundColor(Color.GREEN);
		}
		
		if (current != null) {
			textView.setText(current.getTitle());
			textView.setTextSize(30);
		}
		
		ArrayAdapter<CalEvent> adapter = new ArrayAdapter<CalEvent>(
	    		MainActivity.context, 
	    		R.layout.list_black_text, 
	    		R.id.list_content, 
	    		eventlist);

		listView.setAdapter(adapter);
	}
	
	

}
