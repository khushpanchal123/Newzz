package com.example.newzz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.newzz.adapter.CategoryAdapter;
import com.example.newzz.adapter.NewsAdapter;
import com.example.newzz.database.News;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import okhttp3.OkHttpClient;

//  LoaderManager.LoaderCallbacks<List<News>>
public class MainActivity extends AppCompatActivity {

//    private List<News> mNewsList;
//    private NewsAdapter mNewsAdapter;
//    private static final int LOADER_ID = 0;
//    private String mjson;
//
//    private final OkHttpClient client = new OkHttpClient();
//
//    private static final String JSON_RESPONSE = "https://newsapi.org/v2/top-headlines?country=in&apiKey=c3a342cc6d394cb19fc3db635741e77f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager2 viewPager = (ViewPager2) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        CategoryAdapter adapter = new CategoryAdapter(this);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override public void onConfigureTab(TabLayout.Tab tab, int position) {
                if(position == 0)
                    tab.setText("News");
                else
                    tab.setText("Favorite");
            }
        }).attach();

        //getSupportLoaderManager().initLoader(LOADER_ID, null, MainActivity.this);

//        mNewsList = new ArrayList<>();
//        RecyclerView mRecyclerView = findViewById(R.id.recyclerview_news);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        mRecyclerView.setHasFixedSize(true);
//        mNewsAdapter = new NewsAdapter(this);
//        mRecyclerView.setAdapter(mNewsAdapter);
//
//        makeHttpRequest(JSON_RESPONSE);

    }

//    private void makeHttpRequest(String jsonResponse) {
//
//        Request request = new Request.Builder()
//                .url(jsonResponse)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                Log.i("MainActivity", "That didn't work!");
//            }
//
//            @Override public void onResponse(Call call, Response response) throws IOException {
//                try (ResponseBody responseBody = response.body()) {
//                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//                    mjson = responseBody.string();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                mNewsList = QueryUtils.extractFeatureFromJSON(mjson);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            mNewsAdapter.setNewsData(mNewsList);
//                        }
//                    });
//                }
//            }
//        });
//    }

    //    private void makeHttpRequest(String url) {
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    mNewsList = QueryUtils.extractFeatureFromJSON(response);
//                    mNewsAdapter.setNewsData(mNewsList);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i("MainActivity", "That didn't work!");
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headerMap = new HashMap<String, String>();
//                headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:221.0) Gecko/20100101 Firefox/31.0");
//                return headerMap;
//            }
//        };

        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
//    }

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
//
//    @Override
//    public void onClickNews(int position) {
//        String url = mNewsList.get(position).getUrl();
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//        CustomTabsIntent customTabsIntent = builder.build();
//        customTabsIntent.launchUrl(this, Uri.parse(url));
//    }
}
