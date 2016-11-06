package com.goshopping.usx.goshopping.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.adapter.HomePageMovieNotReAdapter;
import com.goshopping.usx.goshopping.adapter.HomePageMovieReAdapter;
import com.goshopping.usx.goshopping.adapter.HomePageMovieTopHLAdapter;
import com.goshopping.usx.goshopping.bean.CinemaItem;
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
 * 已经热映的电影
 */


public class HomePageMovieReFragment extends Fragment {
    private View rootView;
    private boolean isCached = false;
    private XRecyclerView mRecyclerView;
    private LayoutInflater mInflater;
    private View header;
    private HomePageMovieReAdapter mAdapter;
    private List<CinemaItem> cinemas;
    private List<MovieItem> topHlMovies;


    private HListView mHListview;
    private View topFloatingView;

    public HomePageMovieReFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_homepage_movie_re, container, false);
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

        mAdapter = new HomePageMovieReAdapter(cinemas,(FilterView) topFloatingView);
        mRecyclerView.setAdapter(mAdapter);
        mHListview.setAdapter(new HomePageMovieTopHLAdapter(getContext(), topHlMovies));

       mRecyclerView.setPullRefreshEnabled(false);
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
                if (pos > 1) topFloatingView.setVisibility(View.VISIBLE);
                else topFloatingView.setVisibility(View.GONE);


            }
        });

//        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//
//
//                        mRecyclerView.refreshComplete();
//                    }
//
//                }, 1000);
//            }
//
//            @Override
//            public void onLoadMore() {
//
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//
//                        mRecyclerView.loadMoreComplete();
//
//                    }
//                }, 1000);
//
//            }
//        });


    }

    private void initView() {

        mRecyclerView = (XRecyclerView) getView().findViewById(R.id.movie_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.refresh_icon);
        mRecyclerView.addHeaderView(header);
        topFloatingView = getView().findViewById(R.id.rl_filter);
        topFloatingView.setVisibility(View.GONE);

    }

    private void initData() {
        cinemas = new ArrayList<>();

        topHlMovies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MovieItem t = new MovieItem("m " + i, "");
            topHlMovies.add(t);

        }
        for (int i = 0; i < 30; i++) {
            CinemaItem t = new CinemaItem();
            t.setName("cinema " + i);
            cinemas.add(t);
        }
    }
}
