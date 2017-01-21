package com.twiceyuan.library;

import android.content.Context;
import android.content.SharedPreferences;

import com.twiceyuan.library.typeHandler.BaseTypeHandler;
import com.twiceyuan.library.typeHandler.TypeHandlerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * Created by twiceYuan on 20/01/2017.
 * <p>
 * Build preferences helper class that user defined.
 */
public class RetroPreference {

    public static <T> T create(Context context, Class<T> preferenceClass, int mode) {

        String preferenceName = preferenceClass.getSimpleName();
        final SharedPreferences preferences = context.getSharedPreferences(preferenceName, mode);

        ClassLoader loader = preferenceClass.getClassLoader();
        Class<?> implementClassed[] = new Class[]{preferenceClass};

        //noinspection unchecked
        return (T) Proxy.newProxyInstance(loader, implementClassed, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                final String key = method.getName();
                Type returnType = method.getGenericReturnType();
                // checked
                Type returnTypeClass = Utils.getParameterUpperBound(0, (ParameterizedType) returnType);
                final BaseTypeHandler handler = TypeHandlerFactory.build(preferences, returnTypeClass);
                if (handler == null) {
                    throw new IllegalStateException("Sp does not support this type:" + returnTypeClass.toString());
                }
                return new Preference() {
                    @Override
                    public void set(Object value) {
                        //noinspection unchecked
                        handler.setValue(key, value);
                    }

                    @Override
                    public Object get() {
                        return getWithDefault(handler.defaultValue());
                    }

                    @Override
                    public Object getWithDefault(Object defaultValue) {
                        //noinspection unchecked
                        return handler.get(key, defaultValue);
                    }

                    @Override
                    public void clear() {
                        handler.clear(key);
                    }
                };
            }
        });
    }
}
