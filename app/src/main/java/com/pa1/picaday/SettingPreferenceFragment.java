package com.pa1.picaday;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;

public class SettingPreferenceFragment extends PreferenceFragment {

    SharedPreferences prefs;

    ListPreference timerstyle;
    ListPreference startpagestyle;
    SwitchPreference listmode;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.setting_preference);
        timerstyle = (ListPreference) findPreference("timerstyle");
        startpagestyle = (ListPreference) findPreference("startpage");
        listmode = (SwitchPreference) findPreference("listmode");

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (!prefs.getString("timerstyle", "").equals("")) {
            timerstyle.setSummary(prefs.getString("timerstyle",""));//
        }
        if (!prefs.getString("startpage", "").equals("")) {
            startpagestyle.setSummary(prefs.getString("startpage","") + " 먼저 보기");
        }
        if (!prefs.getBoolean("listmode", false)) {
            listmode.setSummary("그래픽 먼저 보기");
        }
        else {
            listmode.setSummary("리스트 먼저 보기");
        }

        prefs.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("timerstyle")) {
                    timerstyle.setSummary(sharedPreferences.getString("timerstyle",""));//
                }
                if (key.equals("startpage")) {
                    startpagestyle.setSummary(sharedPreferences.getString("startpage","") + " 먼저 보기");
                }
                if (key.equals("listmode")) {
                    if (sharedPreferences.getBoolean("listmode", false)) {
                        listmode.setSummary("리스트 먼저 보기");
                    }
                    else {
                        listmode.setSummary("그래픽 먼저 보기");
                    }

                    ((BaseAdapter)getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
                }
            }
        });
    }
}
