package com.androyen.onyourbike_chapter4;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;

import com.androyen.onyourbike_chapter4.R;

public class SettingsActivity extends ActionBarActivity {

    private CheckBox vibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        vibrate = (CheckBox) findViewById(R.id.vibrate_checkBox);
        Settings settings = ((OnYourBike)getApplication()).getSettings();
        vibrate.setChecked(settings.isVibrateOn(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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

    @Override
    public void onStop() {
        super.onStop();

        //Save settings when stopped
        Settings settings = ((OnYourBike)getApplication()).getSettings();
        settings.setVibrate(this, vibrate.isChecked());
    }
}
