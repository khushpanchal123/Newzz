package com.example.newzz;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>, NewsAdapter.NewsListner {

    private List<News> mNewsList;
    private NewsAdapter mNewsAdapter;
    private static final int LOADER_ID = 0;

    private static final String JSON_RESPONSE = "https://newsapi.org/v2/top-headlines?country=in&apiKey=c3a342cc6d394cb19fc3db635741e77f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportLoaderManager().initLoader(LOADER_ID, null, MainActivity.this);

        mNewsList = new ArrayList<>();
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview_news);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mNewsAdapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(mNewsAdapter);

    }

    @NonNull
    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<List<News>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<List<News>>(this) {

            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public List<News> loadInBackground() {
                try {
                    return QueryUtils.extractNews(JSON_RESPONSE);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {
        mNewsList = data;
        mNewsAdapter.setNewsData(mNewsList);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {

    }

    @Override
    public void onClickNews(int position) {
        String url = mNewsList.get(position).getUrl();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}
