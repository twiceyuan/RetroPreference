package com.twiceyuan.retropreference.annotations

import com.twiceyuan.retropreference.Preference
import com.twiceyuan.retropreference.typeHandler.BaseTypeHandler

/**
 * Created by twiceYuan on 23/01/2017.
 *
 *
 * Preference 项建造器
 */
class PreferenceBuilder(val key: String, val typeHandler: BaseTypeHandler<Any>) {

    fun build(): Preference<Any> {
        return object : Preference<Any> {
            override fun set(t: Any?) {
                typeHandler.setValue(key, t)
            }

            override fun get(): Any? {
                try {
                    return typeHandler[key, typeHandler.defaultValue()]
                } catch (e: ClassCastException) {
                    return typeHandler.defaultValue()
                }
            }

            override fun getWithDefault(defaultValue: Any): Any? {
                try {
                    return typeHandler[key, defaultValue]
                } catch (e: ClassCastException) {
                    return defaultValue
                }
            }

            override fun clear() {
                typeHandler.clear(key)
            }
        }
    }
}
