package com.gkhnnl.howweather.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;

import com.gkhnnl.howweather.R;
import com.gkhnnl.howweather.SettingsActivity;
import com.gkhnnl.howweather.listener.PreferenceListener;
import com.gkhnnl.howweather.util.LanguageUtil;
import com.gkhnnl.howweather.util.UserPref;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragment {

    private PreferenceListener listener;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference lang = findPreference(UserPref.LANG);
        lang.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                listener.onClick();
                LanguageUtil.setLocale(getActivity(), (String) newValue);
                getActivity().recreate();
                return true;
            }
        });

        Preference unit = findPreference(UserPref.UNIT);
        unit.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                listener.onClick();
                return true;
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (SettingsActivity) activity;
    }
}
