/*
 * *
 *  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  *
 *  * @File: PreferenceUtils.java
 *  * @Project: Devote
 *  * @Abstract:
 *  * @Copyright: Copyright © 2015, Saregama India Ltd. All Rights Reserved
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *  * <p/>
 *  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 */

package com.air.movieapp.data;

import android.content.SharedPreferences;
import android.util.Log;

public class PreferenceHelper {

    private static final String TAG = PreferenceHelper.class.getName();

    private SharedPreferences mSharedpreferences;

    public PreferenceHelper(SharedPreferences sharedpreferences) {
        this.mSharedpreferences = sharedpreferences;
    }

    public SharedPreferences getSharedpreferences() {
        return mSharedpreferences;
    }

    /**
     * Get {@link android.content.SharedPreferences.Editor}
     *
     * @return {@link android.content.SharedPreferences.Editor}
     */
    public SharedPreferences.Editor getSharedPreferenceEditor() {
        return mSharedpreferences.edit();
    }

    /**
     * Get {@link String} from {@link SharedPreferences}
     *
     * @param key of the preference
     * @return {@link String} from {@link SharedPreferences}
     */
    public String getStringFromSharedPreference(String key) {
        String value = null;
        if (mSharedpreferences.contains(key)) {
            value = mSharedpreferences.getString(key, null);
        }
        return value;
    }

    public String getStringFromSharedPreference(String key, String defaultValue) {
        String value = defaultValue;
        if (mSharedpreferences.contains(key)) {
            value = mSharedpreferences.getString(key, defaultValue);
        }
        return value;
    }


    /**
     * Save {@link String} to the {@link SharedPreferences}
     *
     * @param key   of the preference
     * @param value of the preference
     */
    public void saveStringIntoSharedPreference(String key, String value) {
        SharedPreferences.Editor prefEditor = getSharedPreferenceEditor();
        prefEditor.putString(key, value);
        Log.d(TAG, "Saved = key " + key + " value = " + value);
        prefEditor.commit();
    }


    /**
     * Get int from {@link SharedPreferences}
     *
     * @param key of the preference
     * @return value for the {@link SharedPreferences}
     */
    public int getIntFromSharedPreference(SharedPreferences sharedpreferences,
                                          String key) {
        int value = 0;
        if (sharedpreferences.contains(key)) {
            value = sharedpreferences.getInt(key, 0);
        }
        Log.d(TAG, "key " + key + " value = " + value);
        return value;
    }


    /**
     * Save int to the {@link SharedPreferences}
     *
     * @param key   of the preference
     * @param value of the preference
     */
    public void saveIntIntoSharedPreference(String key, int value) {
        SharedPreferences.Editor prefEditor = getSharedPreferenceEditor();
        prefEditor.putInt(key, value);
        Log.d(TAG, "Saved = key " + key + " value = " + value);
        prefEditor.commit();
    }

    /**
     * Get float from {@link SharedPreferences}
     *
     * @param key of the preference
     * @return value for the {@link SharedPreferences}
     */
    public float getFloatFromSharedPreference(String key) {
        float value = 0.0f;
        if (mSharedpreferences.contains(key)) {
            value = mSharedpreferences.getFloat(key, 0.0f);
        }
        Log.d(TAG, "key " + key + " value = " + value);
        return value;
    }


    /**
     * Save float to the {@link SharedPreferences}
     *
     * @param key   of the preference
     * @param value of the preference
     */
    public void saveFloatIntoSharedPreference(String key, float value) {
        SharedPreferences.Editor prefEditor = getSharedPreferenceEditor();
        prefEditor.putFloat(key, value);
        Log.d(TAG, "Saved = key " + key + " value = " + value);
        prefEditor.commit();
    }

    /**
     * Get boolean from {@link SharedPreferences}
     *
     * @return value for the {@link SharedPreferences}
     */
    public boolean getBooleanFromSharedPreference(String key) {
        boolean value = false;
        if (mSharedpreferences.contains(key)) {
            value = mSharedpreferences.getBoolean(key, false);
        }
        Log.d(TAG, "key " + key + " value = " + value);
        return value;
    }

    /**
     * Save boolean to the {@link SharedPreferences}
     *
     * @param key   of the preference
     * @param value of the preference
     */
    public void saveBooleanIntoSharedPreference(String key, boolean value) {
        SharedPreferences.Editor prefEditor = getSharedPreferenceEditor();
        prefEditor.putBoolean(key, value);
        Log.d(TAG, "Saved = key " + key + " value = " + value);
        prefEditor.commit();
    }


    /**
     * Remove a particular key and value from the {@link SharedPreferences}
     *
     * @param key of the preference
     */
    public void remove(String key) {
        SharedPreferences.Editor prefEditor = getSharedPreferenceEditor();
        prefEditor.remove(key);
        prefEditor.commit();
    }


    /**
     * Save long to the {@link SharedPreferences}
     *
     * @param key   of the preference
     * @param value of the preference
     */
    public void saveLongIntoSharedPreference(String key, Long value) {

        SharedPreferences.Editor prefEditor = getSharedPreferenceEditor();
        if (value == null) {
            prefEditor.putLong(key, 0L);
        } else {
            prefEditor.putLong(key, value);
        }
        prefEditor.commit();
    }

    /**
     * Get long from {@link SharedPreferences}
     *
     * @return value for the {@link SharedPreferences}
     */
    public long getLongFromSharedPreference(String key) {
        long value = 0L;
        if (mSharedpreferences.contains(key)) {
            value = mSharedpreferences.getLong(key, 0L);
        }
        return value;
    }

}
