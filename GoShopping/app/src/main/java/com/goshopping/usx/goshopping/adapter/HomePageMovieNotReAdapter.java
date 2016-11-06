package com.goshopping.usx.goshopping.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.bean.MovieItem;

import java.util.List;

/**
 * Created by Si on 2016/10/22.
 */

public class HomePageMovieNotReAdapter extends RecyclerView.Adapter<HomePageMovieNotReAdapter.ViewHolder> {


    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;

    List<MovieItem> movies;
    public HomePageMovieNotReAdapter(List<MovieItem> lists) {

        movies=lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_movie_item, parent, false);
        HomePageMovieNotReAdapter.ViewHolder vh = new HomePageMovieNotReAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.movieName.setText(movies.get(position).getMovieName());
        holder.time.setText(movies.get(position).getTime());
        holder.actors.setText("演员：Osa");
        if(position==0) {
            holder.itemView.setTag(FIRST_STICKY_VIEW);
            holder.time.setVisibility(View.VISIBLE);

        }else if(position>=1&&!movies.get(position-1).getTime().equals(movies.get(position).getTime())){
            holder.itemView.setTag(FIRST_STICKY_VIEW);
            holder.time.setVisibility(View.VISIBLE);
            Log.d("lll", "onBindViewHolder:111 "+position+"  "+movies.get(position-1).getTime());
            Log.d("lll", "onBindViewHolder:222 "+position+"  "+movies.get(position).getTime());
        }
        else {
            holder.itemView.setTag(HAS_STICKY_VIEW);
            holder.time.setVisibility(View.GONE);
        }

        holder.itemView.setContentDescription(movies.get(position).getTime());


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView movieName;
        public TextView actors;
        public TextView desc;
        public TextView time;


        public ViewHolder(View view) {
            super(view);

            movieName = (TextView) view.findViewById(R.id.movie_name);
            actors= (TextView) view.findViewById(R.id.actors);
            desc= (TextView) view.findViewById(R.id.movie_desc);
            time= (TextView) view.findViewById(R.id.floating_toper);
        }
    }
}
