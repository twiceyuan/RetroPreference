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

    fun build(preferences: SharedPreferences, type: Type): BaseTypeHandler<Any>? {
        if (type == Int::class.java || type === Integer::class.java || type === Int::class || type === Integer::class) {
            @Suppress("UNCHECKED_CAST")
            return IntegerHandler(preferences) as BaseTypeHandler<Any>
        }
        if (type === Boolean::class.java || type === java.lang.Boolean::class.java) {
            @Suppress("UNCHECKED_CAST")
            return BooleanHandler(preferences) as BaseTypeHandler<Any>
        }
        if (type === Long::class.java || type === java.lang.Long::class.java) {
            @Suppress("UNCHECKED_CAST")
            return LongHandler(preferences) as BaseTypeHandler<Any>
        }
        if (type === Float::class.java || type === java.lang.Float::class.java) {
            @Suppress("UNCHECKED_CAST")
            return FloatHandler(preferences) as BaseTypeHandler<Any>
        }
        if (type === String::class.java || type === java.lang.String::class.java) {
            @Suppress("UNCHECKED_CAST")
            return StringHandler(preferences) as BaseTypeHandler<Any>
        }
        if (type is ParameterizedType) {
            val rawType = type.rawType as Class<*>
            val genericType = getParameterUpperBound(0, type) as Class<*>
            @Suppress("UNCHECKED_CAST")
            if (rawType == Set::class.java && genericType == String::class.java) {
                return StringSetHandler(preferences) as BaseTypeHandler<Any>
            }
        }
        return null
    }
}
