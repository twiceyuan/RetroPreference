package com.twiceyuan.retropreference.exceptions

/**
 * Created by twiceYuan on 21/01/2017.
 *
 *
 * 文件名配置错误
 */
class FileNameError : IllegalStateException {
    constructor(preferenceClass: Class<*>) : super("RetroPreference 的接口注解只能为 FileName，而类 " + preferenceClass.name + " 中发现了不止一个注解") {}

    constructor(preferenceClass: Class<*>, annotation: Annotation) : super("RetroPreference 的类注解只能为 FileName，而类 " + preferenceClass.name + " 发现了注解" + annotation.javaClass.name) {}
}
