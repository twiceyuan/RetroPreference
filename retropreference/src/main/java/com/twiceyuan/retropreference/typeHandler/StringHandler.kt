package com.twiceyuan.retropreference.typeHandler

import android.content.SharedPreferences

/**
 * Created by twiceYuan on 20/01/2017.

 * String handler
 */
internal class StringHandler(preferences: SharedPreferences) : BaseTypeHandler<String>(preferences) {

    override fun setValue(key: String, value: String?) {
        mPreferences.edit().putString(key, value).apply()
    }

    override fun get(key: String, defaultValue: String?): String? = mPreferences.getString(key, defaultValue)
}
