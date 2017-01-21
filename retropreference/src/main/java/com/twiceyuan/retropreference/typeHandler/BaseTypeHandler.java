package com.twiceyuan.retropreference.typeHandler;

import android.content.SharedPreferences;

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * Base type preferences handler.
 */
public abstract class BaseTypeHandler<Type> {

    protected SharedPreferences mPreferences;

    public BaseTypeHandler(SharedPreferences preferences) {
        mPreferences = preferences;
    }

    public abstract void setValue(String key, Type value);

    public abstract Type get(String key, Type defaultValue);

    public Type defaultValue() {
        return null;
    }

    public void clear(String key) {
        mPreferences.edit().remove(key).apply();
    }
}
