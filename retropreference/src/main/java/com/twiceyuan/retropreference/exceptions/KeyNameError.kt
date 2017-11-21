package com.twiceyuan.retropreference.exceptions

import java.lang.reflect.Method

/**
 * Created by twiceYuan on 21/01/2017.

 * 配置项 Key name 配置错误
 */
class KeyNameError : IllegalStateException {

    constructor(method: Method) : super("RetroPreference 的方法注解只能为 KeyName，而方法 " + method.name + " 中发现了不止一个注解") {}

    constructor(method: Method, annotation: Annotation) : super("RetroPreference 的方法注解只能为 KeyName，而方法 " + method.name + " 发现了注解" + annotation.javaClass.name) {}
}
