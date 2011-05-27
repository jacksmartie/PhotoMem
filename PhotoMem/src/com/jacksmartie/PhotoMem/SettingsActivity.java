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

	public static final int PICTURES_OPTION_LENGTH = 15;
	public static final int SECONDS_OPTION_LENGTH = 4;
	public static final int DEFAULT_PICTURES_OPTION = 16;
	public static final int DEFAULT_DELAY_OPTION = 2;
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
		delayOption = settings.getInt(DELAY_OPTION, DEFAULT_DELAY_OPTION);
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
