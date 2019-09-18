package com.twiceyuan.retropreference

import android.content.Context
import com.twiceyuan.retropreference.adapters.SharedPreferencesAdapter
import com.twiceyuan.retropreference.annotations.FileName
import com.twiceyuan.retropreference.annotations.KeyName
import com.twiceyuan.retropreference.annotations.PreferenceBuilder
import com.twiceyuan.retropreference.exceptions.KeyNameError
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy

/**
 * Created by twiceYuan on 20/01/2017.
 *
 *
 * Build preferences helper class that user defined.
 */
object RetroPreference {

    private const val SERIALIZABLE_FILE_PREFIX = "RetroPreference_"

    @JvmStatic
    fun <T : KVStorage> create(context: Context, preferenceClass: Class<T>, mode: Int): T = createKt(context, preferenceClass, mode)

    inline fun <reified T : KVStorage> createKt(context: Context, mode: Int): T = createKt(context, T::class.java, mode)

    fun <T : KVStorage> createKt(context: Context, preferenceClass: Class<T>, mode: Int): T {

        val preferenceName = getFileName(preferenceClass)

        val adapter = SharedPreferencesAdapter(context, preferenceName)
        val loader = preferenceClass.classLoader
        val implementClassed = arrayOf<Class<*>>(preferenceClass)

        @Suppress("UNCHECKED_CAST")
        return Proxy.newProxyInstance(loader, implementClassed, InvocationHandler { _, method, _ ->
            if (method.name == KVStorage::clear.name) {
                adapter.clear()
                return@InvocationHandler null
            }

            if (method.name == KVStorage::allKeys.name) {
                return@InvocationHandler adapter.allKeys()
            }

            val key = getKeyNameFromMethod(method)
            val returnType = method.genericReturnType
            val preferenceType = getParameterUpperBound(0, returnType as ParameterizedType)
            PreferenceBuilder(key, preferenceType, adapter).build()
        }) as T
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
