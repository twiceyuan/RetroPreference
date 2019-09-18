package com.twiceyuan.retropreference.javasample;

import com.twiceyuan.retropreference.KVStorage;
import com.twiceyuan.retropreference.Preference;

import java.util.HashMap;

/**
 * Created by twiceYuan on 2017/5/19.
 *
 * Settings
 */
public interface Settings extends KVStorage {

    /**
     * Mark launch count
     */
    Preference<Integer> launchCount();

    /**
     * Save current user instance
     */
    Preference<User> currentUser();

    /**
     * Map type test
     */
    Preference<HashMap<String, Integer>> testMap();
}
