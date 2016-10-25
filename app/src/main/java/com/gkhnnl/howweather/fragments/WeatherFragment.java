package com.gkhnnl.howweather.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gkhnnl.howweather.App;
import com.gkhnnl.howweather.R;
import com.gkhnnl.howweather.adapter.CustomPagerAdapter;
import com.gkhnnl.howweather.util.ImageUtil;
import com.gkhnnl.howweather.wrapper.WeatherEntity;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    private static final String CITY_ID = "cityId";

    public WeatherFragment() {

    }

    public static WeatherFragment newInstance(String cityId) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(CITY_ID, cityId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);

        String temp = App.temp;

        Bundle bundle = this.getArguments();
        String cityId = bundle.getString(CITY_ID);

        TextView location = (TextView) v.findViewById(R.id.txt_location);
        TextView desc = (TextView) v.findViewById(R.id.txt_description);
        TextView degrees = (TextView) v.findViewById(R.id.txt_degrees);
        ImageView icon = (ImageView) v.findViewById(R.id.icon_weather);
        TextView min = (TextView) v.findViewById(R.id.txt_min);
        TextView max = (TextView) v.findViewById(R.id.txt_max);
        TextView humidity = (TextView) v.findViewById(R.id.txt_humidity);
        //TextView pressure = (TextView) v.findViewById(R.id.txt_pressure);
        TextView wind = (TextView) v.findViewById(R.id.txt_wind);
        //TextView direction = (TextView) v.findViewById(R.id.txt_direction);
        TextView sunrise = (TextView) v.findViewById(R.id.txt_sunrise);
        TextView sunset = (TextView) v.findViewById(R.id.txt_sunset);

        if(App.CityWeather == null)
            return v;

        WeatherEntity weather = App.CityWeather.get(cityId);

        location.setText(weather.getCityName() + ", " + weather.getCountry());
        desc.setText(weather.getDescription());
        degrees.setText(weather.getTemperature() + temp);
        min.setText(weather.getMinDegree() + temp);
        max.setText(weather.getMaxDegree() + temp);
        ImageUtil.setIcon(getActivity(), icon, weather.getIconId(), weather.getIconSet());
        humidity.setText(weather.getHumidity() + getString(R.string.percentage));
        //pressure.setText(weather.getPressure());
        wind.setText(weather.getWindSpeed() + getString(R.string.speed));
        //direction.setText(weather.getWindDegree());
        sunrise.setText(weather.getSunrise());
        sunset.setText(weather.getSunset());

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.hourly)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.daily)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager pager = (ViewPager) v.findViewById(R.id.pager);
        pager.setAdapter(new CustomPagerAdapter(getActivity(), getContext(), cityId));
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return v;
    }
}
