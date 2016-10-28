package com.cache.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Kamal on 10/26/16.
 */

public class PrefUtils {


    private static final String KEY_BEST_SCORE = "KEY_BEST_SCORE";


    public static void saveBestScore(Context ctx, int newBestScore) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(KEY_BEST_SCORE, newBestScore);
        editor.commit();
    }


    public static int getBestScore(Context ctx) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return pref.getInt(KEY_BEST_SCORE, 0);
    }
}
