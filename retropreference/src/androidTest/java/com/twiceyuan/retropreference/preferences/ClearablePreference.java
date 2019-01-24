package com.twiceyuan.retropreference.preferences;

import com.twiceyuan.retropreference.Clearable;
import com.twiceyuan.retropreference.Preference;
import com.twiceyuan.retropreference.model.MockUser;

/**
 * Created by twiceYuan on 10/02/2017.
 * <p>
 * Clearable Preference
 */
public interface ClearablePreference extends Clearable {

    Preference<String> username();

    Preference<String> password();

    Preference<MockUser> user();
}
