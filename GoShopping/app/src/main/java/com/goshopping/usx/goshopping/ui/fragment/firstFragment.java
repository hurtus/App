package com.goshopping.usx.goshopping.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.adapter.HomePageRecyclerAdapter;
import com.goshopping.usx.goshopping.adapter.NavGridViewAdapter;
import com.goshopping.usx.goshopping.adapter.NavViewPagerAdapter;
import com.goshopping.usx.goshopping.bean.TopNavItem;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hwj on 2016-10-15.
 */

public class firstFragment extends Fragment {


    private View rootView;
    private boolean isCached=false;
    private XRecyclerView mRecyclerView;
    private HomePageRecyclerAdapter mAdapter;
    private ArrayList<String> listData;
    private View header;
    private LinearLayout mDots;
    private ViewPager mNavContent;

    private LayoutInflater mInflater;
    //轮播导航实体类集合
    private List<TopNavItem> navItems;
    //轮播页面集合
    private List<View> navPages;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.first_fragment_content, container, false);
            header = inflater.inflate(R.layout.homepage_recyclerview_header, container, false);

        }
        else isCached=true;
        mInflater=LayoutInflater.from(getContext());
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if(isCached) return ;
        initView();
        initEvent();
        initData();
        mDots.getChildAt(0).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.ic_nav_dot_selected);
        mAdapter = new HomePageRecyclerAdapter(listData);
        mRecyclerView.setAdapter(mAdapter);
        if(mNavContent!=null) mNavContent.setAdapter(new NavViewPagerAdapter(navPages));
        mRecyclerView.setRefreshing(true);

    }

    private void initData() {

        int [] itemImg={R.drawable.nav_food,R.drawable.nav_movie,R.drawable.nav_hotel,R.drawable.nav_relax,R.drawable.nav_takeaway,R.drawable.nav_ktv,R.drawable.nav_beauty,R.drawable.nav_jingdian,R.drawable.nav_discount,R.drawable.nav_car,R.drawable.nav_car,R.drawable.nav_car,R.drawable.nav_car,R.drawable.nav_car,R.drawable.nav_car,R.drawable.nav_car};
        int [] itemStr={R.string.nav_food,R.string.nav_movie,R.string.nav_hotel,R.string.nav_relax        ,R.string.nav_takeaway,R.string.nav_ktv    ,R.string.nav_beauty,R.string.nav_jingdian,R.string.nav_discount     ,R.string.nav_car,R.string.nav_car     ,R.string.nav_car    ,R.string.nav_car,R.string.nav_car  ,R.string.nav_car, R.string.nav_car };

        listData = new ArrayList<>();
        for (int i = 0; i < itemImg.length; i++) {
            listData.add("item" + i);
        }

        navItems=new ArrayList<>();
        navPages=new ArrayList<>();
        for (int i = 0;i<itemImg.length;i++){
            navItems.add(new TopNavItem(itemStr[i],itemImg[i]));

        }
        for (int i = 0; i < 2; i++) {

            GridView gridView = (GridView) mInflater.inflate(R.layout.homepage_top_nav_content, mNavContent, false);
            gridView.setAdapter(new NavGridViewAdapter(getContext(), navItems, i, 10));
            navPages.add(gridView);

        }
        for (int i = 0; i < 2; i++) {
            mDots.addView(mInflater.inflate(R.layout.homepage_top_nav_dots, null));
        }
    }

    private void initEvent() {

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        listData.clear();
                        for (int i = 0; i < 15; i++) {
                            listData.add("item" + i );
                        }
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                    }

                }, 1000);
            }

            @Override
            public void onLoadMore() {

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        for (int i = 0; i < 15; i++) {
                            listData.add("item" + (1 + listData.size()));
                        }
                        mRecyclerView.loadMoreComplete();
                        mAdapter.notifyDataSetChanged();
                    }
                }, 1000);

            }
        });

        mNavContent.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

            @Override
            public void onPageSelected(int position) {
                mDots.getChildAt(1-position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.ic_nav_dot_normal);
                // 圆点选中
                mDots.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.ic_nav_dot_selected);

            }
        });

    }

    private void initView() {
        mRecyclerView = (XRecyclerView) getView().findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.refresh_icon);
        mRecyclerView.addHeaderView(header);
        mNavContent= (ViewPager) header.findViewById(R.id.top_nav_content);
        mDots= (LinearLayout) header.findViewById(R.id.top_nav_dot);

    }


}
