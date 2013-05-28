package com.desc.meetingbooker;

import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;

public class SettingsActivity extends Activity {
	
	private final String TAG = SettingsActivity.class.getSimpleName();
	private HashMap<String, String> config;
	private CheckBox extendEndCheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Hide system UI
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_settings);
		
		extendEndCheck = (CheckBox) findViewById(R.id.extendEndCheck);
		
		config = ConfigReader.readConfig(getApplicationContext());
		setViews(config);
	}
	
	private void setViews(HashMap<String, String> map) {
		extendEndCheck.setChecked(Boolean.parseBoolean(map.get("extendendtime")));
	}
	
	public void save(View view) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("extendendtime", extendEndCheck.isChecked() + "");
		ConfigReader.write(map, getApplicationContext());
		finish();
	}
	
	public void cancel(View view) {
		finish();
	}

}