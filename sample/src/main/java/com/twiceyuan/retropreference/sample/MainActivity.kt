package com.twiceyuan.retropreference.sample

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.twiceyuan.retropreference.RetroPreference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create preference proxy by RetroPreference.createKt();
        val settings = RetroPreference.createKt(this, Settings::class.java, Context.MODE_PRIVATE)

        // Get preference item holder
        val launchCount = settings.launchCount()

        // getWithDefault the preference value
        val count: Int? = launchCount.getWithDefault(0)

        findViewById<TextView>(R.id.tv_launch).text = String.format("启动次数：%s", count)

        if (count != null) {
            // set the preference value
            launchCount.set(count.plus(1))
        } else {
            launchCount.set(1)
        }

        // Object store
        val userPreference = settings.currentUser()
        var currentUser: User? = userPreference.get()
        if (currentUser != null) {
            currentUser.age++
            currentUser.score -= 0.1f
            val textDesc = "当前用户：\n$currentUser"
            findViewById<TextView>(R.id.tv_user).text = textDesc
        } else {
            currentUser = User(username = "twiceYuan", password = "123456", age = 0, score = 1000f)
        }
        userPreference.set(currentUser)

        btn_clear_all.setOnClickListener { settings.clear() }
    }
}
