package com.twiceyuan.retropreference;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.twiceyuan.retropreference.exceptions.FileNameError;
import com.twiceyuan.retropreference.exceptions.KeyNameError;
import com.twiceyuan.retropreference.preferences.IllegalFileName;
import com.twiceyuan.retropreference.preferences.IllegalKeyName;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by twiceYuan on 21/01/2017.
 *
 * 非法配置测试
 */
public class IllegalDefineTest {

    private Context  mAppContext;

    @Before
    public void useAppContext() throws Exception {
        // Context of the app under test.
        mAppContext = InstrumentationRegistry.getTargetContext();
    }

    /**
     * 测试文件名注解时的异常
     */
    @Test
    public void illegalFileName() {
        try {
            RetroPreference.INSTANCE.create(mAppContext, IllegalFileName.class, Context.MODE_PRIVATE);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof FileNameError);
        }
    }

    @Test
    public void illegalKeyName() {
        try {
            IllegalKeyName settings = RetroPreference.INSTANCE.create(mAppContext, IllegalKeyName.class, Context.MODE_PRIVATE);
            settings.helloString();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof KeyNameError);
        }
    }
}
