package com.twiceyuan.retropreference.typeHandler;

import java.lang.reflect.Type;

/**
 * Created by twiceYuan on 08/02/2017.
 * <p>
 * 供用户定义的可定制的类型存储操作
 */
public abstract class AdvanceHandler<HandleType> {

    /**
     * 处理器实现类
     */
    abstract BaseTypeHandler<HandleType> provideHandler();

    /**
     * 匹配是否为该类型
     *
     * @param type 需要判断的类型值
     * @return 是否匹配
     */
    abstract boolean matchType(Type type);

    /**
     * 优先级是否高于原有的内置操作
     *
     * @return true：在原有的操作之前匹配，false：在原有的类型不符合时才进行匹配
     */
    boolean isInterceptOriginType() {
        return false;
    }
}
