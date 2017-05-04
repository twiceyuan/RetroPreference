package com.twiceyuan.retropreference.sample

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.twiceyuan.retropreference.RetroPreference

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create preference proxy by RetroPreference.create();
        val settings = RetroPreference.create(this, Settings::class.java, Context.MODE_PRIVATE)

        // Get preference item holder
        val launchCount = settings.launchCount()

        // getWithDefault the preference value
        val count: Int? = launchCount.getWithDefault(0)

        (findViewById(R.id.tv_launch) as TextView).text = String.format("启动次数：%s", count)

        // set the preference value
        launchCount.set(if (count != null) count + 1 else 1)

        // Object store
        val userPreference = settings.currentUser()
        var currentUser: User? = userPreference.get()
        if (currentUser != null) {
            currentUser.age++
            currentUser.score -= 0.1f
            (findViewById(R.id.tv_user) as TextView).text = String.format("当前用户：\n%s", currentUser.toString())
        } else {
            currentUser = User(username = "twiceYuan", password = "123456", age = 0, score = 1000f)
        }
        userPreference.set(currentUser)
    }
}
