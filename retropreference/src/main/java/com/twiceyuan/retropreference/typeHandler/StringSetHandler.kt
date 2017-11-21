package com.twiceyuan.retropreference.typeHandler

import android.annotation.TargetApi
import android.content.SharedPreferences
import android.os.Build

/**
 * Created by twiceYuan on 20/01/2017.

 * StringSet handler
 */
internal class StringSetHandler(preferences: SharedPreferences) : BaseTypeHandler<Set<String>>(preferences) {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun setValue(key: String, value: Set<String>?) {
        mPreferences.edit().putStringSet(key, value).apply()
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun get(key: String, defaultValue: Set<String>?): Set<String>? {
        return mPreferences.getStringSet(key, defaultValue)
    }
}
