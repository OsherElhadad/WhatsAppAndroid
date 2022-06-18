package com.example.whatsappandroid;

import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.whatsappandroid.utilities.Info;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Preference p = findPreference(getString(R.string.mode));
        if (p != null) {
            p.setOnPreferenceChangeListener((preference, newValue) -> {
                Log.i("newValue", newValue.toString());
                if (newValue.toString() == "false") {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                return true;
            });
        }

        p = findPreference(getString(R.string.BasicUrl));
        if (p != null) {
            p.setOnPreferenceChangeListener((preference, newValue) -> {
                Info.baseUrlServer = newValue.toString();
                return true;
            });
        }

        p = findPreference(getString(R.string.BasicPort));
        if (p != null) {
            p.setOnPreferenceChangeListener((preference, newValue) -> {
                Info.serverPort = newValue.toString();
                return true;
            });
        }
    }
}