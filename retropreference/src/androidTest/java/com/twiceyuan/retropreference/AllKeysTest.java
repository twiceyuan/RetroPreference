package com.twiceyuan.retropreference;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.twiceyuan.retropreference.preferences.Settings;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class AllKeysTest {

    private Settings settings;

    @Before
    public void useAppContext() {
        Context context = InstrumentationRegistry.getTargetContext();
        settings = RetroPreference.create(context, Settings.class, Context.MODE_PRIVATE);
    }

    @Test
    public void allKeys() {
        settings.is_login().set(true);
        settings.last_login().set(System.currentTimeMillis());
        settings.launch_count().set(1024);
        settings.user_points().set(12.23f);
        settings.username().set("twiceYuan");
        HashSet<String> userTags = new HashSet<>();
        userTags.add("Tag1");
        userTags.add("Tag2");
        userTags.add("Tag3");
        settings.user_tags().set(userTags);

        List<String> definedKeys = Arrays.asList(
                "is_login",
                "last_login",
                "user_points",
                "launch_count",
                "username",
                "user_tags"
        );

        List<String> savedAllKeys = new ArrayList<>(settings.allKeys());

        Collections.sort(definedKeys);
        Collections.sort(savedAllKeys);

        Assert.assertEquals(definedKeys, savedAllKeys);
    }

    @After
    public void clear() {
        settings.clear();
    }
}
