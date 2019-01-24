package com.twiceyuan.retropreference

import android.content.Context
import android.content.SharedPreferences
import com.twiceyuan.retropreference.annotations.FileName
import com.twiceyuan.retropreference.annotations.KeyName
import com.twiceyuan.retropreference.annotations.PreferenceBuilder
import com.twiceyuan.retropreference.exceptions.KeyNameError
import com.twiceyuan.retropreference.typeHandler.BaseTypeHandler
import com.twiceyuan.retropreference.typeHandler.SerializableHandler
import com.twiceyuan.retropreference.typeHandler.TypeHandlerFactory
import java.io.Serializable
import java.lang.reflect.*

/**
 * Created by twiceYuan on 20/01/2017.
 *
 *
 * Build preferences helper class that user defined.
 */
object RetroPreference {

    private const val SERIALIZABLE_FILE_PREFIX = "RetroPreference_"

    @JvmStatic
    fun <T> create(context: Context, preferenceClass: Class<T>, mode: Int): T = createKt(context, preferenceClass, mode)

    inline fun <reified T> createKt(context: Context, mode: Int): T = createKt(context, T::class.java, mode)

    fun <T> createKt(context: Context, preferenceClass: Class<T>, mode: Int): T {

        val preferenceName = getFileName(preferenceClass)
        val preferences = context.getSharedPreferences(preferenceName, mode)

        val loader = preferenceClass.classLoader
        val implementClassed = arrayOf<Class<*>>(preferenceClass)

        @Suppress("UNCHECKED_CAST")
        return Proxy.newProxyInstance(loader, implementClassed, InvocationHandler { proxy, method, _ ->
            if (proxy is Clearable && handleClearMethod(context, preferenceClass, preferences, method)) {
                return@InvocationHandler null
            }

            val key = getKeyNameFromMethod(method)
            val returnType = method.genericReturnType
            // checked
            val preferenceType = getParameterUpperBound(0, returnType as ParameterizedType)
            var handler: BaseTypeHandler<Any>? = TypeHandlerFactory.build(preferences, preferenceType)

            if (handler == null && isSerializable(preferenceType)) {
                handler = SerializableHandler(preferences, getFileName(preferenceClass), context, preferenceType)
            }

            // if handler is still null
            if (handler == null) {
                val message = "SharedPreferences does not support this type: " + preferenceType.toString()
                throw IllegalStateException(message)
            }
            PreferenceBuilder(key, handler).build()
        }) as T
    }

    private fun isSerializable(type: Type): Boolean {
        val interfaces = if (type is ParameterizedType) {
            (type.rawType as? Class<*>)?.interfaces
        } else {
            (type as? Class<*>)?.interfaces
        }
        if (interfaces == null || interfaces.isEmpty()) return false
        return interfaces.any { it == Serializable::class.java }
    }

    private fun handleClearMethod(
            context: Context,
            preferenceClass: Class<*>,
            preferences: SharedPreferences,
            method: Method
    ): Boolean {
        return if (method.declaringClass == Clearable::class.java) {
            preferences.edit().clear().apply()
            val dir = context.getDir(getFileName(preferenceClass), Context.MODE_PRIVATE)
            if (dir.exists()) {
                dir.listFiles().forEach {
                    if (it.exists()) {
                        it.delete()
                    }
                }
            }
            true
        } else {
            false
        }
    }

    /**
     * 获得方法的 key 值。优先使用注解值，注解值不存在时使用方法名作为 key

     * @param method 定义的方法集接口方法
     * *
     * @return 该项 sp 的 key 值
     */
    private fun getKeyNameFromMethod(method: Method): String {
        val annotations = method.annotations
        if (annotations.isEmpty()) {
            return method.name
        }

        if (annotations.size != 1) {
            throw KeyNameError(method)
        }

        val annotation = annotations[0]
        if (annotation is KeyName) {
            return annotation.value
        } else {
            throw KeyNameError(method, annotation)
        }
    }

    /**
     * 获得 SP 文件的名称。优先使用注解值，注解值不存在时使用类名作为 file name

     * @param preferenceClass 定义的方法集接口
     * *
     * @return 该项 sp 文件的 file name
     */
    fun getFileName(preferenceClass: Class<*>): String {
        val annotations = preferenceClass.annotations
        if (annotations.isEmpty()) {
            return preferenceClass.simpleName
        }

        annotations
                .filterIsInstance<FileName>()
                .forEach { return it.value }

        return SERIALIZABLE_FILE_PREFIX + preferenceClass.simpleName
    }
}
