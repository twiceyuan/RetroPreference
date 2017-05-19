package com.twiceyuan.retropreference.javasample;

import com.twiceyuan.retropreference.Clearable;
import com.twiceyuan.retropreference.Preference;

/**
 * Created by twiceYuan on 2017/5/19.
 *
 * Settings
 */
public interface Settings extends Clearable {

    /**
     * Mark launch count
     */
    Preference<Integer> launchCount();

    /**
     * Save current user instance
     */
    Preference<User> currentUser();
}
