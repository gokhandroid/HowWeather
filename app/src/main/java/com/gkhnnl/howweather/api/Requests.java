package com.gkhnnl.howweather.api;

import android.app.Activity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gkhnnl.howweather.R;
import com.gkhnnl.howweather.listener.ResponseListener;
import com.gkhnnl.howweather.model.City;
import com.gkhnnl.howweather.model.CitySearch;
import com.gkhnnl.howweather.model.DailyWeatherInfo;
import com.gkhnnl.howweather.model.HourlyWeatherInfo;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by gkhnnl on 03/09/16.
 */
public class Requests {

    private static final String TAG = "Requests";
    private static final String LIKE = "like";
    private static final String JSON = "json";

    private static String getBaseUrl(Activity activity) {
        return activity.getString(R.string.base_url);
    }

    public static void getSearchCityResult(Activity activity, String city, String lang, String units, String appid, ResponseListener<CitySearch> listener) {
        String url = getBaseUrl(activity) + "find?q=" + city + "&type=" + LIKE + "&mode=" + JSON + "&lang=" + lang + "&units=" + units + "&appid=" + appid;

        get(activity, url, listener, CitySearch.class);
    }

    public static void getWeatherWithLocation(Activity activity, String lat, String lon, String appid, String lang, String units, ResponseListener<City> listener) {
        String url = getBaseUrl(activity) + "weather?lat=" + lat + "&lon=" + lon + "&mode=" + JSON + "&lang=" + lang + "&units=" + units + "&appid=" + appid;

        get(activity, url, listener, City.class);
    }

    public static void getWeather(Activity activity, String cityId, String appid, String lang, String units, ResponseListener<City> listener) {
        String url = getBaseUrl(activity) + "weather?id=" + cityId + "&mode=" + JSON + "&lang=" + lang + "&units=" + units + "&appid=" + appid;

        get(activity, url, listener, City.class);
    }

    public static void getDailyWeather(Activity activity, String cityId, String appid, String lang, String units, ResponseListener<DailyWeatherInfo> listener) {
        String url = getBaseUrl(activity) + "forecast/daily?id=" + cityId + "&mode=" + JSON + "&lang=" + lang + "&units=" + units + "&appid=" + appid;

        get(activity, url, listener, DailyWeatherInfo.class);
    }

    public static void getHourlyWeather(Activity activity, String cityId, String appid, String lang, String units, ResponseListener<HourlyWeatherInfo> listener) {
        String url = getBaseUrl(activity) + "forecast?id=" + cityId + "&mode=" + JSON + "&lang=" + lang + "&units=" + units + "&appid=" + appid;

        get(activity, url, listener, HourlyWeatherInfo.class);
    }

    private static <T extends Object> void get(final Activity activity, final String url, final ResponseListener<T> responseListener, final Class<?> responseType) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                T obj = (T) new Gson().fromJson(response.toString(), responseType);

                Log.d(TAG, "Request: " + url);
                Log.d(TAG, "Response: " + response.toString());

                if (obj instanceof CitySearch) {
                    if (((CitySearch) obj).getCod().equals("404")) {
                        responseListener.onError();
                    } else {
                        if (responseListener != null)
                            responseListener.onSuccess(obj);
                    }
                } else {

                    if (responseListener != null)
                        responseListener.onSuccess(obj);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (responseListener != null)
                    responseListener.onError();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(5000, 2, 2));
        request.setShouldCache(false);

        VolleyClient.getInstance(activity.getApplicationContext()).addToRequestQueue(request);
    }
}
