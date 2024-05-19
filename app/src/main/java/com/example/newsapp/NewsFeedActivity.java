package com.example.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
