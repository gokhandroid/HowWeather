package com.gkhnnl.howweather.util;

import android.app.Activity;
import android.widget.ListView;

import com.gkhnnl.howweather.App;
import com.gkhnnl.howweather.R;
import com.gkhnnl.howweather.adapter.LocationAdapter;
import com.gkhnnl.howweather.api.Requests;
import com.gkhnnl.howweather.listener.ResponseListener;
import com.gkhnnl.howweather.model.City;
import com.gkhnnl.howweather.model.CitySearch;
import com.gkhnnl.howweather.model.DailyForecast;
import com.gkhnnl.howweather.model.DailyWeatherInfo;
import com.gkhnnl.howweather.model.HourlyWeatherInfo;
import com.gkhnnl.howweather.wrapper.CityEntity;
import com.gkhnnl.howweather.wrapper.DailyEntity;
import com.gkhnnl.howweather.wrapper.HourlyEntity;
import com.gkhnnl.howweather.wrapper.WeatherEntity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


/**
 * Created by gkhnnl on 22/10/2016.
 */

public class ApiUtil {

    private static int numberOfRequests = 0;
    private static Activity activity;
    private static LocationAdapter adapter;

    public static void getWeather(Map<String, String> locations, Activity _activity, int _numberOfRequests, boolean isRefresh) {

        activity = _activity;
        numberOfRequests = _numberOfRequests;

        for (final Map.Entry city : locations.entrySet()) {

            if (!isRefresh) {
                Map<String, WeatherEntity> CityWeather = App.CityWeather;
                if (CityWeather != null)
                    if (locations.size() == 1 && CityWeather.containsKey(city.getKey())) {
                        NavigationUtil.home(activity);
                        return;
                    }
            }

            Requests.getWeather(activity, (String) city.getKey(), activity.getString(R.string.key1), App.lang, App.unit, new ResponseListener<City>() {
                @Override
                public void onSuccess(City city) {
                    if (city != null) {

                        WeatherEntity weather = new WeatherEntity();
                        weather.setCityName(city.getName());
                        weather.setCityId(city.getId());
                        weather.setIconId(city.getWeather().get(0).getId());
                        weather.setIconSet(city.getWeather().get(0).getIcon().substring(2, 3));
                        weather.setCountry(city.getSys().getCountry());
                        weather.setDescription(city.getWeather().get(0).getDescription());
                        weather.setTemperature(String.valueOf(Math.round(city.getMain().getTemp())));
                        weather.setHumidity(String.valueOf(Math.round(city.getMain().getHumidity())));
                        weather.setPressure(String.valueOf(Math.round(city.getMain().getPressure())));
                        weather.setWindSpeed(String.valueOf(Math.round(city.getWind().getSpeed())));
                        weather.setWindDegree(String.valueOf(Math.round(city.getWind().getDeg())));
                        weather.setSunrise(convertUnixTime(city.getSys().getSunrise()));
                        weather.setSunset(convertUnixTime(city.getSys().getSunset()));

                        getDailyWeather(String.valueOf(city.getId()), weather);
                    }
                }

                @Override
                public void onError() {

                }
            });
        }
    }

    private static void getDailyWeather(final String cityId, final WeatherEntity weather) {

        Requests.getDailyWeather(activity, cityId, activity.getString(R.string.key1), App.lang, App.unit, new ResponseListener<DailyWeatherInfo>() {
            @Override
            public void onSuccess(DailyWeatherInfo info) {
                if (info == null)
                    return;

                DateTime now = DateTime.now();
                DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
                String date = fmt.withLocale(Locale.ENGLISH).print(now);
                DailyForecast forecast = null;
                for (DailyForecast item : info.getForecasts()) {
                    String convertedDate = convertUnixDate(item.getDt());
                    if (date.equals(convertedDate)) {
                        forecast = item;
                        break;
                    }
                }

                if(forecast == null)
                    return;

                weather.setMaxDegree(String.valueOf(Math.round(forecast.getTemp().getMax())));
                weather.setMinDegree(String.valueOf(Math.round(forecast.getTemp().getMin())));

                DailyEntity[] dailyEntities = new DailyEntity[4];
                for (int i = 0; i < 4; i++) {
                    DailyEntity entity = new DailyEntity();
                    entity.setTemperature(String.valueOf(Math.round(info.getForecasts().get(i).getTemp().getDay())));
                    entity.setDescription(info.getForecasts().get(i).getWeather().get(0).getDescription());
                    entity.setIconId(info.getForecasts().get(i).getWeather().get(0).getId());
                    entity.setIconSet(info.getForecasts().get(i).getWeather().get(0).getIcon().substring(2,3));
                    dailyEntities[i] = entity;
                }
                weather.setDailyEntities(dailyEntities);

                getHourlyWeather(cityId, weather);
            }

            @Override
            public void onError() {

            }
        });
    }

    private static void getHourlyWeather(final String cityId, final WeatherEntity weather) {

        Requests.getHourlyWeather(activity, cityId, activity.getString(R.string.key1), App.lang, App.unit, new ResponseListener<HourlyWeatherInfo>() {
            @Override
            public void onSuccess(HourlyWeatherInfo info) {

                if (info == null)
                    return;

                HourlyEntity[] hourlyEntities = new HourlyEntity[5];
                for (int i = 0; i < 5; i++) {
                    HourlyEntity entity = new HourlyEntity();
                    entity.setTemperature(String.valueOf(Math.round(info.getHourlyForecast().get(i).getMain().getTemp())));
                    entity.setDescription(info.getHourlyForecast().get(i).getWeather().get(0).getDescription());
                    entity.setHour(formatTime(info.getHourlyForecast().get(i).getDtTxt()));
                    entity.setIconId(info.getHourlyForecast().get(i).getWeather().get(0).getId());
                    entity.setIconSet(info.getHourlyForecast().get(i).getWeather().get(0).getIcon().substring(2,3));
                    hourlyEntities[i] = entity;
                }
                weather.setHourlyEntities(hourlyEntities);

                Map<String, WeatherEntity> CityWeather;
                CityWeather = App.CityWeather;

                if (CityWeather != null)
                    CityWeather.put(cityId, weather);
                else {
                    CityWeather = new LinkedHashMap<>();
                    CityWeather.put(cityId, weather);
                }

                Map<String, WeatherEntity> SortedList = new LinkedHashMap<>();
                Map<String, String> locations = UserPref.getLocation(activity);
                for (Map.Entry entry : locations.entrySet()) {
                    WeatherEntity entity = CityWeather.get(entry.getKey());
                    SortedList.put((String) entry.getKey(), entity);
                }

                App.CityWeather = SortedList;

                if (--numberOfRequests == 0)
                    NavigationUtil.home(activity);

            }

            @Override
            public void onError() {

            }
        });
    }

    public static void searchCity(final Activity activity, String query, final ListView searchList) {

        Requests.getSearchCityResult(activity, query, App.lang, App.unit, activity.getString(R.string.key1), new ResponseListener<CitySearch>() {
            @Override
            public void onSuccess(CitySearch obj) {
                if (obj != null && obj.getCount() != 0) {
                    List<City> cities = obj.getList();
                    if (cities != null && cities.size() != 0) {
                        List<CityEntity> entities = new ArrayList<>();
                        for (City city : cities)
                            entities.add(new CityEntity(city.getId(), city.getName(), city.getSys().getCountry()));

                        adapter = new LocationAdapter(entities, activity, activity.getApplicationContext());
                        searchList.setAdapter(adapter);
                    }
                } else {
                    searchList.setAdapter(null);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    public static void getWeatherWithLocation(final Activity activity, String lat, String lon) {
        Requests.getWeatherWithLocation(activity, lat, lon, activity.getString(R.string.key1), App.lang, App.unit, new ResponseListener<City>() {
            @Override
            public void onSuccess(City city) {

                if (city == null)
                    return;

                if (city.getId() != 0) {
                    String cityId = String.valueOf(city.getId());
                    Map<String, String> location = new LinkedHashMap<>();
                    location.put(cityId, city.getName());
                    getWeather(location, activity, 1, false);
                    UserPref.setLocation(activity.getApplicationContext(), cityId, city.getName());
                    UserPref.setBoolean(activity.getApplicationContext(), UserPref.KEY_ISADD, true);
                }

            }

            @Override
            public void onError() {

            }
        });
    }

    private static String convertUnixTime(int unixTime) {
        Date date = new Date(unixTime * 1000L);
        SimpleDateFormat sdf = UserPref.FORMAT;
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        return sdf.format(date);
    }

    private static String convertUnixDate(int unixTime) {
        Date date = new Date(unixTime * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        return sdf.format(date);
    }

    private static String formatTime(String oldDate) {
        String formattedDate = null;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(oldDate);
            SimpleDateFormat format = UserPref.FORMAT;
            formattedDate = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
}
