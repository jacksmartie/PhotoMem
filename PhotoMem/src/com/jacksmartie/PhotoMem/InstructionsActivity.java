package com.jacksmartie.PhotoMem;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class InstructionsActivity extends OptionsMenu {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructions);
	}
	public void OnButtonInstructionsBackClickListener(View v) {
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.back, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.optionsMenuBack) {
			finish();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
}
