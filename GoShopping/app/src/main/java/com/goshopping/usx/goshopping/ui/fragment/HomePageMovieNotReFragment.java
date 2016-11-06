package com.goshopping.usx.goshopping.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.adapter.HomePageMovieNotReAdapter;
import com.goshopping.usx.goshopping.adapter.HomePageMovieTopHLAdapter;
import com.goshopping.usx.goshopping.adapter.HomePageRecyclerAdapter;
import com.goshopping.usx.goshopping.adapter.NavViewPagerAdapter;
import com.goshopping.usx.goshopping.bean.MovieItem;
import com.goshopping.usx.goshopping.view.FilterView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.widget.HListView;

/**
 * Created by Si on 2016/10/22.
 */


/**
 * 用于准备上映的电影界面
 */

public class HomePageMovieNotReFragment extends Fragment {


    private View rootView;
    private boolean isCached = false;
    private XRecyclerView mRecyclerView;
    private LayoutInflater mInflater;
    private View header;
    private HomePageMovieNotReAdapter mAdapter;
    private List<MovieItem> movies;
    private List<MovieItem> topHlMovies;


    private HListView mHListview;
    private TextView topFloatingView;

    public HomePageMovieNotReFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_homepage_movie_notre, container, false);
            header = inflater.inflate(R.layout.homepage_movie_header, container, false);
            mHListview = (HListView) header.findViewById(R.id.movie_hl);


        } else isCached = true;
        mInflater = LayoutInflater.from(getContext());
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (isCached) return;
        initView();
        initEvent();
        initData();

        mAdapter = new HomePageMovieNotReAdapter(movies);
        mRecyclerView.setAdapter(mAdapter);
        mHListview.setAdapter(new HomePageMovieTopHLAdapter(getContext(),topHlMovies));

        mRecyclerView.setRefreshing(true);
    }

    private void initEvent() {

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                View stickyInfoView = recyclerView.findChildViewUnder(topFloatingView.getMeasuredWidth() / 2, 3);
                int pos = -99999;
                if (stickyInfoView != null) {
                    pos = recyclerView.getChildAdapterPosition(stickyInfoView);

                    Log.d("isin", "onScrolled: " + pos);
                }
                //if (pos == 1) topFloatingView.setVisibility(View.GONE);

                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {

                    if ((int) stickyInfoView.getTag() == HomePageMovieNotReAdapter.FIRST_STICKY_VIEW) {
                        if (topFloatingView.getVisibility() == View.GONE) {
                            topFloatingView.setVisibility(View.VISIBLE);
                        }
                        topFloatingView.setText(String.valueOf(stickyInfoView.getContentDescription()));
                    } else if ((int) stickyInfoView.getTag() == HomePageMovieNotReAdapter.NONE_STICKY_VIEW) {
                        topFloatingView.setVisibility(View.GONE);
                    } else {

                        if (String.valueOf(stickyInfoView.getContentDescription()) != topFloatingView.getText())
                            topFloatingView.setText(String.valueOf(stickyInfoView.getContentDescription()));
                    }
                } else {
                    Log.d("sss", "onScrolled: ");
                    topFloatingView.setVisibility(View.GONE);
                }


                View transInfoView = recyclerView.findChildViewUnder(topFloatingView.getMeasuredWidth() / 2, topFloatingView.getMeasuredHeight() + 1);
                if (transInfoView != null && transInfoView.getTag() != null) {
                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - topFloatingView.getMeasuredHeight();

                    if (transViewStatus == HomePageMovieNotReAdapter.FIRST_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            topFloatingView.setTranslationY(dealtY);
                        } else {
                            topFloatingView.setTranslationY(0);
                        }

                    } else {
                        topFloatingView.setTranslationY(0);
                    }
                }
            }
        });


    }

    private void initView() {

        mRecyclerView = (XRecyclerView) getView().findViewById(R.id.movie_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.refresh_icon);
        mRecyclerView.addHeaderView(header);

        topFloatingView = (TextView) getView().findViewById(R.id.floating_toper);
        topFloatingView.setVisibility(View.GONE);
    }

    private void initData() {
        movies = new ArrayList<>();

        topHlMovies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MovieItem t=new MovieItem("m "+i,"");
            topHlMovies.add(t);

        }
        for (int i = 0; i < 30; i++) {
            List<String> list = new ArrayList<>();
            list.add("actor");
            MovieItem t = new MovieItem("movie_" + i, "周" + i / 4, "好看", "", list);
            movies.add(t);
        }
    }
}
