package com.twiceyuan.retropreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.twiceyuan.retropreference.preferences.AnnotationSettings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * 注解测试
 */
@RunWith(AndroidJUnit4.class)
public class AnnotationTest {

    private AnnotationSettings mSettings;

    private Context mAppContext;

    @Before
    public void useAppContext() throws Exception {
        // Context of the app under test.
        mAppContext = InstrumentationRegistry.getTargetContext();
        mSettings = RetroPreference.create(mAppContext, AnnotationSettings.class, Context.MODE_PRIVATE);
    }

    /**
     * 测试 RetroPreference 保存，使用系统方法读取
     */
    @Test
    public void testWriteWithSystemRead() {
        mSettings.username().set("twiceYuan");
        SharedPreferences preferences = mAppContext.getSharedPreferences(
                RetroPreference.INSTANCE.getFileName(AnnotationSettings.class),
                Context.MODE_PRIVATE);
        String stored = preferences.getString("username", "");
        if (!stored.equals("twiceYuan")) {
            throw new AssertionError();
        }
        mSettings.username().remove();
    }

    /**
     * 测试 RetroPreference 读取使用系统方法保存的值
     */
    @Test
    public void testReadWithSystemWrite() {
        SharedPreferences preferences = mAppContext.getSharedPreferences(
                RetroPreference.INSTANCE.getFileName(AnnotationSettings.class),
                Context.MODE_PRIVATE);
        preferences.edit().putString("username", "twiceYuan").apply();

        String storedUsername = mSettings.username().get();
        Assert.assertEquals("twiceYuan", storedUsername);
        mSettings.username().remove();
    }

    /**
     * 测试 Integer 读写
     */
    @Test
    public void testInteger() {
        mSettings.launchCount().set(7);
        Integer stored = mSettings.launchCount().get();
        if (7 != stored) {
            throw new AssertionError();
        }
        mSettings.launchCount().remove();
    }

    /**
     * 测试 Boolean 读写
     */
    @Test
    public void testBoolean() {
        mSettings.isLogin().set(true);
        Boolean isLogin = mSettings.isLogin().get();
        Assert.assertTrue(isLogin);
        mSettings.isLogin().remove();
    }

    /**
     * 测试 Float 读写
     */
    @Test
    public void testFloat() {
        mSettings.userPoints().set(1.1f);
        Assert.assertEquals(1.1f, mSettings.userPoints().get(), 0);
        mSettings.userPoints().remove();
    }

    /**
     * 测试 Long 读写
     */
    @Test
    public void testLong() {
        mSettings.lastLogin().set(12378217381L);
        Assert.assertEquals(12378217381L, mSettings.lastLogin().get(), 0);
        mSettings.lastLogin().remove();
    }

    /**
     * 测试 String 读写
     */
    @Test
    public void testString() {
        mSettings.username().set("twiceYuan");
        Assert.assertEquals("twiceYuan", mSettings.username().get());
        mSettings.username().remove();
    }

    /**
     * 测试 StringSet 读写
     */
    @Test
    public void testStringSet() {
        Set<String> originSet = new TreeSet<>();
        originSet.add("Hello");
        originSet.add("World");
        originSet.add("Blog");
        originSet.add("Singularity");
        mSettings.userTags().set(originSet);
        Set<String> storedSet = mSettings.userTags().get();

        Object[] originArray = originSet.toArray();
        Object[] storedArray = storedSet.toArray();

        Arrays.sort(originArray);
        Arrays.sort(storedArray);

        Assert.assertArrayEquals(originArray, storedArray);
        mSettings.userTags().remove();
    }
}
