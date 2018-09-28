package com.example.android.newsapp;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.prefs.PreferenceChangeListener;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);
            Preference type = findPreference(getString(R.string.settings_type_key));
            bindPreferenceSummaryToValue(type);
        }

        private void bindPreferenceSummaryToValue(Preference pref) {
            pref.setOnPreferenceChangeListener(this);
            SharedPreferences prefes = PreferenceManager.getDefaultSharedPreferences(pref.getContext());
            String prefStr = prefes.getString(pref.getKey(), "");
            onPreferenceChange(pref, prefStr);
        }

        public boolean onPreferenceChange(Preference pref, Object o) {
            String str = o.toString();
            if (o instanceof ListPreference) {
                ListPreference listOfPreference = (ListPreference) pref;
                int prefindex = listOfPreference.findIndexOfValue(str);
                if (prefindex >= 0) {
                    CharSequence[] labels = listOfPreference.getEntries();
                    pref.setSummary(labels[prefindex]);
                }
            } else {
                pref.setSummary(str);
            }
            return true;
        }
    }
}