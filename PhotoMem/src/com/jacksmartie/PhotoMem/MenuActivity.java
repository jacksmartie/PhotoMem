package com.jacksmartie.PhotoMem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends OptionsMenu {
	public static final String NEW_GAME = "NewGame";
	public static final String PREVIOUS_GAME = "PreviousGame";
	Intent intent;
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);	
	}
	public void OnButtonMenuNewGameClickListener(View v) {
		settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
    	editor = settings.edit();
    	editor.putBoolean(NEW_GAME, true);
    	editor.putBoolean(PREVIOUS_GAME, false);
    	
    	editor.commit();
		
    	finish();
		intent = new Intent(this, com.jacksmartie.PhotoMem.MainActivity.class);
		startActivity(intent);
	}
	public void OnButtonMenuContinueGameClickListener(View v) {
		settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
		editor = settings.edit();
		editor.putBoolean(NEW_GAME, false);
		
		editor.commit();
		
		if (!settings.getBoolean(PREVIOUS_GAME, false)) {
			finish();
			intent = new Intent(this, com.jacksmartie.PhotoMem.MainActivity.class);
			startActivity(intent);
		} else {
			finish();
		}
	}
	public void OnButtonMenuSettingsClickListener(View v) {
		intent = new Intent(this, com.jacksmartie.PhotoMem.SettingsActivity.class);
		startActivity(intent);
	}
	public void OnButtonMenuTopScoresClickListener(View v) {
		intent = new Intent(this, com.jacksmartie.PhotoMem.ScoresActivity.class);
		startActivity(intent);
	}
	public void OnButtonMenuInstructionsClickListener(View v) {
		intent = new Intent(this, com.jacksmartie.PhotoMem.InstructionsActivity.class);
		startActivity(intent);
	}
}
