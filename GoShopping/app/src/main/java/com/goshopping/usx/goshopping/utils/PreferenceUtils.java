package com.goshopping.usx.goshopping.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.goshopping.usx.goshopping.GoShoppingApp;

/**
 * Created by Si on 2016/10/14.
 */

public final class PreferenceUtils
{

    /**
     * 清空数据
     */
    public static void reset(final Context ctx)
    {

        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        edit.clear();
        edit.apply();
    }

    public static String getString(String key, String defValue)
    {

        return PreferenceManager.getDefaultSharedPreferences(GoShoppingApp.getInstance()).getString(key, defValue);
    }

    public static long getLong(String key, long defValue)
    {

        return PreferenceManager.getDefaultSharedPreferences(GoShoppingApp.getInstance()).getLong(key, defValue);
    }

    public static int getInt(String key, int defValue)
    {
        return PreferenceManager.getDefaultSharedPreferences(GoShoppingApp.getInstance()).getInt(key, defValue);
    }

    public static float getFloat(String key, float defValue)
    {

        return PreferenceManager.getDefaultSharedPreferences(GoShoppingApp.getInstance()).getFloat(key, defValue);
    }

    public static void put(String key, String value)
    {

        putString(key, value);
    }

    public static void put(String key, int value)
    {

        putInt(key, value);
    }

    public static void put(String key, float value)
    {

        putFloat(key, value);
    }

    public static void put(String key, boolean value)
    {
        putBoolean(key, value);
    }

    public static SharedPreferences getPreferences()
    {
        return PreferenceManager.getDefaultSharedPreferences(GoShoppingApp.getInstance());
    }


    private static void putFloat(String key, float value)
    {
        SharedPreferences sharedPreferences = getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key, boolean defValue)
    {
        return PreferenceManager.getDefaultSharedPreferences(GoShoppingApp.getInstance()).getBoolean(key, defValue);
    }

    public static void putStringProcess(String key, String value)
    {

        SharedPreferences sharedPreferences = GoShoppingApp.getInstance().getSharedPreferences("preference_mu", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringProcess(String key, String defValue)
    {

        SharedPreferences sharedPreferences = GoShoppingApp.getInstance().getSharedPreferences("preference_mu", Context.MODE_MULTI_PROCESS);
        return sharedPreferences.getString(key, defValue);
    }

    public static boolean hasString(String key)
    {

        SharedPreferences sharedPreferences = getPreferences();
        return sharedPreferences.contains(key);
    }

    private static void putString(String key, String value)
    {

        SharedPreferences sharedPreferences = getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void putLong(String key, long value)
    {

        SharedPreferences sharedPreferences = getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    private static void putBoolean(String key, boolean value)
    {

        SharedPreferences sharedPreferences = getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private static void putInt(String key, int value)
    {

        SharedPreferences sharedPreferences = getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void remove(String... keys)
    {

        if (keys != null)
        {
            SharedPreferences sharedPreferences = getPreferences();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            for (String key : keys)
            {
                editor.remove(key);
            }
            editor.apply();
        }
    }
}