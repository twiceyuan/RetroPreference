package com.twiceyuan.retropreference;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.twiceyuan.retropreference.preferences.Settings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by twiceYuan on 23/01/2017.
 *
 * 测试添加缓存后每次获取对速度的影响
 */
@RunWith(AndroidJUnit4.class)
public class CacheTest {

    private Context  mAppContext;

    @Before
    public void useAppContext() throws Exception {
        // Context of the app under test.
        mAppContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testNoCached() {
        RetroPreference.setEnableCache(false);
        for (int i = 0; i < 10000; i++) {
            RetroPreference.create(mAppContext, Settings.class, Context.MODE_PRIVATE);
        }
    }

    @Test
    public void testCached() {
        RetroPreference.setEnableCache(true);
        for (int i = 0; i < 10000; i++) {
            RetroPreference.create(mAppContext, Settings.class, Context.MODE_PRIVATE);
        }
    }
}
