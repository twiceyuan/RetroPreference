package com.twiceyuan.retropreference.typeHandler

import android.content.SharedPreferences

/**
 * Created by twiceYuan on 20/01/2017.
 * 
 * Integer handler
 */
internal class IntegerHandler(preferences: SharedPreferences) : BaseTypeHandler<Int>(preferences) {

    override fun setValue(key: String, value: Int?) {
        mPreferences.edit().putInt(key, value ?: 0).apply()
    }

    override operator fun get(key: String, defaultValue: Int?): Int? = mPreferences.getInt(key, defaultValue ?: 0)

    override fun defaultValue(): Int? = 0
}
