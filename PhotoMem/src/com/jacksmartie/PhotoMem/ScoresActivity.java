package com.jacksmartie.PhotoMem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ScoresActivity extends OptionsMenu {

	public static final String TOP_SCORE_1 = "TopScore1";
	public static final String TOP_SCORE_2 = "TopScore2";
	public static final String TOP_SCORE_3 = "TopScore3";
	public static final String TOP_SCORE_4 = "TopScore4";
	public static final String TOP_SCORE_5 = "TopScore5";
	public static final String TOP_SCORE_6 = "TopScore6";
	public static final String TOP_SCORE_7 = "TopScore7";
	public static final String TOP_SCORE_8 = "TopScore8";
	public static final String TOP_SCORE_9 = "TopScore9";
	public static final String TOP_SCORE_10 = "TopScore10";
	public static final String TOP_NAME_1 = "TopName1";
	public static final String TOP_NAME_2 = "TopName2";
	public static final String TOP_NAME_3 = "TopName3";
	public static final String TOP_NAME_4 = "TopName4";
	public static final String TOP_NAME_5 = "TopName5";
	public static final String TOP_NAME_6 = "TopName6";
	public static final String TOP_NAME_7 = "TopName7";
	public static final String TOP_NAME_8 = "TopName8";
	public static final String TOP_NAME_9 = "TopName9";
	public static final String TOP_NAME_10 = "TopName10";
	public static final String CURRENT_SCORE = "CurrentScore";
	TextView scores;
	TextView names;
	int scoresValue;
	String namesValue;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scores);
		
		SharedPreferences settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
		
		scoresValue = settings.getInt(TOP_SCORE_1, 0);
		scores = (TextView) findViewById(R.id.scoresScore1);
		scores.setText(String.valueOf(scoresValue));
		
		scoresValue = settings.getInt(TOP_SCORE_2, 0);
		scores = (TextView) findViewById(R.id.scoresScore2);
		scores.setText(String.valueOf(scoresValue));
		
		scoresValue = settings.getInt(TOP_SCORE_3, 0);
		scores = (TextView) findViewById(R.id.scoresScore3);
		scores.setText(String.valueOf(scoresValue));
		
		scoresValue = settings.getInt(TOP_SCORE_4, 0);
		scores = (TextView) findViewById(R.id.scoresScore4);
		scores.setText(String.valueOf(scoresValue));
		
		scoresValue = settings.getInt(TOP_SCORE_5, 0);
		scores = (TextView) findViewById(R.id.scoresScore5);
		scores.setText(String.valueOf(scoresValue));
		
		scoresValue = settings.getInt(TOP_SCORE_6, 0);
		scores = (TextView) findViewById(R.id.scoresScore6);
		scores.setText(String.valueOf(scoresValue));
		
		scoresValue = settings.getInt(TOP_SCORE_7, 0);
		scores = (TextView) findViewById(R.id.scoresScore7);
		scores.setText(String.valueOf(scoresValue));
		
		scoresValue = settings.getInt(TOP_SCORE_8, 0);
		scores = (TextView) findViewById(R.id.scoresScore8);
		scores.setText(String.valueOf(scoresValue));
		
		scoresValue = settings.getInt(TOP_SCORE_9, 0);
		scores = (TextView) findViewById(R.id.scoresScore9);
		scores.setText(String.valueOf(scoresValue));
		
		scoresValue = settings.getInt(TOP_SCORE_10, 0);
		scores = (TextView) findViewById(R.id.scoresScore10);
		scores.setText(String.valueOf(scoresValue));
		
		namesValue = settings.getString(TOP_NAME_1, "");
		names = (TextView) findViewById(R.id.scoresName1);
		names.setText(String.valueOf(namesValue));
		
		namesValue = settings.getString(TOP_NAME_2, "");
		names = (TextView) findViewById(R.id.scoresName2);
		names.setText(String.valueOf(namesValue));
		
		namesValue = settings.getString(TOP_NAME_3, "");
		names = (TextView) findViewById(R.id.scoresName3);
		names.setText(String.valueOf(namesValue));
		
		namesValue = settings.getString(TOP_NAME_4, "");
		names = (TextView) findViewById(R.id.scoresName4);
		names.setText(String.valueOf(namesValue));
		
		namesValue = settings.getString(TOP_NAME_5, "");
		names = (TextView) findViewById(R.id.scoresName5);
		names.setText(String.valueOf(namesValue));
		
		namesValue = settings.getString(TOP_NAME_6, "");
		names = (TextView) findViewById(R.id.scoresName6);
		names.setText(String.valueOf(namesValue));
		
		namesValue = settings.getString(TOP_NAME_7, "");
		names = (TextView) findViewById(R.id.scoresName7);
		names.setText(String.valueOf(namesValue));
		
		namesValue = settings.getString(TOP_NAME_8, "");
		names = (TextView) findViewById(R.id.scoresName8);
		names.setText(String.valueOf(namesValue));
		
		namesValue = settings.getString(TOP_NAME_9, "");
		names = (TextView) findViewById(R.id.scoresName9);
		names.setText(String.valueOf(namesValue));
		
		namesValue = settings.getString(TOP_NAME_10, "");
		names = (TextView) findViewById(R.id.scoresName10);
		names.setText(String.valueOf(namesValue));
		
	}
	public void OnButtonScoresBackClickListener(View v) {
		finish();
	}
	public void OnButtonScoresNameClickListener(View v) {
		Intent intent = new Intent(this, com.jacksmartie.PhotoMem.NameActivity.class);
		startActivity(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.scores, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.optionsMenuBack:
				finish();
				return true;
			case R.id.optionsMenuPlayerName:
				Intent intent = new Intent(this, com.jacksmartie.PhotoMem.NameActivity.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
