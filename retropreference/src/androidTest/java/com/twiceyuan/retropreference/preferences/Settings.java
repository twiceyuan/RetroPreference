package com.twiceyuan.retropreference.preferences;

import com.twiceyuan.retropreference.Preference;

import java.util.Set;

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * Mock setting items
 */
public interface Settings {

    Preference<Integer> launch_count();

    Preference<Boolean> is_login();

    Preference<Float> user_points();

    Preference<Long> last_login();

    Preference<String> username();

    Preference<Set<String>> user_tags();
}
