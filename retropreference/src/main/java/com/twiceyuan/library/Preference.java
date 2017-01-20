package com.twiceyuan.library;

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * Preference Adapter
 */
public interface Preference<T> {
    void set(T t);
    T get();
    T get(T defaultValue);
}
