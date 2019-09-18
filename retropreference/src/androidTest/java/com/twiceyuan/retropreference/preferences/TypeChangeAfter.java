package com.twiceyuan.retropreference.preferences;

import com.twiceyuan.retropreference.KVStorage;
import com.twiceyuan.retropreference.Preference;
import com.twiceyuan.retropreference.annotations.FileName;
import com.twiceyuan.retropreference.model.ChangedModel;

/**
 * Created by twiceYuan on 2017/3/14.
 *
 * 改变类型之后的配置
 */
@FileName("test_type_change")
public interface TypeChangeAfter extends KVStorage {

    Preference<Integer> keep();

    Preference<String> isChanged();

    Preference<ChangedModel> model();
}
