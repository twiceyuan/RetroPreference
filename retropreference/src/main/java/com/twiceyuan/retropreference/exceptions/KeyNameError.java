package com.twiceyuan.retropreference.exceptions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by twiceYuan on 21/01/2017.
 *
 * 配置项 Key name 配置错误
 */
public class KeyNameError extends IllegalStateException {

    public KeyNameError(Method method) {
        super("RetroPreference 的方法注解只能为 KeyName，而方法 " + method.getName() + " 中发现了不止一个注解");
    }

    public KeyNameError(Method method, Annotation annotation) {
        super("RetroPreference 的方法注解只能为 KeyName，而方法 " + method.getName() + " 发现了注解" + annotation.getClass().getName());
    }
}
