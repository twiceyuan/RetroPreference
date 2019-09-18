package com.twiceyuan.retropreference;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.twiceyuan.retropreference.preferences.StoragePreference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by twiceYuan on 10/02/2017.
 *
 * 测试 remove 功能
 */
public class ClearTest {

    private Context mAppContext;

    @Before
    public void useAppContext() {
        // Context of the app under test.
        mAppContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testClear() {
        StoragePreference preference = RetroPreference.create(
                mAppContext,
                StoragePreference.class,
                Context.MODE_PRIVATE);

        preference.username().set("twiceYuan");
        preference.password().set("password");

        Assert.assertEquals("twiceYuan", preference.username().get());
        Assert.assertEquals("password", preference.password().get());

        preference.clear();

        Assert.assertNull(preference.username().get());
        Assert.assertNull(preference.username().get());
    }
}
