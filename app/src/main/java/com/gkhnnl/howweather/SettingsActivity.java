package com.gkhnnl.howweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gkhnnl.howweather.fragments.SettingFragment;
import com.gkhnnl.howweather.listener.PreferenceListener;
import com.gkhnnl.howweather.util.ApiUtil;
import com.gkhnnl.howweather.util.UserPref;

import java.util.Map;

public class SettingsActivity extends AppCompatActivity implements PreferenceListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.setting);
        getFragmentManager().beginTransaction().replace(R.id.container, new SettingFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                checkSetting();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        checkSetting();
    }

    private void checkSetting() {
        Map<String, String> locations = UserPref.getLocation(this);
        int numberOfRequests = locations.size();

        if (MainActivity.isClick && locations.size() != 0) {
            UserPref.setPref(this);
            ApiUtil.getWeather(locations, this, numberOfRequests, true);
        } else
            finish();
    }

    @Override
    public void onClick() {
        MainActivity.isClick = true;
    }
}

