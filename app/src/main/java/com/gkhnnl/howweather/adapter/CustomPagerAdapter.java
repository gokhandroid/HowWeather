package com.gkhnnl.howweather.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gkhnnl.howweather.App;
import com.gkhnnl.howweather.R;
import com.gkhnnl.howweather.util.CustomPagerEnum;
import com.gkhnnl.howweather.util.ImageUtil;
import com.gkhnnl.howweather.wrapper.WeatherEntity;

import org.joda.time.DateTime;


/**
 * Created by gkhnnl on 06/09/16.
 */
public class CustomPagerAdapter extends android.support.v4.view.PagerAdapter {

    private final Context context;
    private final String cityId;
    private final String temp;
    private final Activity activity;

    public CustomPagerAdapter(Activity activity, Context context, String cityId) {
        this.context = context;
        this.cityId = cityId;
        this.temp = App.temp;
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), collection, false);

        WeatherEntity weather = App.CityWeather.get(cityId);

        if (customPagerEnum.getLayoutResId() == R.layout.layout_hourly) {

            /* Günlük Hava Tahmini */

            TextView hour1 = (TextView) layout.findViewById(R.id.txt_hour1);
            TextView hour2 = (TextView) layout.findViewById(R.id.txt_hour2);
            TextView hour3 = (TextView) layout.findViewById(R.id.txt_hour3);
            TextView hour4 = (TextView) layout.findViewById(R.id.txt_hour4);
            TextView hour5 = (TextView) layout.findViewById(R.id.txt_hour5);
            TextView deg1 = (TextView) layout.findViewById(R.id.txt_deg1);
            TextView deg2 = (TextView) layout.findViewById(R.id.txt_deg2);
            TextView deg3 = (TextView) layout.findViewById(R.id.txt_deg3);
            TextView deg4 = (TextView) layout.findViewById(R.id.txt_deg4);
            TextView deg5 = (TextView) layout.findViewById(R.id.txt_deg5);
            TextView desc1 = (TextView) layout.findViewById(R.id.txt_desc1);
            TextView desc2 = (TextView) layout.findViewById(R.id.txt_desc2);
            TextView desc3 = (TextView) layout.findViewById(R.id.txt_desc3);
            TextView desc4 = (TextView) layout.findViewById(R.id.txt_desc4);
            TextView desc5 = (TextView) layout.findViewById(R.id.txt_desc5);
            ImageView icon1 = (ImageView) layout.findViewById(R.id.icon_hour1);
            ImageView icon2 = (ImageView) layout.findViewById(R.id.icon_hour2);
            ImageView icon3 = (ImageView) layout.findViewById(R.id.icon_hour3);
            ImageView icon4 = (ImageView) layout.findViewById(R.id.icon_hour4);
            ImageView icon5 = (ImageView) layout.findViewById(R.id.icon_hour5);


            hour1.setText(weather.getHourlyEntities()[0].getHour());
            hour2.setText(weather.getHourlyEntities()[1].getHour());
            hour3.setText(weather.getHourlyEntities()[2].getHour());
            hour4.setText(weather.getHourlyEntities()[3].getHour());
            hour5.setText(weather.getHourlyEntities()[4].getHour());
            deg1.setText(weather.getHourlyEntities()[0].getTemperature() + temp);
            deg2.setText(weather.getHourlyEntities()[1].getTemperature() + temp);
            deg3.setText(weather.getHourlyEntities()[2].getTemperature() + temp);
            deg4.setText(weather.getHourlyEntities()[3].getTemperature() + temp);
            deg5.setText(weather.getHourlyEntities()[4].getTemperature() + temp);
            desc1.setText(weather.getHourlyEntities()[0].getDescription());
            desc2.setText(weather.getHourlyEntities()[1].getDescription());
            desc3.setText(weather.getHourlyEntities()[2].getDescription());
            desc4.setText(weather.getHourlyEntities()[3].getDescription());
            desc5.setText(weather.getHourlyEntities()[4].getDescription());
            ImageUtil.setIcon(activity, icon1, weather.getHourlyEntities()[0].getIconId(), weather.getHourlyEntities()[0].getIconSet());
            ImageUtil.setIcon(activity, icon2, weather.getHourlyEntities()[1].getIconId(), weather.getHourlyEntities()[1].getIconSet());
            ImageUtil.setIcon(activity, icon3, weather.getHourlyEntities()[2].getIconId(), weather.getHourlyEntities()[2].getIconSet());
            ImageUtil.setIcon(activity, icon4, weather.getHourlyEntities()[3].getIconId(), weather.getHourlyEntities()[3].getIconSet());
            ImageUtil.setIcon(activity, icon5, weather.getHourlyEntities()[4].getIconId(), weather.getHourlyEntities()[4].getIconSet());

        } else if (customPagerEnum.getLayoutResId() == R.layout.layout_daily) {

            TextView day1 = (TextView) layout.findViewById(R.id.txt_day1);
            TextView day2 = (TextView) layout.findViewById(R.id.txt_day2);
            TextView day3 = (TextView) layout.findViewById(R.id.txt_day3);
            TextView day4 = (TextView) layout.findViewById(R.id.txt_day4);
            TextView dayDeg1 = (TextView) layout.findViewById(R.id.txt_dayDeg1);
            TextView dayDeg2 = (TextView) layout.findViewById(R.id.txt_dayDeg2);
            TextView dayDeg3 = (TextView) layout.findViewById(R.id.txt_dayDeg3);
            TextView dayDeg4 = (TextView) layout.findViewById(R.id.txt_dayDeg4);
            TextView dayDesc1 = (TextView) layout.findViewById(R.id.txt_dayDesc1);
            TextView dayDesc2 = (TextView) layout.findViewById(R.id.txt_dayDesc2);
            TextView dayDesc3 = (TextView) layout.findViewById(R.id.txt_dayDesc3);
            TextView dayDesc4 = (TextView) layout.findViewById(R.id.txt_dayDesc4);
            ImageView dayIcon1 = (ImageView) layout.findViewById(R.id.icon_day1);
            ImageView dayIcon2 = (ImageView) layout.findViewById(R.id.icon_day2);
            ImageView dayIcon3 = (ImageView) layout.findViewById(R.id.icon_day3);
            ImageView dayIcon4 = (ImageView) layout.findViewById(R.id.icon_day4);

            DateTime now = DateTime.now();
            int dayCount = now.dayOfWeek().get();

            String day;
            day = getDayOfWeek(dayCount + 1);
            day1.setText(day);
            day = getDayOfWeek(dayCount + 2);
            day2.setText(day);
            day = getDayOfWeek(dayCount + 3);
            day3.setText(day);
            day = getDayOfWeek(dayCount + 4);
            day4.setText(day);
            dayDeg1.setText(weather.getDailyEntities()[0].getTemperature() + temp);
            dayDeg2.setText(weather.getDailyEntities()[1].getTemperature() + temp);
            dayDeg3.setText(weather.getDailyEntities()[2].getTemperature() + temp);
            dayDeg4.setText(weather.getDailyEntities()[3].getTemperature() + temp);
            dayDesc1.setText(weather.getDailyEntities()[0].getDescription());
            dayDesc2.setText(weather.getDailyEntities()[1].getDescription());
            dayDesc3.setText(weather.getDailyEntities()[2].getDescription());
            dayDesc4.setText(weather.getDailyEntities()[3].getDescription());
            ImageUtil.setIcon(activity, dayIcon1, weather.getDailyEntities()[0].getIconId(), weather.getDailyEntities()[0].getIconSet());
            ImageUtil.setIcon(activity, dayIcon2, weather.getDailyEntities()[1].getIconId(), weather.getDailyEntities()[1].getIconSet());
            ImageUtil.setIcon(activity, dayIcon3, weather.getDailyEntities()[2].getIconId(), weather.getDailyEntities()[2].getIconSet());
            ImageUtil.setIcon(activity, dayIcon4, weather.getDailyEntities()[3].getIconId(), weather.getDailyEntities()[3].getIconSet());
        }

        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return CustomPagerEnum.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private String getDayOfWeek(int day) {

        String name = null;
        int i = day % 7;
        switch (i) {
            case 1:
                name = context.getResources().getString(R.string.monday);
                break;
            case 2:
                name = context.getResources().getString(R.string.tuesday);
                break;
            case 3:
                name = context.getResources().getString(R.string.wednesday);
                break;
            case 4:
                name = context.getResources().getString(R.string.thursday);
                break;
            case 5:
                name = context.getResources().getString(R.string.friday);
                break;
            case 6:
                name = context.getResources().getString(R.string.saturday);
                break;
            case 0:
                name = context.getResources().getString(R.string.sunday);
                break;
            default:
        }
        return name;
    }
}
