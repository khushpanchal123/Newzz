package com.example.newzz;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

//  LoaderManager.LoaderCallbacks<List<News>>
public class MainActivity extends AppCompatActivity implements NewsAdapter.NewsListner {

    private List<News> mNewsList;
    private NewsAdapter mNewsAdapter;
    private static final int LOADER_ID = 0;

    private static final String JSON_RESPONSE = "https://newsapi.org/v2/top-headlines?country=in&apiKey=c3a342cc6d394cb19fc3db635741e77f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportLoaderManager().initLoader(LOADER_ID, null, MainActivity.this);

        mNewsList = new ArrayList<>();
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview_news);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mNewsAdapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(mNewsAdapter);

        makeHttpRequest(JSON_RESPONSE);
    }

    private void makeHttpRequest(String url) {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mNewsList = QueryUtils.extractFeatureFromJSON(response);
                    mNewsAdapter.setNewsData(mNewsList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("MainActivity", "That didn't work!");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:221.0) Gecko/20100101 Firefox/31.0");
                return headerMap;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

//    @NonNull
//    @SuppressLint("StaticFieldLeak")
//    @Override
//    public Loader<List<News>> onCreateLoader(int id, final Bundle args) {
//        return new AsyncTaskLoader<List<News>>(this) {
//
//            @Override
//            protected void onStartLoading() {
//                forceLoad();
//            }
//
//            @Override
//            public List<News> loadInBackground() {
//                try {
//                    return QueryUtils.extractNews(JSON_RESPONSE);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return null;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//        };
//    }
//
//    @Override
//    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {
//        mNewsList = data;
//        mNewsAdapter.setNewsData(mNewsList);
//    }
//
//    @Override
//    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
//
//    }

    @Override
    public void onClickNews(int position) {
        String url = mNewsList.get(position).getUrl();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}
