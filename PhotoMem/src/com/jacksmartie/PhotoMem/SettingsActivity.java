package com.jacksmartie.PhotoMem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends OptionsMenu {
	
	public static final int PICTURES_4 = 0;
	public static final int PICTURES_6 = 1;
	public static final int PICTURES_8 = 2;
	public static final int PICTURES_10 = 3;
	public static final int PICTURES_12 = 4;
	public static final int PICTURES_14 = 5;
	public static final int PICTURES_16 = 6;
	public static final int PICTURES_18 = 7;
	public static final int PICTURES_20 = 8;
	public static final int PICTURES_22 = 9;
	public static final int PICTURES_24 = 10;
	public static final int PICTURES_26 = 11;
	public static final int PICTURES_28 = 12;
	public static final int PICTURES_30 = 13;
	public static final int PICTURES_32 = 14;
	public static final int PICTURES_OPTION_0 = 4;
	public static final int PICTURES_OPTION_1 = 6;
	public static final int PICTURES_OPTION_2 = 8;
	public static final int PICTURES_OPTION_3 = 10;
	public static final int PICTURES_OPTION_4 = 12;
	public static final int PICTURES_OPTION_5 = 14;
	public static final int PICTURES_OPTION_6 = 16;
	public static final int PICTURES_OPTION_7 = 18;
	public static final int PICTURES_OPTION_8 = 20;
	public static final int PICTURES_OPTION_9 = 22;
	public static final int PICTURES_OPTION_10 = 24;
	public static final int PICTURES_OPTION_11 = 26;
	public static final int PICTURES_OPTION_12 = 28;
	public static final int PICTURES_OPTION_13 = 30;
	public static final int PICTURES_OPTION_14 = 32;
	public static final int DELAY_2 = 0;
	public static final int DELAY_3 = 1;
	public static final int DELAY_4 = 2;
	public static final int DELAY_5 = 3;
	public static final int DELAY_OPTION_0 = 2;
	public static final int DELAY_OPTION_1 = 3;
	public static final int DELAY_OPTION_2 = 4;
	public static final int DELAY_OPTION_3 = 5;
	
	public static final int DEFAULT_PICTURES_OPTION = PICTURES_16;
	public static final String SETTINGS_NAME = "SettingsFile";
	public static final String PICTURES_OPTION = "picturesOption";
	public static final String DELAY_OPTION = "delayOption";
	public static final String RESTART_GAME = "restartGame";
	public int picturesOption = 0;
	public int delayOption = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinnerSettingsNumberPictures);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numberPictures, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnSettingsPicturesItemSelectedListener());
		SharedPreferences settings = getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
		picturesOption = settings.getInt(PICTURES_OPTION, DEFAULT_PICTURES_OPTION);
		spinner.setSelection(picturesOption);
		
		spinner = (Spinner) findViewById(R.id.spinnerSettingsFlipDelay);
		adapter = ArrayAdapter.createFromResource(this, R.array.flipDelay, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnSettingsDelayItemSelectedListener());
		delayOption = settings.getInt(DELAY_OPTION, DELAY_2);
		spinner.setSelection(delayOption);
	}
	public void OnButtonSettingsOkClickListener(View v) {
		Spinner spinner = (Spinner) findViewById(R.id.spinnerSettingsNumberPictures);
		picturesOption = spinner.getSelectedItemPosition();
		spinner = (Spinner) findViewById(R.id.spinnerSettingsFlipDelay);
		delayOption = spinner.getSelectedItemPosition();
		
		SharedPreferences settings = getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(PICTURES_OPTION, picturesOption);
		editor.putInt(DELAY_OPTION, delayOption);
		editor.commit();
		
		finish();
	}
	public void OnButtonSettingsCancelClickListener(View v) {
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.ok_cancel, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.optionsMenuOk:
				Spinner spinner = (Spinner) findViewById(R.id.spinnerSettingsNumberPictures);
				picturesOption = spinner.getSelectedItemPosition();
				spinner = (Spinner) findViewById(R.id.spinnerSettingsFlipDelay);
				delayOption = spinner.getSelectedItemPosition();
				
				SharedPreferences settings = getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				editor.putInt(PICTURES_OPTION, picturesOption);
				editor.putInt(DELAY_OPTION, delayOption);
				editor.commit();
				
				finish();
				return true;
			case R.id.optionsMenuCancel:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
