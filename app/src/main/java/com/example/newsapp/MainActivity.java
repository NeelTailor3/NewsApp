package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String username = "";
        UserSessionManager sessionManager = new UserSessionManager(getApplicationContext());

        if(sessionManager.isLoggedIn()) {
            username = sessionManager.getUsername();
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        String finalUsername = username;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(finalUsername.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, NewsFeedActivity.class);
                    startActivity(intent);
                }
            }
        }, 3000);
    }
}