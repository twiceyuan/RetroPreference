package com.twiceyuan.retropreference

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.twiceyuan.retropreference.preferences.AnnotationSettings
import com.twiceyuan.retropreference.preferences.AnnotationSettings2
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * 注解测试
 */
@RunWith(AndroidJUnit4::class)
class AnnotationTestKotlin {

    private lateinit var mSettings: AnnotationSettings
    private lateinit var mSettings2: AnnotationSettings2

    private lateinit var mAppContext: Context

    @Before
    fun useAppContext() {
        // Context of the app under test.
        mAppContext = InstrumentationRegistry.getTargetContext()
        mSettings = RetroPreference.createKt(mAppContext, Context.MODE_PRIVATE)
        mSettings2 = RetroPreference.createKt(mAppContext, Context.MODE_PRIVATE)
    }

    /**
     * 测试 RetroPreference 保存，使用系统方法读取
     */
    @Test
    fun testWriteWithSystemRead() {
        mSettings.username().set("twiceYuan")
        val preferences = mAppContext.getSharedPreferences(
                RetroPreference.getFileName(AnnotationSettings::class.java),
                Context.MODE_PRIVATE)
        val stored = preferences.getString("username", "")
        Assert.assertEquals(stored, "twiceYuan")
        mSettings.username().remove()
    }

    /**
     * 测试 RetroPreference 读取使用系统方法保存的值
     */
    @Test
    fun testReadWithSystemWrite() {
        val preferences = mAppContext.getSharedPreferences(
                RetroPreference.getFileName(AnnotationSettings::class.java),
                Context.MODE_PRIVATE)
        preferences.edit().putString("username", "twiceYuan").apply()

        val storedUsername = mSettings.username().get()
        Assert.assertEquals("twiceYuan", storedUsername)
        mSettings.username().remove()
    }

    /**
     * 测试 Integer 读写
     */
    @Test
    fun testInteger() {
        mSettings.launchCount().set(7)
        val stored = mSettings.launchCount().get()
        if (7 != stored) {
            throw AssertionError()
        }
        mSettings.launchCount().remove()
    }

    /**
     * 测试 Boolean 读写
     */
    @Test
    fun testBoolean() {
        mSettings.isLogin.set(true)
        val isLogin = mSettings.isLogin.get()
        Assert.assertTrue(isLogin!!)
        mSettings.isLogin.remove()
    }

    /**
     * 测试 Float 读写
     */
    @Test
    fun testFloat() {
        mSettings.userPoints().set(1.1f)
        Assert.assertEquals(1.1f, mSettings.userPoints().get()!!, 0f)
        mSettings.userPoints().remove()
    }

    /**
     * 测试 Long 读写
     */
    @Test
    fun testLong() {
        mSettings.lastLogin().set(12378217381L)
        Assert.assertEquals(12378217381f, mSettings.lastLogin().get()!!.toFloat(), 0f)
        mSettings.lastLogin().remove()
    }

    /**
     * 测试 String 读写
     */
    @Test
    fun testString() {
        mSettings.username().set("twiceYuan")
        Assert.assertEquals("twiceYuan", mSettings.username().get())
        mSettings.username().remove()
    }

    /**
     * 测试 StringSet 读写
     */
    @Test
    fun testStringSet() {
        val originSet = TreeSet<String>()
        originSet.add("Hello")
        originSet.add("World")
        originSet.add("Blog")
        originSet.add("Singularity")
        mSettings.userTags().set(originSet)
        val storedSet = mSettings.userTags().get()

        val originArray = originSet.toTypedArray()
        val storedArray = storedSet!!.toTypedArray()

        Arrays.sort(originArray)
        Arrays.sort(storedArray)

        Assert.assertArrayEquals(originArray, storedArray)
        mSettings.userTags().remove()
    }
}
