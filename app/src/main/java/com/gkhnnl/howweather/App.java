package com.gkhnnl.howweather;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.gkhnnl.howweather.util.LanguageUtil;
import com.gkhnnl.howweather.wrapper.WeatherEntity;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

/**
 * Created by gkhnnl on 05/09/16.
 */
public class App extends Application {

    public static Map<String, WeatherEntity> CityWeather = null;
    public static String unit = null;
    public static String temp = null;
    public static String lang = null;

    @Override
    public void onCreate() {
        super.onCreate();
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
        Fabric.with(this, new Answers());
        LanguageUtil.onCreate(this);
    }

}
