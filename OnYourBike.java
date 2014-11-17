package com.androyen.onyourbike_chapter4;

import android.app.Application;
import android.widget.ProgressBar;

/**
 * Created by rnguyen on 11/17/14.
 */
public class OnYourBike extends Application {

    protected Settings settings;

    public Settings getSettings() {
        if (settings == null) {
            settings = new Settings();
        }

        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
