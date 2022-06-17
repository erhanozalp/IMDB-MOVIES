package com.example.imdb.Activities.Activities.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.imdb.Activities.WatchListActivity;
import com.example.imdb.Entity.Result;
import com.example.imdb.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.ViewHolder>{
    private List<Result> movieResultList;

    private Result result;
    public WatchListAdapter(List<Result> movieResultList){
        this.movieResultList=movieResultList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.watchlist_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        result = movieResultList.get(position);

        holder.title_textview.setText(result.getTitle());
        holder.year_textview.setText(result.getDescription());
        Picasso.get().load(result.getImage())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_not_supported_24)
                .resize(250,250)
                .into(holder.image_view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigateWatchListActivity(v.getContext());
            }
        });

    }
    @Override
    public int getItemCount() {
        return movieResultList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title_textview;
        private ImageView image_view;
        private TextView year_textview;

        public ViewHolder(View v) {

            super(v);

            title_textview=v.findViewById(R.id.movie_name_textview);
            image_view=v.findViewById(R.id.image_view);
            year_textview=v.findViewById(R.id.year_textview);
        }
    }


}