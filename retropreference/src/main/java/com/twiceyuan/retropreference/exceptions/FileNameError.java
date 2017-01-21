package com.twiceyuan.retropreference.exceptions;

import java.lang.annotation.Annotation;

/**
 * Created by twiceYuan on 21/01/2017.
 * <p>
 * 文件名配置错误
 */
public class FileNameError extends IllegalStateException {
    public FileNameError(Class preferenceClass) {
        super("RetroPreference 的接口注解只能为 FileName，而类 " + preferenceClass.getName() + " 中发现了不止一个注解");
    }

    public FileNameError(Class preferenceClass, Annotation annotation) {
        super("RetroPreference 的类注解只能为 FileName，而类 " + preferenceClass.getName() + " 发现了注解" + annotation.getClass().getName());
    }
}
