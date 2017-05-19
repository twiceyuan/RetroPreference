package com.twiceyuan.retropreference

import org.jetbrains.annotations.NotNull

/**
 * Created by twiceYuan on 20/01/2017.

 * Preference Adapter
 */
interface Preference<T> {
    fun set(t: T?)
    fun get(): T?
    fun getWithDefault(@NotNull defaultValue: T): T
    fun clear()
}
