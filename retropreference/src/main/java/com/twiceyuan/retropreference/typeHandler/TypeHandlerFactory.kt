package com.twiceyuan.retropreference.typeHandler

import android.content.SharedPreferences
import com.twiceyuan.retropreference.getParameterUpperBound
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by twiceYuan on 20/01/2017.

 * Type handler factory
 */
object TypeHandlerFactory {

    fun build(preferences: SharedPreferences, type: Type): BaseTypeHandler<out Any>? {
        if (type === Int::class.java || type === Integer::class.java) {
            return IntegerHandler(preferences)
        }
        if (type === Boolean::class.java) {
            return BooleanHandler(preferences)
        }
        if (type === Long::class.java) {
            return LongHandler(preferences)
        }
        if (type === Float::class.java) {
            return FloatHandler(preferences)
        }
        if (type === String::class.java) {
            return StringHandler(preferences)
        }
        if (type is ParameterizedType) {
            val rawType = type.rawType as Class<*>
            val genericType = getParameterUpperBound(0, type) as Class<*>
            if (rawType == Set::class.java && genericType == String::class.java) {
                return StringSetHandler(preferences)
            }
        }
        return null
    }
}
