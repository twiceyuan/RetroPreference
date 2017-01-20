package com.twiceyuan.retropreference.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.twiceyuan.library.Preference;
import com.twiceyuan.library.RetroPreference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create preference proxy by RetroPreference.create();
        Settings settings = RetroPreference.create(this, Settings.class, MODE_PRIVATE);

        // Get preference item holder
        Preference<Integer> launchCount = settings.launchCount();

        // get the preference value
        Integer count = launchCount.get(/*defaultValue = */0);

        count++;
        Toast.makeText(this, "Launch count: " + count, Toast.LENGTH_SHORT).show();

        // set the preference value
        launchCount.set(count);
    }
}
