package com.twiceyuan.retropreference.sample

import com.twiceyuan.retropreference.Clearable
import com.twiceyuan.retropreference.Preference

/**
 * Created by twiceYuan on 20/01/2017.

 * Mock Settings
 */
interface Settings : Clearable {

    /**
     * Mark launch count
     */
    fun launchCount(): Preference<Int>

    /**
     * Save current user instance
     */
    fun currentUser(): Preference<User>
}
