package com.twiceyuan.library;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Set;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private Settings mSettings;

    @Before
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        mSettings = RetroPreference.create(appContext, Settings.class, Context.MODE_PRIVATE);
    }

    @Test
    public void testInteger() {
        mSettings.launche_count().set(7);
        Integer stored = mSettings.launche_count().get();
        if (7 != stored) {
            throw new AssertionError();
        }
    }

    @Test
    public void testFloat() {
        mSettings.user_points().set(1.1f);
        if (1.1f != mSettings.user_points().get()) {
            throw new AssertionError();
        }
    }

    @Test
    public void testLong() {
        mSettings.last_login().set(12378217381L);
        if (12378217381L != mSettings.last_login().get()) {
            throw new AssertionError();
        }
    }

    @Test
    public void testString() {
        mSettings.username().set("twiceYuan");
        Assert.assertEquals("twiceYuan", mSettings.username().get());
    }

    @Test
    public void testStringSet() {
        Set<String> originSet = new HashSet<>();
        originSet.add("Hello");
        originSet.add("World");
        originSet.add("Blog");
        originSet.add("Singularity");
        mSettings.user_tags().set(originSet);
        Set<String> storedSet = mSettings.user_tags().get();
        for (String s : storedSet) {
            if (!originSet.contains(s)) {
                throw new AssertionError("Stored set has " + s + " but origin set not.");
            }
        }

        for (String s : originSet) {
            if (!storedSet.contains(s)) {
                throw new AssertionError("Origin set has " + s + " but stored set not.");
            }
        }
    }
}
