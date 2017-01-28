package com.twiceyuan.retropreference.preferences;

import com.twiceyuan.retropreference.Preference;
import com.twiceyuan.retropreference.annotations.FileName;
import com.twiceyuan.retropreference.annotations.KeyName;

import java.util.Set;

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * Mock setting items
 */
@FileName("annotation_sp_file")
public interface AnnotationSettings {

    @KeyName("launch_count")
    Preference<Integer> launchCount();

    @KeyName("is_login")
    Preference<Boolean> isLogin();

    @KeyName("user_points")
    Preference<Float> userPoints();

    @KeyName("last_login")
    Preference<Long> lastLogin();

    @KeyName("username")
    Preference<String> username();

    @KeyName("user_tags")
    Preference<Set<String>> userTags();
}
