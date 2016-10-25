package com.gkhnnl.howweather.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by gkhnnl on 24/10/2016.
 */

public class LanguageUtil {

    private static final String LANG = "lang";

    public static void onCreate(Context context) {
        String lang = getLanguage(context);
        setLocale(context, lang);
    }

    /* public static String getLanguage(Context context) {
         return getPersistedData(context, Locale.getDefault().getLanguage());
     }
 */
    public static void setLocale(Context context, String language) {
        setLanguage(context, language);
        updateResources(context, language);
    }

    private static String getLanguage(Context context) {
        return UserPref.getString(context, LANG);
    }

    private static void setLanguage(Context context, String language) {
        UserPref.setString(context, LANG, language);
    }

    private static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
