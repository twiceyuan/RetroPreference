package com.twiceyuan.retropreference.sample;

import java.io.Serializable;

/**
 * Created by twiceYuan on 09/02/2017.
 * <p>
 * 用户对象
 */
class User implements Serializable {

    String username;
    String password;
    float  score;
    int    age;

    @Override
    public String toString() {
        return String.format("" +
                        "{\n" +
                        "    username: %s,\n" +
                        "    password: %s,\n" +
                        "    score: %s,\n" +
                        "    age: %s\n" +
                        "}",
                username, password, score, age);
    }
}
