package com.jacksmartie.PhotoMem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class NameActivity extends OptionsMenu {

	public static final String NAME_FIELD = "NameField";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.name);
		
		SharedPreferences settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
		String name = settings.getString(NAME_FIELD, "");
		EditText nameField = (EditText) findViewById(R.id.nameEditText1);
		nameField.setText(name);
	}
	public void OnButtonNameOkClickListener(View v) {
		SharedPreferences settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		
		EditText nameField = (EditText) findViewById(R.id.nameEditText1);
		String name = nameField.getText().toString();
		editor.putString(NAME_FIELD, name);
		
		editor.commit();
		
		finish();
	}
	public void OnButtonNameCancelClickListener(View v) {
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
				SharedPreferences settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				
				EditText nameField = (EditText) findViewById(R.id.nameEditText1);
				String name = nameField.getText().toString();
				editor.putString(NAME_FIELD, name);
				
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
