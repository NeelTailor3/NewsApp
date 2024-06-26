package com.example.newsapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        dbHelper = new DatabaseHelper(this);
    }

    public void loginUser(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        } else {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String[] projection = {
                    DatabaseHelper.COLUMN_ID,
                    DatabaseHelper.COLUMN_EMAIL,
                    DatabaseHelper.COLUMN_PASSWORD
            };

            String selection = DatabaseHelper.COLUMN_EMAIL + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
            String[] selectionArgs = { email, password };

            Cursor cursor = db.query(
                    DatabaseHelper.TABLE_USERS,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                cursor.close();
                UserSessionManager sessionManager = new UserSessionManager(getApplicationContext());
                sessionManager.createLoginSession(etEmail.getText().toString());
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish(); // Close login activity after successful login
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void redirectToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}