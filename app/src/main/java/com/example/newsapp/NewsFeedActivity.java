package com.example.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsItem> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.action_category) {
                    Toast.makeText(NewsFeedActivity.this, "Category clicked", Toast.LENGTH_SHORT).show();
                } else if(item.getItemId() == R.id.action_saved) {
                    Toast.makeText(NewsFeedActivity.this, "Saved clicked", Toast.LENGTH_SHORT).show();
                } else if(item.getItemId() == R.id.action_logout) {
                    UserSessionManager sessionManager = new UserSessionManager(getApplicationContext());
                    sessionManager.logoutUser();

                    Intent intent = new Intent(NewsFeedActivity.this, MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(NewsFeedActivity.this, "Successfully Logged Out", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsList = new ArrayList<>();

        String API_ENDPOINT = "https://newsdata.io/api/1/news?" +
                "country=in&" +
                "language=en&" +
                "category=education,environment,science,technology,world&" +
                "apikey=" + getString(R.string.newsdataio_apikey);

        new FetchNewsTask().execute(API_ENDPOINT);
    }

    public void onReadMoreClick(View view) {
        String url = "https://www.example.com"; // Replace with your URL
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private class FetchNewsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return NetworkUtils.getJSONFromAPI(params[0]);
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            if (jsonResponse != null) {
                try {

                    JSONObject apiResponse = new JSONObject(jsonResponse);
                    JSONArray jsonArray = apiResponse.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject newsObject = jsonArray.getJSONObject(i);

                        String title = newsObject.getString("title");
                        String description = newsObject.getString("description");
                        String link = newsObject.getString("link");
                        String image_url = newsObject.getString("image_url");

                        newsList.add(new NewsItem(title, description, link, image_url));
                    }

                    newsAdapter = new NewsAdapter(NewsFeedActivity.this, newsList);
                    recyclerView.setAdapter(newsAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(NewsFeedActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
