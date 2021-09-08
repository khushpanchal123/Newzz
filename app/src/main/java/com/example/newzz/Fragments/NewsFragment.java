package com.example.newzz.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newzz.AppExecutor;
import com.example.newzz.MainViewModel;
import com.example.newzz.NewsListner;
import com.example.newzz.database.AppDatabase;
import com.example.newzz.database.News;
import com.example.newzz.adapter.NewsAdapter;
import com.example.newzz.Utils.QueryUtils;
import com.example.newzz.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NewsFragment extends Fragment implements NewsListner {

    private List<News> mNewsList;
    private List<News> mFavoriteNews;
    private NewsAdapter mNewsAdapter;
    private static final int LOADER_ID = 0;
    private String mjson;
    private AppDatabase mDb;

    private final OkHttpClient client = new OkHttpClient();

    private static final String JSON_RESPONSE = "https://newsapi.org/v2/top-headlines?country=in&apiKey=c3a342cc6d394cb19fc3db635741e77f";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.news_list, container, false);

        mDb = AppDatabase.getInstance(getActivity());
        mNewsList = new ArrayList<>();
        RecyclerView mRecyclerView = rootView.findViewById(R.id.recyclerview_news);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mNewsAdapter = new NewsAdapter();
        mNewsAdapter.setmNewsListner(this);
        mRecyclerView.setAdapter(mNewsAdapter);
        setupViewModel();
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
                            setFavStar();
                            mNewsAdapter.setNewsData(mNewsList);
                        }
                    });
                }
            }
        });
    }

    private void setFavStar() {
        int flag = 0;
        for(int i=0; i<mNewsList.size(); i++){
            for(int j=0; j<mFavoriteNews.size(); j++){
                if(mNewsList.get(i).getTitle().equals(mFavoriteNews.get(j).getTitle())) flag = 1;
            }
            if(flag==1) mNewsList.get(i).setFavoriteStatus(true);
            else mNewsList.get(i).setFavoriteStatus(false);
            flag=0;
        }
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTasks().observe(getActivity(), new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> newsEntries) {
                mFavoriteNews = newsEntries;
                setFavStar();
                mNewsAdapter.notifyDataSetChanged();
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

    @Override
    public void onFavoriteClick(int position) {

        if(mNewsList.get(position).getFavoriteStatus()){
            AppExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mNewsList.get(position).setFavoriteStatus(false);
                    mDb.newsDao().deleteNews(FavoriteFragment.getFavNewsToDelete(mNewsList.get(position)));
                    //mDb.newsDao().deleteNews(mNewsList.get(position));
                }
            });
            Toast.makeText(getActivity(), "Removed from Favorites", Toast.LENGTH_SHORT).show();
        }
        else {
            AppExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mNewsList.get(position).setFavoriteStatus(true);
                    mDb.newsDao().insertNews(mNewsList.get(position));
                }
            });
            Toast.makeText(getActivity(), "Successfully added as Favorites", Toast.LENGTH_SHORT).show();
        }
        mNewsAdapter.notifyDataSetChanged();
    }

}
