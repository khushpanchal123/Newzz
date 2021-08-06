package com.example.newzz.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.newzz.Fragments.FavoriteFragment;
import com.example.newzz.Fragments.NewsFragment;

public class CategoryAdapter extends FragmentStateAdapter{

    /** Context of the app */
    private Context mContext;

    public CategoryAdapter(FragmentActivity fm) {
        super(fm);
        mContext = fm;
    }


//    @Override
//    public CharSequence getPageTitle(int position) {
//        if (position == 0) {
//            return mContext.getString(R.string.category_news);
//        } else
//            return mContext.getString(R.string.category_favorite);
//    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new NewsFragment();
        } else
            return new FavoriteFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
