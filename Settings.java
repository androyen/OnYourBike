package com.androyen.onyourbike_chapter4;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by rnguyen on 11/17/14.
 */
public class Settings {

    public static final String TAG = Settings.class.getSimpleName();

    protected boolean vibrateOn;

    private static String VIBRATE = "vibrate";

    public Settings() {

    }

    public boolean isVibrateOn(Activity activity) {
        Log.d(TAG, "isVibrateOn");
        SharedPreferences preferences = activity.getPreferences(Activity.MODE_PRIVATE);

        //Whether vibrate keys exist
        if (preferences.contains(VIBRATE)) {
            vibrateOn = preferences.getBoolean(VIBRATE, false);
        }
        return vibrateOn;
    }

    public void setVibrate(Activity activity, boolean vibrate) {
        vibrateOn = vibrate;

        //Save the vibrate property
        SharedPreferences preferences = activity.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(VIBRATE, vibrate);
        editor.apply();
    }
}
