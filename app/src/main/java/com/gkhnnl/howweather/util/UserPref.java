package com.gkhnnl.howweather.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gkhnnl.howweather.App;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gkhnnl on 05/09/16.
 */
public class UserPref {

    private static final String STORE_NAME = "HowWeatherUserPref";
    private static final String KEYS = "keys";
    private static final String NAMES = "names";
    public static final String KEY_ISADD = "isAdd";
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm");
    private static final String TEMP = "temp";
    public static final String LANG = "lang";
    public static final String UNIT = "unit";
    private static final String CELS = "°C";
    private static final String FAHR = "°F";
    private static final String TR = "tr";
    private static final String ENG = "en";
    private static final String METRIC = "metric";
    public static final String IMPERIAL = "imperial";

    public static String getString(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, "");
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, false);
    }

    public static void setLocation(Context context, String id, String cityName) {
        SharedPreferences settings = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        JSONArray arrayKey = new JSONArray();
        JSONArray arrayValue = new JSONArray();
        Map<String, String> locations = UserPref.getLocation(context);
        locations.put(id, cityName);

        for (Map.Entry<String, String> i : locations.entrySet()) {
            arrayValue.put(i.getValue());
            arrayKey.put(i.getKey());
        }
        if (!locations.isEmpty()) {
            editor.putString(NAMES, arrayValue.toString());
            editor.putString(KEYS, arrayKey.toString());
        } else {
            editor.putString(KEYS, null);
            editor.putString(NAMES, null);
        }
        editor.apply();
    }

    public static Map<String, String> getLocation(Context context) {
        SharedPreferences settings = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        String keys = settings.getString(KEYS, null);
        String values = settings.getString(NAMES, null);
        Map<String, String> locations = new LinkedHashMap<>();
        if (keys != null) {
            try {
                JSONArray arrayKey = new JSONArray(keys);
                JSONArray arrayValue = new JSONArray(values);
                for (int i = 0; i < arrayKey.length(); i++) {
                    String id = arrayKey.optString(i);
                    String name = arrayValue.optString(i);
                    locations.put(id, name);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return locations;
    }

    public static void deleteLocation(Context context, String id) {
        SharedPreferences settings = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Map<String, String> locations = UserPref.getLocation(context);
        locations.remove(id);
        editor.remove(KEYS).apply();
        editor.remove(NAMES).apply();
        App.CityWeather.remove(id);
        refreshLocationData(context, locations);
    }

    private static void refreshLocationData(Context context, Map<String, String> locations) {
        SharedPreferences settings = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        JSONArray arrayKey = new JSONArray();
        JSONArray arrayValue = new JSONArray();
        for (Map.Entry<String, String> i : locations.entrySet()) {
            arrayValue.put(i.getValue());
            arrayKey.put(i.getKey());
        }

        if (!locations.isEmpty()) {
            editor.putString(NAMES, arrayValue.toString());
            editor.putString(KEYS, arrayKey.toString());
        } else {
            editor.putString(KEYS, null);
            editor.putString(NAMES, null);
        }
        editor.apply();
    }

    public static void setPref(Activity activity) {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(activity);
        App.lang = sharedPrefs.getString(UserPref.LANG, UserPref.ENG);
        App.unit = sharedPrefs.getString(UserPref.UNIT, UserPref.METRIC);
        if (App.unit.equals(UserPref.METRIC))
            App.temp = UserPref.CELS;
        else
            App.temp = UserPref.FAHR;
    }
}
