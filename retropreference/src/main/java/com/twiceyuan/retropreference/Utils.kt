package com.twiceyuan.retropreference

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType

/**
 * Created by twiceYuan on 20/01/2017.

 * 类型操作的一些工具
 */

/**
 * copy from retrofit.
 */
internal fun getParameterUpperBound(index: Int, type: ParameterizedType): Type {
    val types = type.actualTypeArguments
    if (index < 0 || index >= types.size) {
        throw IllegalArgumentException("Index " + index + " not in range [0," + types.size + ") for " + type)
    }
    val paramType = types[index]
    if (paramType is WildcardType) {
        return paramType.upperBounds[0]
    }
    return paramType
}
