package com.twiceyuan.retropreference.javasample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.twiceyuan.retropreference.Preference;
import com.twiceyuan.retropreference.RetroPreference;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Create preference proxy by RetroPreference.createKt();
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = RetroPreference.create(this, Settings.class, Context.MODE_PRIVATE);

        // Get preference item holder
        Preference<Integer> launchCount = settings.launchCount();

        // getWithDefault the preference value
        Integer count = launchCount.getWithDefault(1);

        ((TextView) findViewById(R.id.tv_launch)).setText(String.format("启动次数：%s", count));

        // set the preference value
        launchCount.set(count + 1);

        // Object store
        Preference<User> userPreference = settings.currentUser();

        User currentUser = userPreference.getWithDefault(new User("twiceYuan", "123456", 0f, 1000));

        currentUser.age++;
        currentUser.score -= 0.1f;

        ((TextView) findViewById(R.id.tv_user)).setText(String.format("当前用户：\n%s", currentUser.toString()));

        userPreference.set(currentUser);

        HashMap<String, Integer> map = new HashMap<>();
        map.put("Test", 123);
        settings.testMap().set(map);

        HashMap<String, Integer> readMap = settings.testMap().get();
        if (readMap != null) {
            Log.i(TAG, String.valueOf(readMap.get("Test")));
        }
    }

    public void clear(View view) {
        settings.clear();
    }

    public void clearWithoutCount(View view) {
        Integer count = settings.launchCount().get();
        settings.clear();
        settings.launchCount().set(count);
    }
}
