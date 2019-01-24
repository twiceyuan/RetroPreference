package com.twiceyuan.retropreference;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.twiceyuan.retropreference.model.MockUser;
import com.twiceyuan.retropreference.preferences.ClearablePreference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by twiceYuan on 10/02/2017.
 *
 * 测试 clear 功能
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
        ClearablePreference preference = RetroPreference.create(
                mAppContext,
                ClearablePreference.class,
                Context.MODE_PRIVATE);

        preference.username().set("twiceYuan");
        preference.password().set("password");

        MockUser mockUser = new MockUser();
        mockUser.username = "twiceYuan";
        preference.user().set(mockUser);

        Assert.assertEquals("twiceYuan", preference.username().get());
        Assert.assertEquals("password", preference.password().get());

        MockUser user = preference.user().get();
        Assert.assertNotNull(user);
        Assert.assertEquals("twiceYuan", user.username);

        preference.clear();

        Assert.assertNull(preference.username().get());
        Assert.assertNull(preference.username().get());
        Assert.assertNull(preference.user().get());
    }
}
