package com.twiceyuan.retropreference.annotations

import com.twiceyuan.retropreference.Preference
import com.twiceyuan.retropreference.typeHandler.BaseTypeHandler

/**
 * Created by twiceYuan on 23/01/2017.
 *
 *
 * Preference 项建造器
 */
class PreferenceBuilder {

    private var mKey: String? = null
    private var mTypeHandler: BaseTypeHandler<Any>? = null

    fun setKey(key: String): PreferenceBuilder {
        this.mKey = key
        return this
    }

    fun setTypeHandler(typeHandler: BaseTypeHandler<Any>): PreferenceBuilder {
        mTypeHandler = typeHandler
        return this
    }

    fun build(): Preference<Any> {
        return object : Preference<Any> {
            override fun set(t: Any?) {
                mTypeHandler!!.setValue(mKey!!, t)
            }

            override fun get(): Any? {
                try {
                    return mTypeHandler!![mKey!!, mTypeHandler!!.defaultValue()]
                } catch (e: ClassCastException) {
                    return mTypeHandler!!.defaultValue()
                }
            }

            override fun getWithDefault(defaultValue: Any): Any? {
                try {

                    return mTypeHandler!![mKey!!, defaultValue]
                } catch (e: ClassCastException) {
                    return defaultValue
                }

            }

            override fun clear() {
                mTypeHandler!!.clear(mKey!!)
            }
        }
    }
}
