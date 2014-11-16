package com.androyen.onyourbike_chapter4;

import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TimerActivity extends ActionBarActivity {

    protected TextView counter;
    protected Button start;
    protected Button stop;
    protected boolean timerRunning;

    private static String TAG = TimerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //Strict Mode
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().penaltyDeath().build());
        }

        counter = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start_button);
        stop = (Button) findViewById(R.id.stop_button);
    }

    public void clickedStart(View view) {
        Log.d(TAG, "Clicked start button");
        timerRunning = true;
        enableButtons();
    }

    public void clickedStop(View view) {
        Log.d(TAG, "Clicked stop button");
        timerRunning = false;
        enableButtons();
    }

    protected void enableButtons() {
        Log.d(TAG, "Set buttons enabled/disabled");
        start.setEnabled(!timerRunning);
        stop.setEnabled(timerRunning);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
