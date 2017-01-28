package com.twiceyuan.retropreference.typeHandler;

import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * StringSet handler
 */
class StringSetHandler extends BaseTypeHandler<Set<String>> {

    StringSetHandler(SharedPreferences preferences) {
        super(preferences);
    }

    @Override
    public void setValue(String key, Set<String> value) {
        mPreferences.edit().putStringSet(key, value).apply();
    }

    @Override
    public Set<String> get(String key, Set<String> defaultValue) {
        return mPreferences.getStringSet(key, defaultValue);
    }
}
