package com.jacksmartie.PhotoMem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OptionsMenu extends Activity {
	Intent intent;
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.optionsMenuNewGame:
				settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
		    	editor = settings.edit();
		    	editor.putBoolean(MenuActivity.NEW_GAME, true);
		    	editor.putBoolean(MenuActivity.PREVIOUS_GAME, false);
		    	
		    	editor.commit();
				
		    	finish();
				intent = new Intent(this, com.jacksmartie.PhotoMem.MainActivity.class);
				startActivity(intent);
				return true;
			case R.id.optionsMenuContinueGame:
				settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
				editor = settings.edit();
				editor.putBoolean(MenuActivity.NEW_GAME, false);
				
				editor.commit();
				
				if (!settings.getBoolean(MenuActivity.PREVIOUS_GAME, false)) {
					finish();
					intent = new Intent(this, com.jacksmartie.PhotoMem.MainActivity.class);
					startActivity(intent);
				} 
				return true;
			case R.id.optionsMenuSettings:
				intent = new Intent(this, com.jacksmartie.PhotoMem.SettingsActivity.class);
				startActivity(intent);
				return true;
			case R.id.optionsMenuTopScores:
				intent = new Intent(this, com.jacksmartie.PhotoMem.ScoresActivity.class);
				startActivity(intent);
				return true;
			case R.id.optionsMenuInstructions:
				intent = new Intent(this, com.jacksmartie.PhotoMem.InstructionsActivity.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}	
	}
}
