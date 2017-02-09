package com.twiceyuan.retropreference.preferences;

import com.twiceyuan.retropreference.Preference;
import com.twiceyuan.retropreference.model.MockUser;

/**
 * Created by twiceYuan on 09/02/2017.
 *
 * Serializable 存储测试
 */
public interface SerializableSettings {

    Preference<MockUser> storedUser();
}
