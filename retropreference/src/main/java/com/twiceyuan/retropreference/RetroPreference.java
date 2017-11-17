package com.twiceyuan.retropreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.twiceyuan.retropreference.annotations.FileName;
import com.twiceyuan.retropreference.annotations.KeyName;
import com.twiceyuan.retropreference.annotations.PreferenceBuilder;
import com.twiceyuan.retropreference.exceptions.KeyNameError;
import com.twiceyuan.retropreference.exceptions.PreferenceNotCreateException;
import com.twiceyuan.retropreference.typeHandler.BaseTypeHandler;
import com.twiceyuan.retropreference.typeHandler.SerializableHandler;
import com.twiceyuan.retropreference.typeHandler.TypeHandlerFactory;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by twiceYuan on 20/01/2017.
 * <p>
 * Build preferences helper class that user defined.
 */
public class RetroPreference {

    private static Map<Class, Object> mPreferenceCache;

    private static boolean sEnableCache = true;

    private static Class sDefaultPreferenceClass = null;

    public static <T> T create(final Context context, final Class<T> preferenceClass, int mode) {

        Map<Class, Object> cachePreference = getPreferenceCache();
        Object cached = cachePreference.get(preferenceClass);
        if (sEnableCache && cached != null) {
            //noinspection unchecked
            return (T) cached;
        }

        final String preferenceName = getFileName(preferenceClass);
        final SharedPreferences preferences = context.getSharedPreferences(preferenceName, mode);

        ClassLoader loader = preferenceClass.getClassLoader();
        final Class<?> implementClassed[] = new Class[]{preferenceClass};

        //noinspection unchecked
        T proxy = (T) Proxy.newProxyInstance(loader, implementClassed, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if (proxy instanceof Clearable && handleClearMethod(preferences, method)) {
                    return null;
                }

                final String key = getKeyNameFromMethod(method);
                final Type returnType = method.getGenericReturnType();
                // checked
                Type preferenceType = Utils.getParameterUpperBound(0, (ParameterizedType) returnType);
                BaseTypeHandler handler = TypeHandlerFactory.build(preferences, preferenceType);

                if (handler == null && isSerializable(preferenceType)) {
                    handler = new SerializableHandler(preferences,
                            getFileName(preferenceClass),
                            context,
                            preferenceType);
                }

                // if handler is still null
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

    private static boolean isSerializable(Type type) {
        Class[] interfaces = ((Class) type).getInterfaces();
        if (interfaces == null || interfaces.length == 0) return false;
        for (Class implInterface : interfaces) {
            if (implInterface == Serializable.class) {
                return true;
            }
        }
        return false;
    }

    private static boolean handleClearMethod(SharedPreferences preferences, Method method) {
        if (method.getDeclaringClass() == Clearable.class) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            return true;
        } else {
            return false;
        }
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

        for (Annotation annotation : annotations) {
            if (annotation instanceof FileName) {
                return ((FileName) annotation).value();
            }
        }

        return preferenceClass.getSimpleName();
    }

    private synchronized static Map<Class, Object> getPreferenceCache() {
        if (mPreferenceCache == null) {
            mPreferenceCache = new HashMap<>();
        }
        return mPreferenceCache;
    }

    public static <T> T getCache(Class<T> preferenceClass) {
        Object preferenceObject = getPreferenceCache().get(preferenceClass);
        if (preferenceObject == null) {
            throw new PreferenceNotCreateException(preferenceClass);
        }
        //noinspection unchecked
        return (T) preferenceObject;
    }

    public static void initDefaultPreference(Context context, Class preferenceClass, int mode) {
        create(context, preferenceClass, mode);
        sDefaultPreferenceClass = preferenceClass;
    }

    public static <T> T getDefault() {

        if (sDefaultPreferenceClass == null) {
            throw new RuntimeException("使用 getDefault() 前请先调用 setDefaultPreference 进行初始化");
        }

        Object defaultCache = getCache(sDefaultPreferenceClass);
        try {
            //noinspection unchecked
            return (T) defaultCache;
        } catch (ClassCastException e) {
            throw new RuntimeException("获取的类型与期望类型不匹配，请确认 setDefaultPreference 传入的类型");
        }
    }

    static void setEnableCache(boolean enableCache) {
        sEnableCache = enableCache;
    }
}
