package com.twiceyuan.retropreference.javasample;

import android.content.Context;
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
        Settings settings = RetroPreference.INSTANCE.create(this, Settings.class, Context.MODE_PRIVATE);

        // Get preference item holder
        Preference<Integer> launchCount = settings.launchCount();

        // getWithDefault the preference value
        Integer count = launchCount.getWithDefault(1);

        ((TextView) findViewById(R.id.tv_launch)).setText(String.format("启动次数：%s", count));

        // set the preference value
        launchCount.set(count + 1);

        // Object store
        Preference<User> userPreference = settings.currentUser();
        User currentUser = userPreference.getWithDefault(new User() {{
            username = "twiceYuan";
            password = "123456";
            age = 0;
            score = 1000f;
        }});

        currentUser.age++;
        currentUser.score -= 0.1f;
        ((TextView) findViewById(R.id.tv_user)).setText(String.format("当前用户：\n%s", currentUser.toString()));

        userPreference.set(currentUser);
    }
}
