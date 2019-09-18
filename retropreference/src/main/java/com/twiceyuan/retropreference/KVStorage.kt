package com.twiceyuan.retropreference

/**
 * Created by twiceYuan on 10/02/2017.
 * 如果希望接口可以使用 remove 方法来清空文件里的所有信息，可以继承这个接口
 */
interface KVStorage {

    fun clear()

    fun allKeys(): Set<String>
}
