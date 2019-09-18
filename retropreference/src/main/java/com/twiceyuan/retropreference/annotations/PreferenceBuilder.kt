package com.twiceyuan.retropreference.annotations

import com.twiceyuan.retropreference.Preference
import com.twiceyuan.retropreference.adapters.Adapter
import java.lang.reflect.Type

/**
 * Created by twiceYuan on 23/01/2017.
 *
 *
 * Preference 项构造器
 */
class PreferenceBuilder(val key: String, val type: Type, val adapter: Adapter) {

    fun build(): Preference<Any> {
        return object : Preference<Any> {
            override fun set(t: Any?) {
                adapter.saveValue(key, type, t)
            }

            override fun get(): Any? {
                return adapter.readValue(key, type)
            }

            override fun remove() {
                adapter.saveValue(key, type, null)
            }
        }
    }
}
