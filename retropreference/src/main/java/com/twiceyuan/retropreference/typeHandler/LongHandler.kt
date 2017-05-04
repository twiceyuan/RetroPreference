package com.twiceyuan.retropreference.typeHandler

import android.content.SharedPreferences

/**
 * Created by twiceYuan on 20/01/2017.

 * Long handler
 */
internal class LongHandler(preferences: SharedPreferences) : BaseTypeHandler<Long>(preferences) {

    override fun setValue(key: String, value: Long?) {
        mPreferences.edit().putLong(key, value ?: 0L).apply()
    }

    override operator fun get(key: String, defaultValue: Long?): Long? {
        return mPreferences.getLong(key, defaultValue ?: 0L)
    }

    override fun defaultValue(): Long? {
        return 0L
    }
}
