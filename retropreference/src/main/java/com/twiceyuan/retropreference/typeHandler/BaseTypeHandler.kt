package com.twiceyuan.retropreference.typeHandler

import android.content.SharedPreferences

/**
 * Created by twiceYuan on 20/01/2017.

 * Base type preferences handler.
 */
abstract class BaseTypeHandler<Type>(internal var mPreferences: SharedPreferences) {

    abstract fun setValue(key: String, value: Type?)

    @Throws(ClassCastException::class)
    abstract operator fun get(key: String, defaultValue: Type?): Type?

    open fun defaultValue(): Type? {
        return null
    }

    fun clear(key: String) {
        mPreferences.edit().remove(key).apply()
    }
}
