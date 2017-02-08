package com.twiceyuan.retropreference.typeHandler;

import android.content.SharedPreferences;

import com.twiceyuan.retropreference.Utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * Type handler factory
 */
public final class TypeHandlerFactory {

    private static List<AdvanceHandler<?>> sPreviousHandler = new ArrayList<>();
    private static List<AdvanceHandler<?>> sAfterHandler    = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public static <T> BaseTypeHandler<T> build(SharedPreferences preferences, Type type) {

        BaseTypeHandler previousHandler = interceptUserDefinedType(type, sPreviousHandler);
        if (previousHandler != null) {
            return previousHandler;
        }

        if (type == Integer.class) {
            return (BaseTypeHandler<T>) new IntegerHandler(preferences);
        }
        if (type == Boolean.class) {
            return (BaseTypeHandler<T>) new BooleanHandler(preferences);
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
            // if generic type is Set<String>
            if (rawType == Set.class && genericType == String.class) {
                return (BaseTypeHandler<T>) new StringSetHandler(preferences);
            }
        }

        BaseTypeHandler afterHandler = interceptUserDefinedType(type, sAfterHandler);
        if (afterHandler != null) {
            return afterHandler;
        }

        return null;
    }

    private static BaseTypeHandler interceptUserDefinedType(Type type, List<AdvanceHandler<?>> handlers) {
        for (AdvanceHandler<?> handler : handlers) {
            if (handler.matchType(type)) {
                return handler.provideHandler();
            }
        }
        return null;
    }

    public static void addUserDefinedHandler(AdvanceHandler<?> handler) {
        if (handler.isInterceptOriginType()) {
            sPreviousHandler.add(handler);
        } else {
            sAfterHandler.add(handler);
        }
    }

    public static void clearUserHandlers() {
        sPreviousHandler.clear();
        sAfterHandler.clear();
    }
}
