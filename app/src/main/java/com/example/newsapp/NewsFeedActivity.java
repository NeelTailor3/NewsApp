package com.example.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsFeedActivity extends AppCompatActivity {
    private TextView tvNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed);

        tvNews = findViewById(R.id.tvNews);

        String API_ENDPOINT = "https://newsdata.io/api/1/news?" +
                "country=in&" +
                "language=en&" +
                "category=education,environment,science,technology,world&" +
                "apikey=" + getString(R.string.newsdataio_apikey);

        new FetchNewsTask().execute(API_ENDPOINT);
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
                    StringBuilder newsBuilder = new StringBuilder();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject newsObject = jsonArray.getJSONObject(i);
                        String title = newsObject.getString("title");
                        newsBuilder.append("Title: ").append(title).append("\n");
                    }
                    tvNews.setText(newsBuilder.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
