package com.example.hsn07.calarsaat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Hsn07 on 13.6.2017.
 */

public class Settings extends PreferenceActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        SharedPreferences getAlarms = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String alarms = getAlarms.getString("zilsesi", "default ringtone");
        Toast.makeText(this, "alarm..: "+alarms, Toast.LENGTH_SHORT).show();


    }
}
