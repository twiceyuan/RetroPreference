package com.twiceyuan.retropreference.preferences

import com.twiceyuan.retropreference.Preference
import com.twiceyuan.retropreference.annotations.KeyName

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * Mock setting items
 */
interface AnnotationSettings2 {

    @KeyName("is_login")
    fun isLogin(): Preference<Boolean>

    @KeyName("launch_count")
    fun launchCount(): Preference<Int>

    @KeyName("user_points")
    fun userPoints(): Preference<Float>

    @KeyName("last_login")
    fun lastLogin(): Preference<Long>

    @KeyName("username")
    fun username(): Preference<String>

    @KeyName("user_tags")
    fun userTags(): Preference<Set<String>>
}
