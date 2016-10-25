package com.gkhnnl.howweather.util;

import android.app.Activity;
import android.content.Intent;

import com.gkhnnl.howweather.LocationActivity;
import com.gkhnnl.howweather.LocationListActivity;
import com.gkhnnl.howweather.MainActivity;
import com.gkhnnl.howweather.SettingsActivity;

/**
 * Created by gkhnnl on 05/09/16.
 */
public class NavigationUtil {

    public static void startLocation(Activity activity) {
        Intent intent = new Intent(activity, LocationListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
        activity.finish();
    }

    public static void goLocation(Activity activity) {
        Intent intent = new Intent(activity, LocationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public static void goLocationList(Activity activity){
        Intent intent = new Intent(activity, LocationListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public static void goSetting(Activity activity) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public static void home(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }
}
