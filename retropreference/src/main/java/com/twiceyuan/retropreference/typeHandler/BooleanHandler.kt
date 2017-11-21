package com.twiceyuan.retropreference.typeHandler

import android.content.SharedPreferences

/**
 * Created by twiceYuan on 20/01/2017.

 * Boolean handler
 */
class BooleanHandler(preferences: SharedPreferences) : BaseTypeHandler<Boolean>(preferences) {

    override fun setValue(key: String, value: Boolean?) {
        mPreferences.edit().putBoolean(key, value ?: false).apply()
    }

    override operator fun get(key: String, defaultValue: Boolean?): Boolean? {
        return mPreferences.getBoolean(key, defaultValue ?: false)
    }

    override fun defaultValue(): Boolean? {
        return false
    }
}
