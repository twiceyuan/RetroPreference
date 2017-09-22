package com.twiceyuan.retropreference.exceptions;

/**
 * Created by twiceYuan on 2017/9/22.
 *
 * 提示用户获取缓存前需要先创建对象
 */
public class PreferenceNotCreateException extends RuntimeException {

    public PreferenceNotCreateException(Class preferenceClass) {
        super(preferenceClass.getName() + "缓存对象未找到，使用前请先确定已调用 create 方法创建 preference 对象");
    }
}
