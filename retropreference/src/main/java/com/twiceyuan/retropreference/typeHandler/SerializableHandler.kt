package com.twiceyuan.retropreference.typeHandler

import android.content.Context
import android.content.SharedPreferences
import java.io.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by twiceYuan on 09/02/2017.
 *
 *
 * Serializable 对象存储。实际为保存对象到文件，并非使用 preference
 */
class SerializableHandler(preferences: SharedPreferences,
                          private val mPreferencesName: String,
                          private val mContext: Context,
                          private val mObjectType: Type) : BaseTypeHandler<Any>(preferences) {

    /**
     * 写入对象到文件
     */
    override fun setValue(key: String, value: Any?) {
        val objectFile = getObjectFile(key) ?: return
        try {
            val fileOutput = FileOutputStream(objectFile)
            val objectOutput = ObjectOutputStream(fileOutput)
            objectOutput.writeObject(value)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    /**
     * 读取文件中的对象
     */
    override fun get(key: String, defaultValue: Any?): Any? {
        val objectFile = getObjectFile(key) ?: return defaultValue
        return try {
            val fileInput = FileInputStream(objectFile)
            val objectInput = ObjectInputStream(fileInput)
            val o = objectInput.readObject()
            if (mObjectType is ParameterizedType) {
                (mObjectType.rawType as Class<*>).cast(o)
            } else {
                (mObjectType as Class<*>).cast(o)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    private fun getObjectFile(key: String): File? {
        val dir = mContext.getDir(mPreferencesName, Context.MODE_PRIVATE)
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return null
            }
        }
        return File(dir, key)
    }
}
