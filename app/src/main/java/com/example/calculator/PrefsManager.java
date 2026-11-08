package com.example.calculator;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {

    private static final String PREF_NAME = "KalkulatorPrefs";
    private static final String KEY_DARK_MODE = "dark_mode";

    private SharedPreferences preferences;

    public PrefsManager(Context context) {
        preferences = context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
        );
    }

    // Menyimpan pilihan tema
    public void setDarkMode(boolean aktif) {
        preferences.edit()
                .putBoolean(KEY_DARK_MODE, aktif)
                .apply();
    }

    // Mengambil pilihan tema yang tersimpan
    public boolean isDarkMode() {
        return preferences.getBoolean(KEY_DARK_MODE, true);
    }
}