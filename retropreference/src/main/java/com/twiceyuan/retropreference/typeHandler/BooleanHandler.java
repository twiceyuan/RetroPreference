package com.twiceyuan.retropreference.typeHandler;

import android.content.SharedPreferences;

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * Boolean handler
 */
public class BooleanHandler extends BaseTypeHandler<Boolean> {

    public BooleanHandler(SharedPreferences preferences) {
        super(preferences);
    }

    @Override
    public void setValue(String key, Boolean value) {
        mPreferences.edit().putBoolean(key, value).apply();
    }

    @Override
    public Boolean get(String key, Boolean defaultValue) {
        return mPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public Boolean defaultValue() {
        return false;
    }
}
