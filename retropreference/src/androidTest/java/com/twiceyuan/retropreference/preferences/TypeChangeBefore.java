package com.twiceyuan.retropreference.preferences;

import com.twiceyuan.retropreference.KVStorage;
import com.twiceyuan.retropreference.Preference;
import com.twiceyuan.retropreference.annotations.FileName;
import com.twiceyuan.retropreference.model.OriginModel;

/**
 * Created by twiceYuan on 2017/3/14.
 *
 * 改变类型之前的配置
 */
@FileName("test_type_change")
public interface TypeChangeBefore extends KVStorage {

    Preference<Integer> keep();

    Preference<Boolean> isChanged();

    Preference<OriginModel> model();
}
