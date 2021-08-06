package com.example.newzz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.viewHolder> {

    private List<News>  mNewsList;
    private final NewsListner mNewsListner;

    public NewsAdapter(NewsListner newsListner) {
        super();
        mNewsListner = newsListner;
    }

    public void setNewsData(List<News> data) {
        mNewsList = data;
        notifyDataSetChanged();
    }

    public interface NewsListner{
        void onClickNews(int position);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.viewHolder holder, int position) {

        News currNews = mNewsList.get(position);
        holder.titleTV.setText(currNews.getTitle());
        Glide.with(holder.itemView.getContext()).load(currNews.getImage()).into(holder.imageIV);
        holder.favoriteIB.setChecked(currNews.getFavoriteStatus());
        holder.favoriteIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //currNews.setFavoriteStatus(!currNews.getFavoriteStatus());
                if(currNews.getFavoriteStatus()){
                    Toast.makeText(holder.itemView.getContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();
                    currNews.setFavoriteStatus(false);
                }
                else {
                    Toast.makeText(holder.itemView.getContext(), "Successfully added as Favorites", Toast.LENGTH_SHORT).show();
                    currNews.setFavoriteStatus(true);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mNewsList==null) return 0;
        return mNewsList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView titleTV;
        public final ImageView imageIV;
        public final CheckBox favoriteIB;

        public viewHolder(View itemView) {
            super(itemView);
            titleTV =  itemView.findViewById(R.id.title_ID);
            imageIV =  itemView.findViewById(R.id.image_ID);
            favoriteIB = itemView.findViewById(R.id.favorite_ID);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mNewsListner.onClickNews(getAdapterPosition());
        }
    }
}
