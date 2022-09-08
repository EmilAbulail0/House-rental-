package com.example.houserentals;

import android.content.Context;
import android.content.SharedPreferences;

public class HouseRentalsSPM {
    private static final String SHARED_PREF_NAME = "MySharedPreference";
    private static final int SHARED_PREF_PRIVATE = Context.MODE_PRIVATE;
    private static HouseRentalsSPM ourInstance = null;
    private static SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    static HouseRentalsSPM getInstance(Context context) {
        if (ourInstance != null) {
            return ourInstance;
        }
        ourInstance=new HouseRentalsSPM(context);
        return ourInstance;
    }

    private HouseRentalsSPM(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, SHARED_PREF_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean writeString(String key, String value) {
        editor.putString(key, value);
        return editor.commit();
    }
    public String readString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

}
