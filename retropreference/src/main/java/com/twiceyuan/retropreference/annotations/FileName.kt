package com.twiceyuan.retropreference.annotations

/**
 * Created by twiceYuan on 21/01/2017.

 * 当没有使用该注解时，默认使用为 RetroPreference 定义的接口类的 SimpleClassName 作为 SP 的 file name。

 * 你也可以使用该注解来单独指明 SP 的 file name
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class FileName(val value: String)
