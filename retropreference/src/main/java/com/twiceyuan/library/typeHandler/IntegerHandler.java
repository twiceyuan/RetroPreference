package com.twiceyuan.library.typeHandler;

import android.content.SharedPreferences;

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * Integer handler
 */
public class IntegerHandler extends BaseTypeHandler<Integer> {

    public IntegerHandler(SharedPreferences preferences) {
        super(preferences);
    }

    @Override
    public void setValue(String key, Integer value) {
        mPreferences.edit().putInt(key, value).apply();
    }

    @Override
    public Integer get(String key, Integer defaultValue) {
        return mPreferences.getInt(key, defaultValue);
    }

    @Override
    public Integer defaultValue() {
        return 0;
    }
}
