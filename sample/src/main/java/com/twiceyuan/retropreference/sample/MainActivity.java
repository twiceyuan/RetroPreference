package com.twiceyuan.retropreference.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.twiceyuan.retropreference.Preference;
import com.twiceyuan.retropreference.RetroPreference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create preference proxy by RetroPreference.create();
        Settings settings = RetroPreference.getDefault();

        // Get preference item holder
        Preference<Integer> launchCount = settings.launchCount();

        // getWithDefault the preference value
        Integer count = launchCount.getWithDefault(/*defaultValue = */0);

        count++;
        ((TextView) findViewById(R.id.tv_launch)).setText(String.format("启动次数：%s", count));

        // set the preference value
        launchCount.set(count);

        // Object store
        Preference<User> userPreference = settings.currentUser();
        User currentUser = userPreference.get();
        if (currentUser != null) {
            currentUser.age++;
            currentUser.score -= 0.1;
            ((TextView) findViewById(R.id.tv_user)).setText(String.format("当前用户：\n%s", currentUser.toString()));
        } else {
            currentUser = new User();
            currentUser.username = "twiceYuan";
            currentUser.password = "123456";
            currentUser.age = 0;
            currentUser.score = 1000;
        }
        userPreference.set(currentUser);
    }
}
