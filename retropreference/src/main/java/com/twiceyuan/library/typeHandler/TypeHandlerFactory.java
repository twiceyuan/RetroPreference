package com.twiceyuan.library.typeHandler;

import android.content.SharedPreferences;

import com.twiceyuan.library.Utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * Type handler factory
 */
public final class TypeHandlerFactory {

    @SuppressWarnings("unchecked")
    public static <T> BaseTypeHandler<T> build(SharedPreferences preferences, Type type) {
        if (type == Integer.class) {
            return (BaseTypeHandler<T>) new IntegerHandler(preferences);
        }
        if (type == Long.class) {
            return (BaseTypeHandler<T>) new LongHandler(preferences);
        }
        if (type == Float.class) {
            return (BaseTypeHandler<T>) new FloatHandler(preferences);
        }
        if (type == String.class) {
            return (BaseTypeHandler<T>) new StringHandler(preferences);
        }
        if (type instanceof ParameterizedType) {
            Class rawType = (Class) ((ParameterizedType) type).getRawType();
            Class genericType = (Class) Utils.getParameterUpperBound(0, (ParameterizedType) type);
            if (rawType == Set.class && genericType == String.class) {
                return (BaseTypeHandler<T>) new StringSetHandler(preferences);
            }
        }
        return null;
    }
}
