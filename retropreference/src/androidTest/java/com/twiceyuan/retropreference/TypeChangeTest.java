package com.twiceyuan.retropreference;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.twiceyuan.retropreference.model.OriginModel;
import com.twiceyuan.retropreference.preferences.TypeChangeAfter;
import com.twiceyuan.retropreference.preferences.TypeChangeBefore;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by twiceYuan on 09/02/2017.
 *
 * 序列化对象存储测试
 */
public class TypeChangeTest {

    private Context mAppContext;

    @Before
    public void useAppContext() {
        // Context of the app under test.
        mAppContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testSave() {
        TypeChangeBefore preferences = RetroPreference.create(
                mAppContext,
                TypeChangeBefore.class,
                Context.MODE_PRIVATE
        );

        preferences.keep().set(1024);
        preferences.isChanged().set(true);
        preferences.model().set(new OriginModel());

        TypeChangeAfter preferences2 = RetroPreference.create(
                mAppContext,
                TypeChangeAfter.class,
                Context.MODE_PRIVATE
        );

        Assert.assertEquals(Integer.valueOf(1024), preferences2.keep().get());
        Assert.assertNull(preferences2.isChanged().get());
        Assert.assertNull(preferences2.model().get());
    }
}
