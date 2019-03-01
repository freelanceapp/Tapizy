package ibt.com.tapizy.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ibt.com.tapizy.model.login_data_modal.UserDataMainModal;

/**
 * Created by Natraj3 on 7/1/2017.
 */

public class AppPreference {

    public static final String APP_PREFENCE = "Tapizy_preference";

    public static void setUserDataPreferences(Context context, String key, UserDataMainModal responseBody) {
        Gson gson = new GsonBuilder().setLenient().create();
        String data = gson.toJson(responseBody);
        SharedPreferences preferences = context.getSharedPreferences("Tapizy", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, data);
        editor.apply();
    }

    public static void setTokenPreference(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences("Tapizy", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getTokenPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("Tapizy", Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void setStringPreference(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void setIntegerPreference(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getIntegerPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    public static void setFloatPreference(Context context, String key, float value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloatPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        return preferences.getFloat(key, 0);
    }

    public static void setBooleanPreference(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setFirstBooleanPref(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getFirstBooleanPref(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, true);
    }

    public static boolean getBooleanPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void clearAllPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

}


