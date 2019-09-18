package com.twiceyuan.retropreference.adapters

import java.lang.reflect.Type

interface Adapter {

    /**
     * Save a value by key
     */
    fun saveValue(key: String, type: Type, value: Any?)

    /**
     * Read a value by key
     */
    fun readValue(key: String, type: Type): Any?

    /**
     * Clear all value
     */
    fun clear()

    /**
     * Get all keys
     */
    fun allKeys(): Set<String>
}