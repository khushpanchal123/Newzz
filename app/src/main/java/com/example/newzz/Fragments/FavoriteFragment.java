package com.example.newzz.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newzz.AppExecutor;
import com.example.newzz.MainViewModel;
import com.example.newzz.R;
import com.example.newzz.adapter.NewsAdapter;
import com.example.newzz.database.AppDatabase;
import com.example.newzz.database.News;

import java.util.List;

public class FavoriteFragment extends Fragment implements NewsAdapter.NewsListner {

    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private AppDatabase mDb;
    private List<News> mFavoriteNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_list, container, false);

        mDb = AppDatabase.getInstance(getActivity());

        mRecyclerView = rootView.findViewById(R.id.recyclerview_news);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNewsAdapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(mNewsAdapter);
        setupViewModel();
        return rootView;
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTasks().observe(getActivity(), new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> newsEntries) {
                mFavoriteNews = newsEntries;
                mNewsAdapter.setNewsData(newsEntries);
            }
        });
    }

    @Override
    public void onClickNews(int position) {
        final News currNews = mFavoriteNews.get(position);
        String url = currNews.getUrl();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
    }

    @Override
    public void onFavoriteClick(int position) {

        final News currNews = mFavoriteNews.get(position);
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.newsDao().deleteNews(currNews);
                if(currNews.getFavoriteStatus()) Log.d("Khush Panchal", "true");
                else Log.d("Khush Panchal", "false");
            }
        });
    }
}
