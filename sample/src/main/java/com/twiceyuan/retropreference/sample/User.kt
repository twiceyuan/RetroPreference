package com.twiceyuan.retropreference.sample

import java.io.Serializable

/**
 * Created by twiceYuan on 09/02/2017.
 *
 *
 * 用户对象
 */
class User(val username: String, var password: String, var score: Float, var age: Int) : Serializable {

    override fun toString(): String {
        return String.format("" +
                "{\n" +
                "    username: %s,\n" +
                "    password: %s,\n" +
                "    score: %s,\n" +
                "    age: %s\n" +
                "}",
                username, password, score, age)
    }
}
