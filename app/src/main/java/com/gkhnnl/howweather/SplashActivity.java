package com.gkhnnl.howweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gkhnnl.howweather.listener.DialogListener;
import com.gkhnnl.howweather.util.ApiUtil;
import com.gkhnnl.howweather.util.ConnectionUtil;
import com.gkhnnl.howweather.util.DialogUtil;
import com.gkhnnl.howweather.util.NavigationUtil;
import com.gkhnnl.howweather.util.UserPref;

import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        UserPref.setPref(this);

        if (ConnectionUtil.isConnect(this)) {
            if (UserPref.getBoolean(this, UserPref.KEY_ISADD)) {
                Map<String, String> locations = UserPref.getLocation(this);
                int numberOfRequests = locations.size();
                if (numberOfRequests != 0)
                    ApiUtil.getWeather(locations, this, numberOfRequests, false);
                else
                    NavigationUtil.home(this);
            } else {
                NavigationUtil.home(this);
            }
        } else {

            DialogUtil.showDialog(this, getString(R.string.warning), getString(R.string.controlInternetConnection), getString(R.string.close), "", new DialogListener() {
                @Override
                public void onPositive() {
                    finish();
                }
            });
        }

    }
}
