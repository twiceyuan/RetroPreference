package com.twiceyuan.retropreference.preferences;

import com.twiceyuan.retropreference.KVStorage;
import com.twiceyuan.retropreference.Preference;

/**
 * Created by twiceYuan on 10/02/2017.
 * <p>
 * KVStorage Preference
 */
public interface StoragePreference extends KVStorage {

    Preference<String> username();

    Preference<String> password();
}
