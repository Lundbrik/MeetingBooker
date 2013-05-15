package com.desc.meetingbooker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * The Activity used when an event is being edited
 * 
 * @author Carl Johnsen, Daniel Pedersen, Emil Pedersen and Sune Bartels
 * @version 0.9
 * @since 14-05-2013
 */
public class EditActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit, menu);
		return true;
	}

}
