package com.gkhnnl.howweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.gkhnnl.howweather.adapter.PagerAdapter;
import com.gkhnnl.howweather.fragments.WeatherFragment;
import com.gkhnnl.howweather.util.NavigationUtil;
import com.gkhnnl.howweather.util.UserPref;
import com.gkhnnl.howweather.wrapper.WeatherEntity;
import com.robohorse.pagerbullet.PagerBullet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    protected static boolean isClick = false;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        layout = (RelativeLayout) findViewById(R.id.add_location);
        if (layout.getVisibility() != View.GONE) {
            TextView warning = (TextView) findViewById(R.id.warning);
            warning.setText(R.string.addLocationWarning);
        }
        ImageView btnLocation = (ImageView) findViewById(R.id.btn_location);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.startLocation(MainActivity.this);
            }
        });

        PagerBullet pagerBullet = (PagerBullet) findViewById(R.id.pagerBullet);
        pagerBullet.invalidateBullets();

        Map<String, WeatherEntity> CityWeatherInfo = App.CityWeather;
        if (CityWeatherInfo != null) {
            List<Fragment> fragments = new ArrayList<>();
            for (Map.Entry<String, WeatherEntity> pairs : CityWeatherInfo.entrySet()) {
                Fragment fragment = WeatherFragment.newInstance(pairs.getKey());
                fragments.add(fragment);
            }

            PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
            adapter.notifyDataSetChanged();
            pagerBullet.setAdapter(adapter);
            layout.setVisibility(View.GONE);
            pagerBullet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.setting) {
            NavigationUtil.goSetting(this);
            isClick = false;
        } else if (item.getItemId() == R.id.locations) {
            NavigationUtil.goLocationList(this);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (layout.getVisibility() != View.GONE) {
            TextView warning = (TextView) findViewById(R.id.warning);
            warning.setText(R.string.addLocationWarning);
        } else if (UserPref.getLocation(this).size() == 0)
            layout.setVisibility(View.VISIBLE);
    }
}
