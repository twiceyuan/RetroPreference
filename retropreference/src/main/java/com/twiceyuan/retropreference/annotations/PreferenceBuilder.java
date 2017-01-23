package com.twiceyuan.retropreference.annotations;

import com.twiceyuan.retropreference.Preference;
import com.twiceyuan.retropreference.typeHandler.BaseTypeHandler;

/**
 * Created by twiceYuan on 23/01/2017.
 * <p>
 * Preference 项建造器
 */
public class PreferenceBuilder {

    private String          mKey;
    private BaseTypeHandler mTypeHandler;

    public PreferenceBuilder setKey(String key) {
        this.mKey = key;
        return this;
    }

    public PreferenceBuilder setTypeHandler(BaseTypeHandler typeHandler) {
        mTypeHandler = typeHandler;
        return this;
    }

    public Preference build() {
        return new Preference() {
            @Override
            public void set(Object o) {
                //noinspection unchecked
                mTypeHandler.setValue(mKey, o);
            }

            @Override
            public Object get() {
                //noinspection unchecked
                return mTypeHandler.get(mKey, mTypeHandler.defaultValue());
            }

            @Override
            public Object getWithDefault(Object defaultValue) {
                //noinspection unchecked
                return mTypeHandler.get(mKey, defaultValue);
            }

            @Override
            public void clear() {
                mTypeHandler.clear(mKey);
            }
        };
    }
}
