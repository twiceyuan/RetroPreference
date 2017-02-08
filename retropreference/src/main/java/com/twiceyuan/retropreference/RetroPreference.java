package com.twiceyuan.retropreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.twiceyuan.retropreference.annotations.FileName;
import com.twiceyuan.retropreference.annotations.KeyName;
import com.twiceyuan.retropreference.annotations.PreferenceBuilder;
import com.twiceyuan.retropreference.exceptions.FileNameError;
import com.twiceyuan.retropreference.exceptions.KeyNameError;
import com.twiceyuan.retropreference.typeHandler.AdvanceHandler;
import com.twiceyuan.retropreference.typeHandler.BaseTypeHandler;
import com.twiceyuan.retropreference.typeHandler.TypeHandlerFactory;

import java.lang.annotation.Annotation;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by twiceYuan on 20/01/2017.
 * <p>
 * Build preferences helper class that user defined.
 */
public class RetroPreference {

    private static SoftReference<Map<Class, Object>> mPreferenceCache;
    private static boolean sEnableCache = true;

    public static <T> T create(Context context, final Class<T> preferenceClass, int mode) {

        Map<Class, Object> cachePreference = getPreferenceCache();
        Object cached = cachePreference.get(preferenceClass);
        if (sEnableCache && cached != null) {
            //noinspection unchecked
            return (T) cached;
        }

        final String preferenceName = getFileName(preferenceClass);
        final SharedPreferences preferences = context.getSharedPreferences(preferenceName, mode);

        ClassLoader loader = preferenceClass.getClassLoader();
        Class<?> implementClassed[] = new Class[]{preferenceClass};

        //noinspection unchecked
        T proxy = (T) Proxy.newProxyInstance(loader, implementClassed, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                final String key = getKeyNameFromMethod(method);
                final Type returnType = method.getGenericReturnType();
                // checked
                Type preferenceType = Utils.getParameterUpperBound(0, (ParameterizedType) returnType);
                final BaseTypeHandler handler = TypeHandlerFactory.build(preferences, preferenceType);
                if (handler == null) {
                    throw new IllegalStateException("SharedPreferences does not support this type: " + preferenceType.toString());
                }
                return new PreferenceBuilder()
                        .setKey(key)
                        .setTypeHandler(handler)
                        .build();
            }
        });
        cachePreference.put(preferenceClass, proxy);
        return proxy;
    }

    public static void addUserDefinedHandler(AdvanceHandler<?> handler) {
        TypeHandlerFactory.addUserDefinedHandler(handler);
    }

    public static void clearUserDefinedHandlers() {
        TypeHandlerFactory.clearUserHandlers();
    }

    /**
     * 获得方法的 key 值。优先使用注解值，注解值不存在时使用方法名作为 key
     *
     * @param method 定义的方法集接口方法
     * @return 该项 sp 的 key 值
     */
    private static String getKeyNameFromMethod(Method method) {
        Annotation[] annotations = method.getAnnotations();
        if (annotations == null || annotations.length == 0) {
            return method.getName();
        }

        if (annotations.length != 1) {
            throw new KeyNameError(method);
        }

        Annotation annotation = annotations[0];
        if (annotation instanceof KeyName) {
            return ((KeyName) annotation).value();
        } else {
            throw new KeyNameError(method, annotation);
        }
    }

    /**
     * 获得 SP 文件的名称。优先使用注解值，注解值不存在时使用类名作为 file name
     *
     * @param preferenceClass 定义的方法集接口
     * @return 该项 sp 文件的 file name
     */
    @SuppressWarnings("WeakerAccess")
    public static String getFileName(Class preferenceClass) {
        Annotation[] annotations = preferenceClass.getAnnotations();
        if (annotations == null || annotations.length == 0) {
            return preferenceClass.getSimpleName();
        }

        if (annotations.length != 1) {
            throw new FileNameError(preferenceClass);
        }

        Annotation annotation = annotations[0];
        if (annotation instanceof FileName) {
            return ((FileName) annotation).value();
        } else {
            throw new FileNameError(preferenceClass, annotation);
        }
    }

    private static Map<Class, Object> getPreferenceCache() {
        if (mPreferenceCache == null || mPreferenceCache.get() == null) {
            mPreferenceCache = new SoftReference<Map<Class, Object>>(new ConcurrentHashMap<Class, Object>());
        }
        return mPreferenceCache.get();
    }

    static void setEnableCache(boolean enableCache) {
        sEnableCache = enableCache;
    }
}
