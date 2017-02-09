package com.twiceyuan.retropreference;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.twiceyuan.retropreference.model.MockUser;
import com.twiceyuan.retropreference.preferences.SerializableSettings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by twiceYuan on 09/02/2017.
 *
 * 序列化对象存储测试
 */
public class SerializableTest {

    private Context mAppContext;

    @Before
    public void useAppContext() throws Exception {
        // Context of the app under test.
        mAppContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testSave() {
        MockUser originUser = new MockUser();
        originUser.username = "twiceYuan";
        originUser.password = "123456";
        originUser.age = 99;
        originUser.score = 1203910;

        SerializableSettings settings = RetroPreference.create(
                mAppContext,
                SerializableSettings.class,
                Context.MODE_PRIVATE);

        settings.storedUser().set(originUser);
        MockUser storedUser = settings.storedUser().get();
        Assert.assertEquals(originUser.username, storedUser.username);
        Assert.assertEquals(originUser.password, storedUser.password);
        Assert.assertEquals(originUser.score, storedUser.score, 0);
        Assert.assertEquals(originUser.age, storedUser.age);
    }
}
