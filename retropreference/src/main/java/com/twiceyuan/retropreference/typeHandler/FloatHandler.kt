package com.twiceyuan.retropreference.typeHandler

import android.content.SharedPreferences

/**
 * Created by twiceYuan on 20/01/2017.

 * Float handler
 */
internal class FloatHandler(preferences: SharedPreferences) : BaseTypeHandler<Float>(preferences) {

    override fun setValue(key: String, value: Float?) {
        mPreferences.edit().putFloat(key, value ?: 0f).apply()
    }

    override operator fun get(key: String, defaultValue: Float?): Float? {
        return mPreferences.getFloat(key, defaultValue ?: 0f)
    }

    override fun defaultValue(): Float? {
        return 0f
    }
}
