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

    @Suppress("UNCHECKED_CAST")
    fun build(preferences: SharedPreferences, type: Type): BaseTypeHandler<Any>? = when (type) {

        Int::class.java, Integer::class.java, Int::class, Integer::class -> {
            IntegerHandler(preferences) as BaseTypeHandler<Any>
        }

        Boolean::class.java, java.lang.Boolean::class.java -> {
            BooleanHandler(preferences) as BaseTypeHandler<Any>
        }

        Long::class.java, java.lang.Long::class.java -> {
            LongHandler(preferences) as BaseTypeHandler<Any>
        }

        Float::class.java, java.lang.Float::class.java -> {
            FloatHandler(preferences) as BaseTypeHandler<Any>
        }

        String::class.java, java.lang.String::class.java -> {
            StringHandler(preferences) as BaseTypeHandler<Any>
        }

        is ParameterizedType -> {
            val rawType = type.rawType as Class<*>
            val genericType = getParameterUpperBound(0, type) as Class<*>

            if (rawType == Set::class.java && genericType == String::class.java) {
                StringSetHandler(preferences) as BaseTypeHandler<Any>
            } else {
                null
            }
        }
        else -> null
    }
}
