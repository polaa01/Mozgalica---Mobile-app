package com.example.mozgalica;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LocaleHelper {
    public static void setLocale(Context context, String langCode)
    {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());


        //SharedPreferences prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        //String username = prefs.getString("username", null);

        //if(username != null)
        //{
            SharedPreferences preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("my_Lang", langCode);
            editor.apply();
        //}

    }

    public static void loadLocale(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);

        //String username = prefs.getString("username", null);

        //String language = "en";
       // if(username != null)
        //{
            //SharedPreferences preferences = context.getSharedPreferences("Settings_" + username, Context.MODE_PRIVATE);
           // language = preferences.getString("my_Lang", "en");
        //}
        String language = prefs.getString("my_Lang", "en");
        setLocale(context, language);
    }

}
