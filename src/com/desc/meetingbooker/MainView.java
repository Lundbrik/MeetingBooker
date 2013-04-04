package com.desc.meetingbooker;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainView extends View {
	
	private static ListView listView;
	private static TextView textView;
	private static View mainView;
	
	public MainView(Context context) {
		super(context);
		listView = (ListView) findViewById(R.id.listView1);
		textView = (TextView) findViewById(R.id.textView1);
		mainView = (View) findViewById(R.id.mainLay);
	}
	
	public static void mainView(Context context) {
		ArrayList<CalEvent> eventlist = ReadCalendar.readCalendar(context);
		
		CalEvent current = null;
		
		for (CalEvent ev : eventlist) {
			if (ev.getStatus() >= 0) {
				current = ev;
				break;
			}
		}
		
		if (current.getStatus() == 1) {
			mainView.setBackgroundColor(Color.RED);
		} else {
			mainView.setBackgroundColor(Color.GREEN);
		}
		
		if (current != null) {
			textView.setText(current.getTitle());
			textView.setTextSize(30);
		}
		
		ArrayAdapter<CalEvent> adapter = new ArrayAdapter<CalEvent>(
	    		context, 
	    		android.R.layout.simple_list_item_1, 
	    		android.R.id.text1, 
	    		eventlist);
		
		listView.setAdapter(adapter);
	}
}
