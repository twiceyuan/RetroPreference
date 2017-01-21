package com.twiceyuan.retropreference.typeHandler;

import android.content.SharedPreferences;

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * String handler
 */
public class StringHandler extends BaseTypeHandler<String> {

    public StringHandler(SharedPreferences preferences) {
        super(preferences);
    }

    @Override
    public void setValue(String key, String value) {
        mPreferences.edit().putString(key, value).apply();
    }

    @Override
    public String get(String key, String defaultValue) {
        return mPreferences.getString(key, defaultValue);
    }
}
