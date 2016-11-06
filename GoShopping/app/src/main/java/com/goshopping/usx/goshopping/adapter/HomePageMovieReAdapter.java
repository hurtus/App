package com.goshopping.usx.goshopping.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.bean.CinemaItem;
import com.goshopping.usx.goshopping.view.FilterView;

import java.util.List;

/**
 * Created by Si on 2016/11/5.
 */

public class HomePageMovieReAdapter extends RecyclerView.Adapter<HomePageMovieReAdapter.ViewHolder> {

    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;
    private RecyclerView recyclerView;
    private View targetView;
    private FilterView rlFilter;

    List<CinemaItem> cinemas;
    public HomePageMovieReAdapter(List<CinemaItem> lists, FilterView filter) {

        rlFilter=filter;
        cinemas=lists;
    }

    @Override
    public HomePageMovieReAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(recyclerView==null) recyclerView= (RecyclerView) parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_movie_re_cinema_item, parent, false);
        HomePageMovieReAdapter.ViewHolder vh = new HomePageMovieReAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(HomePageMovieReAdapter.ViewHolder holder, int position) {



        holder.cinemaName.setText(cinemas.get(position).getName());
//        holder.time.setText(movies.get(position).getTime());
//        holder.actors.setText("演员：Osa");
        if(position==0) {
            holder.itemView.setTag(FIRST_STICKY_VIEW);
            holder.floatingToper.setVisibility(View.VISIBLE);

        }else {
            holder.itemView.setTag(HAS_STICKY_VIEW);
            holder.floatingToper.setVisibility(View.GONE);
        }

//        holder.itemView.setContentDescription(movies.get(position).getTime());

        if(position==0)
        {
            targetView=holder.itemView;
            View.OnClickListener l=new InnerClickListener();
            holder.floatingToper.findViewById(R.id.ll_brand).setOnClickListener(l);
            holder.floatingToper.findViewById(R.id.ll_city).setOnClickListener(l);
            holder.floatingToper.findViewById(R.id.ll_rating).setOnClickListener(l);
        }


    }

    @Override
    public int getItemCount() {
        return cinemas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cinemaName;
        public View floatingToper;



        public ViewHolder(View view) {
            super(view);
            floatingToper=view.findViewById(R.id.floatingToper);
            cinemaName = (TextView) view.findViewById(R.id.cinema_name);

        }
    }
    class InnerClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            recyclerView.smoothScrollBy(0,targetView.getTop());
            switch (v.getId())
            {
                case R.id.ll_brand:
                    rlFilter.updateTabState(0);
                    break;
                case R.id.ll_city:
                    rlFilter.updateTabState(1);
                    break;
                default:
                    rlFilter.updateTabState(2);
                    break;
            }
        }
    }
}
