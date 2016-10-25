package com.gkhnnl.howweather.util;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.gkhnnl.howweather.R;

/**
 * Created by gkhnnl on 23/10/2016.
 */

public class ImageUtil {

    public static void setIcon(Activity activity, ImageView image, int icon, String day) {
        image.setImageDrawable(getIcon(activity, icon, day));
    }

    private static Drawable getIcon(Activity activity, int icon, String day) {

        int set = icon / 100;
        Drawable image = null;

        if (set == 2) {
            image = activity.getResources().getDrawable(R.drawable.thunderstorm);
            return image;
        } else if (set == 3) {
            image = activity.getResources().getDrawable(R.drawable.drizzle);
            return image;
        } else if (set == 5) {
            if (icon < 503) {
                image = activity.getResources().getDrawable(R.drawable.lightrain);
                return image;
            } else if (icon == 503) {
                image = activity.getResources().getDrawable(R.drawable.veryheavyrain);
                return image;
            } else {
                image = activity.getResources().getDrawable(R.drawable.extremerain);
                return image;
            }
        } else if (set == 6) {
            if (icon < 602) {
                image = activity.getResources().getDrawable(R.drawable.lightsnow);
                return image;
            } else if (icon < 615) {
                image = activity.getResources().getDrawable(R.drawable.heavysnow);
                return image;
            } else {
                image = activity.getResources().getDrawable(R.drawable.rainandsnow);
                return image;
            }
        } else if (set == 7) {
            image = activity.getResources().getDrawable(R.drawable.fog);
            return image;
        } else if (set == 8) {
            if (icon == 800 && day.equals("n")) {
                image = activity.getResources().getDrawable(R.drawable.clearnight);
                return image;
            } else if (icon == 800) {
                image = activity.getResources().getDrawable(R.drawable.clear);
                return image;
            } else if (icon == 801 && day.equals("n")) {
                image = activity.getResources().getDrawable(R.drawable.fewcloudsnight);
                return image;
            } else if (icon == 801) {
                image = activity.getResources().getDrawable(R.drawable.fewclouds);
                return image;
            } else if (icon == 802) {
                image = activity.getResources().getDrawable(R.drawable.scattered);
                return image;
            } else if (icon == 803) {
                image = activity.getResources().getDrawable(R.drawable.broken);
                return image;
            } else if (icon == 804) {
                image = activity.getResources().getDrawable(R.drawable.overcast);
                return image;
            }
        } else {
            image = activity.getResources().getDrawable(R.drawable.broken);
            return image;
        }

        return image;
    }
}
