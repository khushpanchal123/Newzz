package com.example.newzz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newzz.MainActivity;
import com.example.newzz.NewsListner;
import com.example.newzz.database.News;
import com.example.newzz.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.viewHolder> {

    private List<News> mNewsListAdapter;
    private NewsListner mNewsListner;

    public NewsAdapter() {
        super();
    }

    public void setmNewsListner(NewsListner newsListner) {
        this.mNewsListner = newsListner;
    }

    public void setNewsData(List<News> data) {
        mNewsListAdapter = data; // Reference to original list
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.viewHolder holder, int position) {

        News currNews = mNewsListAdapter.get(position);
        holder.titleTV.setText(currNews.getTitle());
        Glide.with(holder.itemView.getContext()).load(currNews.getImage()).into(holder.imageIV);
        holder.favoriteIB.setChecked(currNews.getFavoriteStatus());
        holder.favoriteIB.setOnClickListener(v -> mNewsListner.onFavoriteClick(position));
    }

    @Override
    public int getItemCount() {
        if (mNewsListAdapter == null) return 0;
        return mNewsListAdapter.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView titleTV;
        public final ImageView imageIV;
        public final CheckBox favoriteIB;

        public viewHolder(View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.title_ID);
            imageIV = itemView.findViewById(R.id.image_ID);
            favoriteIB = itemView.findViewById(R.id.favorite_ID);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mNewsListner.onClickNews(getBindingAdapterPosition());
        }
    }
}
