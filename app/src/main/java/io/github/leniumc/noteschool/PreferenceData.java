package io.github.leniumc.noteschool;

/**
 * Created by 陈涵宇 on 2017/8/23.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferenceData
{
    private static final String PREF_LOGGEDIN_ID = "logged_in_id";
    private static final String PREF_LOGGEDIN_STATUS = "logged_in_status";

    public static SharedPreferences getSharedPreferences(Context ctx)
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setLoggedInEmail(Context ctx, String email)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LOGGEDIN_ID, email);
        editor.apply();
    }

    public static String getLoggedInEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_LOGGEDIN_ID, "");
    }

    public static void setLoggedInStatus(Context ctx, boolean status)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_LOGGEDIN_STATUS, status);
        editor.apply();
    }

    public static boolean getLoggedInStatus(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_LOGGEDIN_STATUS, false);
    }

    public static void clearLoggedInId(Context ctx)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_LOGGEDIN_ID);
        editor.remove(PREF_LOGGEDIN_STATUS);
        editor.apply();
    }
}
