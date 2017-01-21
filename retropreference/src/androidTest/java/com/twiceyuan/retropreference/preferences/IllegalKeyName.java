package com.twiceyuan.retropreference.preferences;

import android.support.test.annotation.Beta;

import com.twiceyuan.retropreference.Preference;

/**
 * Created by twiceYuan on 21/01/2017.
 *
 * 错误的文件名注解测试
 */
public interface IllegalKeyName {

    @Beta
    Preference<String> helloString();
}
