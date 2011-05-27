package com.jacksmartie.PhotoMem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends OptionsMenu {
	public static int screenWidth, screenHeight = 0;
	public static int totalImages = 0;
	public static int imageViewDimension = 0;
	public static int imageViewFlippedDimension = 0;
	public static int imageBackgroundDimension = 0;
	public static ArrayList<String> imageStringArrayList;
	public static ArrayList<String> imageStringArrayList2;
	public static int flippedCounter = 0;
	public static ArrayList<Boolean> positionTrackerArrayList;
	public static Cursor mImageExternalCursor;
	public static Cursor mImageInternalCursor;
	public static Random numGenerator = new Random();
	public static int solvedTracker = 0;
	public static int currentScore = 0;
	public static int secondsPassed = 0;
	public static int secondsPassedChrono = 0;
	public static int lengthScoresArrayList = 11;
	public static ArrayList<String> topNames = new ArrayList<String>(lengthScoresArrayList);
	public static ArrayList<String> topScoresOption = new ArrayList<String>(lengthScoresArrayList);
	public static ArrayList<String> topNamesOption = new ArrayList<String>(lengthScoresArrayList);
	public static ArrayList<Integer> topScores = new ArrayList<Integer>(lengthScoresArrayList);
	public static float SQUARE_SIZE_DP = 40.0f;
	public static float BACKGROUND_SIZE_DP = 0.0f;
	public static float scale = 0;
	public static BitmapFactory.Options bitmapBoundsOptions;
	public static BitmapFactory.Options bitmapOptions;
	public static Resources resources;
	public static BitmapDrawable bitmapDrawable;
	public static Chronometer mChronometer;
	public static final String SECONDS_PASSED_CHRONO = "SecondsPassedChrono";
	public static final String FLIPPED_FACE_DOWN_FIRING = "FlippedFaceDownFiring";
	public static final String CONFIGURATION_CHANGED = "ConfigurationChanged";
	public static final int MAX_COLOR_VALUE_PLUS_ONE = 256;
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        

        bitmapBoundsOptions = new BitmapFactory.Options();
        bitmapBoundsOptions.inJustDecodeBounds = true;
        bitmapOptions = new BitmapFactory.Options();
        
        resources = getResources();
        
        
        scale = this.getApplicationContext().getResources().getDisplayMetrics().density;
        
        displayOrientation();
        
        String[] projection = { MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA };
        String selection = "";
        String[] selectionArgs = null;
        String selection2 = null;
        
		mImageExternalCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, selection2);
		mImageInternalCursor = managedQuery(MediaStore.Images.Media.INTERNAL_CONTENT_URI, projection, selection, selectionArgs, selection2);
		
		
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.optionsMenuNewGame:
				finish();
				
				settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
		    	editor = settings.edit();
		    	editor.putBoolean(MenuActivity.NEW_GAME, true);
		    	editor.putBoolean(MenuActivity.PREVIOUS_GAME, false);
		    	
		    	editor.commit();
				
				intent = new Intent(this, com.jacksmartie.PhotoMem.MainActivity.class);
				startActivity(intent);
				return true;
			case R.id.optionsMenuContinueGame:
				return true;
			case R.id.optionsMenuSettings:
				setNewGameFalse();
		    	
				intent = new Intent(this, com.jacksmartie.PhotoMem.SettingsActivity.class);
				startActivity(intent);
				return true;
			case R.id.optionsMenuTopScores:
				setNewGameFalse();
		    	
				intent = new Intent(this, com.jacksmartie.PhotoMem.ScoresActivity.class);
				startActivity(intent);
				return true;
			case R.id.optionsMenuInstructions:
				setNewGameFalse();
		    	
				intent = new Intent(this, com.jacksmartie.PhotoMem.InstructionsActivity.class);
				startActivity(intent);
				return true;
			case R.id.optionsMenuPlayerName:
				setNewGameFalse();
		    	
				intent = new Intent(this, com.jacksmartie.PhotoMem.NameActivity.class);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}	
	}
    public void onResume() {
    	super.onResume();
    	
    	resetScreenBackground();
    	initializeVariables();
        reinitializeVariables();
    	
    	missingPicturesPrompt();
        
    	setImageAdapter();
    	
    	getGameVariables();
    	
    	setmChronometer();
    }
    public void onPause() {
    	super.onPause();
    	
    	stopmChronometer();
    	
    	storeOnPauseVariables();
    	
    	calculateTopScores();
    }
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
    	@Override
    	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    		if (positionTrackerArrayList.get(position)) {
    			resetScreenBackground();
    			v.setLayoutParams(new GridView.LayoutParams(imageViewDimension, imageViewDimension));
				v.setBackgroundResource(R.drawable.icon);
				positionTrackerArrayList.set(position, false);
				flippedCounter--;
			} else if (positionTrackerArrayList.get(position) == false) {
				if (flippedCounter < 2) {
					TextView textView;
					SharedPreferences settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = settings.edit();
					
					v.setLayoutParams(new GridView.LayoutParams(imageViewFlippedDimension, imageViewFlippedDimension));
					
					BitmapFactory.decodeFile(imageStringArrayList.get(position), bitmapBoundsOptions);
					int originalSize = (bitmapBoundsOptions.outHeight > bitmapBoundsOptions.outWidth) ? bitmapBoundsOptions.outHeight: bitmapBoundsOptions.outWidth;
					bitmapOptions.inSampleSize = originalSize / imageViewDimension;
					bitmapDrawable = new BitmapDrawable(resources, BitmapFactory.decodeFile(imageStringArrayList.get(position), bitmapOptions));
					v.setBackgroundDrawable(bitmapDrawable);
					
					GridView gridView = (GridView) findViewById(R.id.gridView);
					bitmapOptions.inSampleSize = originalSize / imageBackgroundDimension;
					bitmapDrawable = new BitmapDrawable(resources, BitmapFactory.decodeFile(imageStringArrayList.get(position), bitmapOptions));
					gridView.setBackgroundDrawable(bitmapDrawable);
					
					positionTrackerArrayList.set(position, true);
    				flippedCounter++;
    				
    				for (int i = 0; i < totalImages; i++) {
    					if ((i != position) && (positionTrackerArrayList.get(i) == true)) {
    						if (imageStringArrayList.get(i).equalsIgnoreCase(imageStringArrayList.get(position))) {
    							positionTrackerArrayList.set(i, false);
    							positionTrackerArrayList.set(position, false);
    							
    							resetScreenBackground();
    							v.setLayoutParams(new GridView.LayoutParams(imageViewDimension, imageViewDimension));
    							v.setVisibility(View.INVISIBLE);
    							v = parent.getChildAt(i);
    							v.setLayoutParams(new GridView.LayoutParams(imageViewDimension, imageViewDimension));
    							v.setVisibility(View.INVISIBLE);
    							flippedCounter -= 2;
    							solvedTracker += 2;
    							
    							textView = (TextView) findViewById(R.id.currentScore);
    							currentScore = currentScore + ((solvedTracker*totalImages)/10);
    							if ((currentScore - ((secondsPassed*totalImages)/100)) < 0) {
    								currentScore = 0;
    								textView.setText(String.valueOf(currentScore));
    							} else {
    								textView.setText(String.valueOf(currentScore - ((secondsPassed*totalImages)/100)));
    							}
        					}
    					}
    					
    				}
    				if (solvedTracker == totalImages) {
    					restartGame();
    				}
    				if (!settings.getBoolean(FLIPPED_FACE_DOWN_FIRING, false)) {
    					for (int i = 0; i < totalImages; i++) {
        					if ((i != position) && (positionTrackerArrayList.get(i) == true)) {
        						if (!imageStringArrayList.get(i).equalsIgnoreCase(imageStringArrayList.get(position))) {
        							editor.putBoolean(FLIPPED_FACE_DOWN_FIRING, true);
        							
        							editor.commit();
        							
        							Handler handler = new Handler();
        							
        							switch (settings.getInt(SettingsActivity.DELAY_OPTION, SettingsActivity.DELAY_2)) {
        								case SettingsActivity.DELAY_2:
        									handler.postDelayed(flipFaceDown, SettingsActivity.DELAY_OPTION_0*1000);
        									break;
        								case SettingsActivity.DELAY_3:
        									handler.postDelayed(flipFaceDown, SettingsActivity.DELAY_OPTION_1*1000);
        									break;
        								case SettingsActivity.DELAY_4:
        									handler.postDelayed(flipFaceDown, SettingsActivity.DELAY_OPTION_2*1000);
        									break;
        								case SettingsActivity.DELAY_5:
        									handler.postDelayed(flipFaceDown, SettingsActivity.DELAY_OPTION_3*1000);
        									break;
        								default:
        									break;
        							}
        							
        							
        						}
        					}
        				}
    				}
    				
				}
				
			}
    	}
    };
    public void displayOrientation() {
    	Display display = getWindowManager().getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
        
        imageViewDimension = (int) (SQUARE_SIZE_DP * scale + 0.5f);
        imageViewFlippedDimension = imageViewDimension;
        
        BACKGROUND_SIZE_DP = screenWidth < screenHeight ? screenWidth : screenHeight;
        imageBackgroundDimension = (int) (BACKGROUND_SIZE_DP * scale + 0.5f);
    	GridView gridView = (GridView) findViewById(R.id.gridView);
    	if (screenWidth > screenHeight ) {
    		gridView.setNumColumns(8);
    	} else {
    		gridView.setNumColumns(4);
    	}
    }
    public void setupGame() {
    	Set<String> set = new HashSet<String>();
		while (set.size() < totalImages/2) {
			if (mImageExternalCursor.getCount() > 0) {
				mImageExternalCursor.moveToPosition(numGenerator.nextInt(mImageExternalCursor.getCount()));
				set.add(mImageExternalCursor.getString(mImageExternalCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA)));
			}
			if (mImageInternalCursor.getCount() > 0) {
				mImageInternalCursor.moveToPosition(numGenerator.nextInt(mImageInternalCursor.getCount()));
				set.add(mImageInternalCursor.getString(mImageInternalCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA)));
			}
		}
		Iterator<String> iterator = set.iterator();
		for (int i = 0; i < totalImages/2; i++) {
			if (iterator.hasNext()) {
				imageStringArrayList.add(i, (String)iterator.next());
				imageStringArrayList2.add(i, imageStringArrayList.get(i));
			}
		}
		for (int i = 0; i < totalImages/2; i++) {
			
				imageStringArrayList.add(i+(totalImages/2), imageStringArrayList2.get(i));
		}
		Collections.shuffle(imageStringArrayList);
	}
    public void restartGame() {
    	storeGameVariables();
    	
    	finish();
		Intent intent = new Intent(this, com.jacksmartie.PhotoMem.MainActivity.class);
		startActivity(intent);
    }
    public void OnButtonMainBackClickListener(View v) {
    	settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
    	editor = settings.edit();
    	
    	editor.putBoolean(MenuActivity.NEW_GAME, false);
    	
    	editor.commit();
    	
    	Intent intent = new Intent(this, com.jacksmartie.PhotoMem.MenuActivity.class);
    	startActivity(intent);
    }
    
    public void initializeVariables() {
    	secondsPassed = 0;
    	
    	topScoresOption.add(0, ScoresActivity.TOP_SCORE_1);
    	topScoresOption.add(1, ScoresActivity.TOP_SCORE_2);
    	topScoresOption.add(2, ScoresActivity.TOP_SCORE_3);
    	topScoresOption.add(3, ScoresActivity.TOP_SCORE_4);
    	topScoresOption.add(4, ScoresActivity.TOP_SCORE_5);
    	topScoresOption.add(5, ScoresActivity.TOP_SCORE_6);
    	topScoresOption.add(6, ScoresActivity.TOP_SCORE_7);
    	topScoresOption.add(7, ScoresActivity.TOP_SCORE_8);
    	topScoresOption.add(8, ScoresActivity.TOP_SCORE_9);
    	topScoresOption.add(9, ScoresActivity.TOP_SCORE_10);
    	
    	topNamesOption.add(0, ScoresActivity.TOP_NAME_1);
    	topNamesOption.add(1, ScoresActivity.TOP_NAME_2);
    	topNamesOption.add(2, ScoresActivity.TOP_NAME_3);
    	topNamesOption.add(3, ScoresActivity.TOP_NAME_4);
    	topNamesOption.add(4, ScoresActivity.TOP_NAME_5);
    	topNamesOption.add(5, ScoresActivity.TOP_NAME_6);
    	topNamesOption.add(6, ScoresActivity.TOP_NAME_7);
    	topNamesOption.add(7, ScoresActivity.TOP_NAME_8);
    	topNamesOption.add(8, ScoresActivity.TOP_NAME_9);
    	topNamesOption.add(9, ScoresActivity.TOP_NAME_10);
    	
    }
    public void reinitializeVariables() {
    	settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
    	editor = settings.edit();
    	
    	solvedTracker = 0;
        flippedCounter = 0;
        editor.putBoolean(FLIPPED_FACE_DOWN_FIRING, false);
        if (settings.getBoolean(MenuActivity.NEW_GAME, true)) {
        	if (!settings.getBoolean(MenuActivity.PREVIOUS_GAME, false)) {
        		editor.putInt(SECONDS_PASSED_CHRONO, 0);
        	}
        	
        }
        
        editor.commit();
        
        if (settings.getBoolean(MenuActivity.NEW_GAME, true)) {
    		secondsPassedChrono = 0;
    	}
    	
    	if (settings.getBoolean(MenuActivity.NEW_GAME, true)) {
    		currentScore = 0;
    	} else {
    		currentScore = settings.getInt(ScoresActivity.CURRENT_SCORE, 0);
    	}
    	
    	if (settings.getBoolean(MenuActivity.NEW_GAME, true)) {
    		editor.putInt(ScoresActivity.CURRENT_SCORE, 0);
    		
    		editor.commit();
    	} else {
    		settings.getInt(ScoresActivity.CURRENT_SCORE, 0);
    	}
    	TextView textView;
    	textView = (TextView) findViewById(R.id.highScore);
    	textView.setText(String.valueOf(settings.getInt(ScoresActivity.TOP_SCORE_1, 0)));
        
        switch (settings.getInt(SettingsActivity.PICTURES_OPTION, SettingsActivity.DEFAULT_PICTURES_OPTION)) {
        	case SettingsActivity.PICTURES_4:
        		totalImages = SettingsActivity.PICTURES_OPTION_0;
	        	break;
        	case SettingsActivity.PICTURES_6:
        		totalImages = SettingsActivity.PICTURES_OPTION_1;
	        	break;
        	case SettingsActivity.PICTURES_8:
        		totalImages = SettingsActivity.PICTURES_OPTION_2;
	        	break;
        	case SettingsActivity.PICTURES_10:
        		totalImages = SettingsActivity.PICTURES_OPTION_3;
	        	break;
        	case SettingsActivity.PICTURES_12:
        		totalImages = SettingsActivity.PICTURES_OPTION_4;
	        	break;
        	case SettingsActivity.PICTURES_14:
        		totalImages = SettingsActivity.PICTURES_OPTION_5;
	        	break;
        	case SettingsActivity.PICTURES_16:
        		totalImages = SettingsActivity.PICTURES_OPTION_6;
	        	break;
        	case SettingsActivity.PICTURES_18:
        		totalImages = SettingsActivity.PICTURES_OPTION_7;
	        	break;
        	case SettingsActivity.PICTURES_20:
        		totalImages = SettingsActivity.PICTURES_OPTION_8;
	        	break;
        	case SettingsActivity.PICTURES_22:
        		totalImages = SettingsActivity.PICTURES_OPTION_9;
	        	break;
        	case SettingsActivity.PICTURES_24:
        		totalImages = SettingsActivity.PICTURES_OPTION_10;
	        	break;
        	case SettingsActivity.PICTURES_26:
        		totalImages = SettingsActivity.PICTURES_OPTION_11;
	        	break;
        	case SettingsActivity.PICTURES_28:
        		totalImages = SettingsActivity.PICTURES_OPTION_12;
	        	break;
        	case SettingsActivity.PICTURES_30:
        		totalImages = SettingsActivity.PICTURES_OPTION_13;
	        	break;
        	case SettingsActivity.PICTURES_32:
        		totalImages = SettingsActivity.PICTURES_OPTION_14;
	        	break;
	        default:
	        	break;
        }
        
        imageStringArrayList = new ArrayList<String>(totalImages);
        imageStringArrayList2 = new ArrayList<String>(totalImages);
        positionTrackerArrayList = new ArrayList<Boolean>(totalImages);
        
        for (int i = 0; i < totalImages; i++) {
        	positionTrackerArrayList.add(i, false);
        }
    }
    private Runnable flipFaceDown = new Runnable() {
    	@Override
    	public void run() {
    		flipFaceDown();
    	}
    };
    
    public void flipFaceDown() {
    	SharedPreferences settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = settings.edit();
    	
    	GridView gridView = (GridView) findViewById(R.id.gridView);
    	ImageView imageView;
    	
    	resetScreenBackground();
    	int count = 0;
    	for (int i = 0; i < totalImages; i++) {
    		if (positionTrackerArrayList.get(i) == true) {
    			imageView = (ImageView) gridView.getChildAt(i);
    			imageView.setLayoutParams(new GridView.LayoutParams(imageViewDimension, imageViewDimension));
    			imageView.setBackgroundResource(R.drawable.icon);
    			positionTrackerArrayList.set(i, false);
    			count++;
    			}
    		if (count == 2) {
				break;
    		}
    	}
    	flippedCounter -= count;
    	
    	editor.putBoolean(FLIPPED_FACE_DOWN_FIRING, false);
    	
    	editor.commit();
    }
    public void calculateTopScores() {
    	SharedPreferences settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = settings.edit();
    	
    	topScores.clear();
    	topNames.clear();
    	
    	for (int i = 0; i < lengthScoresArrayList - 1; i++) {
    		topScores.add(i, settings.getInt(topScoresOption.get(i), 0));
    		topNames.add(i, settings.getString(topNamesOption.get(i), ""));
    	}
    	for (int i = 0; i < lengthScoresArrayList - 1; i++) {
    		if (currentScore > topScores.get(i)) {
    			topScores.add(i, currentScore);
    			topNames.add(i, settings.getString(NameActivity.NAME_FIELD, ""));
    			break;
    		}
    	}
    	while (topScores.size() > lengthScoresArrayList - 1) {
    		topScores.remove(topScores.size() - 1);
    	}
    	while (topNames.size() > lengthScoresArrayList - 1) {
    		topNames.remove(topNames.size() - 1);
    	}
    	for (int i = 0; i < lengthScoresArrayList - 1; i++) {
    		editor.putInt(topScoresOption.get(i), topScores.get(i));
    		editor.putString(topNamesOption.get(i), topNames.get(i));
    		
    		editor.commit();
    	}
    }
    public void setImageAdapter() {
    	GridView gridView = (GridView) findViewById(R.id.gridView);
    	gridView.setAdapter(new ImageAdapter(getApplicationContext()));
        gridView.setOnItemClickListener(itemClickListener);
    }
    public void storeOnPauseVariables() {
    	settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
    	editor = settings.edit();
    	
    	editor.putInt(SECONDS_PASSED_CHRONO, secondsPassedChrono);
    	editor.putBoolean(MenuActivity.PREVIOUS_GAME, true);
    	editor.putBoolean(MenuActivity.NEW_GAME, false);
    	editor.putInt(ScoresActivity.CURRENT_SCORE, currentScore - ((secondsPassed*totalImages)/100));
    	
    	editor.commit();
    }
    public void storeGameVariables() {
    	settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
    	editor = settings.edit();
    	
    	editor.putBoolean(SettingsActivity.RESTART_GAME, true);
    	editor.putBoolean(MenuActivity.NEW_GAME, false);
    	editor.putBoolean(MenuActivity.PREVIOUS_GAME, true);
    	editor.putInt(ScoresActivity.CURRENT_SCORE, currentScore - ((secondsPassed*totalImages)/100));
    	
    	editor.commit();
    }
    public void getGameVariables() {
    	settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
    	
    	if (settings.getBoolean(SettingsActivity.RESTART_GAME, false)) {
    		if (!settings.getBoolean(MenuActivity.NEW_GAME, true)) {
    			getCurrentScore();
    		}
    	}
    }
    public void setmChronometer() {
    	SharedPreferences settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
    	
    	mChronometer = (Chronometer) findViewById(R.id.chronometer1);
    	if (settings.getBoolean(MenuActivity.PREVIOUS_GAME, false)) {
    		continueTimer();
    	}
    	if (settings.getBoolean(MenuActivity.NEW_GAME, false)) {
			mChronometer.setBase(SystemClock.elapsedRealtime());
		}
    	mChronometer.start();
    	mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
			@Override
			public void onChronometerTick(Chronometer chronometer) {
				secondsPassed++;
				secondsPassedChrono++;
			}
		}		
    	);
    }
    public void stopmChronometer() {
    	mChronometer = (Chronometer) findViewById(R.id.chronometer1);
    	mChronometer.stop();
    }
    public void setNewGameFalse() {
    	settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
    	editor = settings.edit();
    	
    	editor.putBoolean(MenuActivity.NEW_GAME, false);
    	
    	editor.commit();
    }
    public void resetScreenBackground() {
    	GridView gridView = (GridView) findViewById(R.id.gridView);
    	gridView.setBackgroundResource(R.color.white);
    }
    public void missingPicturesPrompt() {
    	if ((mImageExternalCursor.getCount() + mImageInternalCursor.getCount()) < totalImages/2) {
    		finish();
    		Intent intent = new Intent(this, com.jacksmartie.PhotoMem.MenuActivity.class);
    		startActivity(intent);
    		for (int i = 0; i < 3; i++) {
				Toast.makeText(MainActivity.this, R.string.missingPicturesPrompt, Toast.LENGTH_LONG).show();
			}	
		} else {
			setupGame();
		}
    }
    public void getCurrentScore() {
    	settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
    	TextView textView;
    	
    	currentScore = settings.getInt(ScoresActivity.CURRENT_SCORE, 0);
		if (currentScore < 0) {
			currentScore = 0;
		}
		textView = (TextView) findViewById(R.id.currentScore);
		textView.setText(String.valueOf(currentScore));
    }
    public void continueTimer() {
    	settings = getSharedPreferences(SettingsActivity.SETTINGS_NAME, Context.MODE_PRIVATE);
    	mChronometer = (Chronometer) findViewById(R.id.chronometer1);
    	
    	secondsPassedChrono = settings.getInt(SECONDS_PASSED_CHRONO, 0);
		mChronometer.setBase(SystemClock.elapsedRealtime() - (secondsPassedChrono*1000));
    }
}