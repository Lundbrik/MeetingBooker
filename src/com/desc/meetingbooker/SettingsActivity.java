package com.desc.meetingbooker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class SettingsActivity extends Activity {
	
	private final String TAG = SettingsActivity.class.getSimpleName();
	private ArrayList<String> config;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Hide system UI
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_settings);
		
		config = new ArrayList<String>();
		
		try {
			FileInputStream in = openFileInput("config.cfg");
			InputStreamReader inputStreamReader = new InputStreamReader(in);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				config.add(line);
			}
			in.close();
		} catch (IOException e) {
			Log.e(TAG, "IOEXCEPTION !");
		}
		
		if (config.isEmpty()) {
			configMake();
		} else {
			for (String st : config) {
				interpret(st);
			}
		}
		
	}
	
	public static void readConfig() {
		
	}
	
	private void interpret(String str) {
		int index = str.indexOf(' ');
		String command = str.substring(0,index-1);
		String value = str.substring(index+1,str.length());
		if (command.equals("extendendtime")) {
			MainActivity.extendEnd = Boolean.parseBoolean(value);
		}
	}
	
	private void configMake() {
		try {
			File file = new File("config.cfg");
			FileWriter fw = new FileWriter(file);
			fw.write("extendendtime true");
			MainActivity.extendEnd = true;
			fw.close();
		} catch (IOException e) {
			Log.e(TAG, e.toString());
		}
	}

}
