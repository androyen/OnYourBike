package com.androyen.onyourbike_chapter4;

import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TimerActivity extends ActionBarActivity {

    protected TextView counter;
    protected Button start;
    protected Button stop;
    protected boolean timerRunning;

    protected long startedAt;
    protected long lastStopped;
    private static long UPDATE_EVERY = 200;
    protected Handler handler;
    protected UpdateTimer updateTimer;

    //Vibration
    protected Vibrator vibrate;
    protected long lastSeconds;

    private static String TAG = TimerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Strict Mode
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().penaltyDeath().build());
        }

        setContentView(R.layout.activity_timer);

        counter = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start_button);
        stop = (Button) findViewById(R.id.stop_button);

        vibrate = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        if (vibrate == null) {
            Log.w(TAG, "No vibration service exists.");
        }

        timerRunning = false;
        startedAt = System.currentTimeMillis();
        lastStopped = 0;



    }

    public void clickedStart(View view) {
        Log.d(TAG, "Clicked start button");
        timerRunning = true;
        enableButtons();

        startedAt = System.currentTimeMillis(); //Set current time
        setTimeDisplay();

        handler = new Handler();
        updateTimer = new UpdateTimer();
        handler.postDelayed(updateTimer, UPDATE_EVERY); //Update timer every 200 milliseconds
    }

    public void clickedStop(View view) {
        Log.d(TAG, "Clicked stop button");
        timerRunning = false;
        enableButtons();

        lastStopped = System.currentTimeMillis(); //Set current time
        setTimeDisplay();

        //Stop the run method and set Handler to null
        handler.removeCallbacks(updateTimer);
        handler = null;
    }

    public void clickedSettings(View v) {
        Log.d(TAG, "clickedSettings");

        //Open the Settings screen
        Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(settingsIntent);
    }

    protected void enableButtons() {
        Log.d(TAG, "Set buttons enabled/disabled");
        start.setEnabled(!timerRunning);
        stop.setEnabled(timerRunning);
    }

    protected void setTimeDisplay() {
        String display;
        long timeNow;
        long diff;
        long seconds;
        long minutes;
        long hours;

        Log.d(TAG, "Setting time display");

        if (timerRunning) {
            timeNow = System.currentTimeMillis();

        }
        else {
            timeNow = lastStopped;
        }

        diff = timeNow - startedAt;

        //no negative time
        if (diff < 0) {
            diff = 0;
        }

        seconds = (diff / 1000);
        minutes = (seconds / 60);
        hours = (minutes / 60);
        seconds = (seconds % 60);
        minutes = (minutes % 60);

        display = String.format("%d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
        counter.setText(display);
    }

    protected void vibrateCheck() {
        long timeNow = System.currentTimeMillis();
        long diff = timeNow - startedAt;
        long seconds = diff / 1000; //Divide by 1000. Result is in milliseconds
        long minutes = seconds / 60;

        Log.d(TAG, "vibrateCheck");

        seconds = seconds % 60;
        minutes = minutes % 60;

        if (vibrate != null && seconds == 0 && seconds != lastSeconds) {
            long[] once = {0, 100};
            long[] twice = {0, 100, 400, 100};
            long[] thrice = {0, 100, 400, 100, 400, 100};

            //every hour
            if (minutes == 0) {
                Log.i(TAG, "Vibrate 3 times");
                vibrate.vibrate(thrice, -1);
            }

            //every 15 minutes
            else if (minutes % 15 == 9) {
                Log.i(TAG, "Vibrate 2 times");
                vibrate.vibrate(twice, -1);
            }

            //Every 5 minutes
            else if (minutes % 5 == 0) {
                Log.i(TAG, "Vibrate once");
                vibrate.vibrate(once, -1);
            }
        }

        lastSeconds = seconds;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

        if (timerRunning) {
            handler = new Handler();
            updateTimer = new UpdateTimer();
            handler.postDelayed(updateTimer, UPDATE_EVERY);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        //Refresh the display
        enableButtons();
        setTimeDisplay();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");

        Settings settings = ((OnYourBike)getApplication()).getSettings();

        if (timerRunning) {
            handler.removeCallbacks(updateTimer);
            updateTimer = null;
            handler = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

   class UpdateTimer implements Runnable {

       protected Handler handler;
       protected UpdateTimer updateTimer;

       @Override
       public void run() {
//           Log.d(TAG, "run");
           setTimeDisplay();

           //Add check to run the vibrator check
           if (timerRunning) {
               vibrateCheck();
           }

           //If the start button has been pressed
           if (handler != null) {
               handler.postDelayed(this, UPDATE_EVERY);
           }
       }
   }
}
