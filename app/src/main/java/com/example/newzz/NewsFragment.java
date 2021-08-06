package com.example.newzz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NewsFragment extends Fragment implements NewsAdapter.NewsListner {

    private List<News> mNewsList;
    private NewsAdapter mNewsAdapter;
    private static final int LOADER_ID = 0;
    private String mjson;

    private final OkHttpClient client = new OkHttpClient();

    private static final String JSON_RESPONSE = "https://newsapi.org/v2/top-headlines?country=in&apiKey=c3a342cc6d394cb19fc3db635741e77f";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.news_list, container, false);

        mNewsList = new ArrayList<>();
        RecyclerView mRecyclerView = rootView.findViewById(R.id.recyclerview_news);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mNewsAdapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(mNewsAdapter);

        makeHttpRequest(JSON_RESPONSE);
        return rootView;
    }

    private void makeHttpRequest(String jsonResponse) {

        Request request = new Request.Builder()
                .url(jsonResponse)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("MainActivity", "That didn't work!");
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    mjson = responseBody.string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mNewsList = QueryUtils.extractFeatureFromJSON(mjson);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mNewsAdapter.setNewsData(mNewsList);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClickNews(int position) {
        String url = mNewsList.get(position).getUrl();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
    }

}
