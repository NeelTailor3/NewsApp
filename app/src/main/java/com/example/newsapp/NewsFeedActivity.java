package com.example.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsItem> newsList;
    private ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        loader = findViewById(R.id.newsFeedLoader);
        topAppBar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_category) {
                    onCategoryMenuItemClicked();
                } else if (item.getItemId() == R.id.action_saved) {
                    Toast.makeText(NewsFeedActivity.this, "Saved clicked", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.action_logout) {
                    onLogoutButtonClicked();
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsList = new ArrayList<>();

        loadData("");
    }

    public void onReadMoreClick(View view) {
        String url = "https://www.example.com"; // Replace with your URL
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void loadData(String selectedCategories) {
        loader.setVisibility(View.VISIBLE);
        String API_ENDPOINT = "https://newsdata.io/api/1/news?" +
                "country=in&" +
                "language=en&" +
                "apikey=" + getString(R.string.newsdataio_apikey);

        if(!selectedCategories.equals("")) {
           API_ENDPOINT += "&category=" + selectedCategories;
        }

        new FetchNewsTask().execute(API_ENDPOINT);
    }

    private void onCategoryMenuItemClicked() {
        CategoryBottomSheetDialogFragment bottomSheet = new CategoryBottomSheetDialogFragment();
        bottomSheet.showNow(getSupportFragmentManager(), "CategoryBottomSheetDialogFragment");

        bottomSheet.setOnDismissListener(new CategoryBottomSheetDialogFragment.OnDismissListener() {
            @Override
            public void onDismiss(List<CategoryItemDataModel> selectedItems) {
                // Handle selected items
                String selected = "";
                for (CategoryItemDataModel item : selectedItems) {
                    selected += item.getName() + ",";
                }
                loadData(selected);
            }

        });
    }

    private void onLogoutButtonClicked() {
        UserSessionManager sessionManager = new UserSessionManager(getApplicationContext());
        sessionManager.logoutUser();

        Intent intent = new Intent(NewsFeedActivity.this, MainActivity.class);
        startActivity(intent);

        Toast.makeText(NewsFeedActivity.this, "Successfully Logged Out", Toast.LENGTH_SHORT).show();
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
                    newsList.clear();

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
                    loader.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(NewsFeedActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
