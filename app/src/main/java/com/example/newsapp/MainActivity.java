package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String email = "";
        UserSessionManager sessionManager = new UserSessionManager(getApplicationContext());

        if(sessionManager.isLoggedIn()) {
            email = sessionManager.getEmail();
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        String finalEmail = email;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(finalEmail.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, NewsFeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}