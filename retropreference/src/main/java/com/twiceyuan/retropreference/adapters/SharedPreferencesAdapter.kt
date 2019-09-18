package com.twiceyuan.retropreference.adapters

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.twiceyuan.retropreference.getParameterUpperBound
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

private const val TAG = "SPAdapter"

class SharedPreferencesAdapter(context: Context, fileName: String) : Adapter {

    private val preferences: SharedPreferences =
            context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    enum class SharePreferencesValueType {
        INT,
        BOOLEAN,
        LONG,
        FLOAT,
        STRING,
        STRING_SET,
        NOT_SUPPORT,
    }

    /**
     * 判断类型是 SharedPreferences 支持的哪种类型
     */
    private fun Type.toPreferencesType(): SharePreferencesValueType = when (this) {
        Int::class.java, Integer::class.java, Int::class, Integer::class ->
            SharePreferencesValueType.INT

        Boolean::class.java, java.lang.Boolean::class.java ->
            SharePreferencesValueType.BOOLEAN

        Long::class.java, java.lang.Long::class.java ->
            SharePreferencesValueType.LONG

        Float::class.java, java.lang.Float::class.java ->
            SharePreferencesValueType.FLOAT

        String::class.java, java.lang.String::class.java ->
            SharePreferencesValueType.STRING

        is ParameterizedType -> {
            val rawType = rawType as Class<*>
            val genericType = getParameterUpperBound(0, this) as Class<*>

            if (Set::class.java.isAssignableFrom(rawType) && genericType == String::class.java) {
                SharePreferencesValueType.STRING_SET
            } else {
                SharePreferencesValueType.NOT_SUPPORT
            }
        }

        else -> SharePreferencesValueType.NOT_SUPPORT
    }


    override fun saveValue(key: String, type: Type, value: Any?) {
        val editor = preferences.edit()
        if (value == null) {
            editor.remove(key)
            editor.apply()
            return
        }

        when (type.toPreferencesType()) {
            SharePreferencesValueType.INT -> {
                editor.putInt(key, value as Int)
                editor.apply()
            }
            SharePreferencesValueType.BOOLEAN -> {
                editor.putBoolean(key, value as Boolean)
                editor.apply()
            }
            SharePreferencesValueType.LONG -> {
                editor.putLong(key, value as Long)
                editor.apply()
            }
            SharePreferencesValueType.FLOAT -> {
                editor.putFloat(key, value as Float)
                editor.apply()
            }
            SharePreferencesValueType.STRING -> {
                editor.putString(key, value as String)
                editor.apply()
            }
            SharePreferencesValueType.STRING_SET -> {
                @Suppress("UNCHECKED_CAST")
                editor.putStringSet(key, value as Set<String>)
                editor.apply()
                return
            }
            SharePreferencesValueType.NOT_SUPPORT -> {
                Log.e(TAG, "[saveValue]${this::class.java.name} not support type: $type")
            }
        }
    }

    override fun readValue(key: String, type: Type): Any? {

        if (!preferences.contains(key)) {
            return null
        }

        // Catch 类发生变化的情况，打印日志并返回 null
        return try {
            readValue(type, key)
        } catch (e: ClassCastException) {
            Log.i(TAG, "Value of [key=$key] was changed: ${e.message}")
            null
        }
    }

    private fun readValue(type: Type, key: String): Any? {
        // 下面所有 defValue 实际都不会用到，因为前面已经进行过判空
        return when (type.toPreferencesType()) {
            SharePreferencesValueType.INT -> {
                preferences.getInt(key, -1)
            }

            SharePreferencesValueType.BOOLEAN -> {
                preferences.getBoolean(key, false)
            }

            SharePreferencesValueType.LONG -> {
                preferences.getLong(key, -1)
            }

            SharePreferencesValueType.FLOAT -> {
                preferences.getFloat(key, -1f)
            }

            SharePreferencesValueType.STRING -> {
                preferences.getString(key, null)
            }

            SharePreferencesValueType.STRING_SET -> {
                preferences.getStringSet(key, null)
            }

            SharePreferencesValueType.NOT_SUPPORT -> {
                Log.e(TAG, "[readValue]${this::class.java.name} not support type: $type")
                null
            }
        }
    }

    override fun clear() = preferences.edit().clear().apply()

    override fun allKeys(): Set<String> = preferences.all.keys
}
