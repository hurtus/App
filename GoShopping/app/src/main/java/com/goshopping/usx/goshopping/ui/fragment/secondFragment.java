package com.goshopping.usx.goshopping.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.adapter.NearbyRecyclerAdapter;
import com.goshopping.usx.goshopping.bean.MerchantBean;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hwj on 2016-10-15.
 */

public class secondFragment extends Fragment{

    private View mConvertView;
    private XRecyclerView mRecyclerView;
    private List<MerchantBean> merchantList = new ArrayList<MerchantBean>();
    private NearbyRecyclerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mConvertView == null) {
            mConvertView = inflater.inflate(R.layout.second_fragment_content, container, false);

            initView();
            initData();
            initEvent();
        }
        ViewGroup parent = (ViewGroup) mConvertView.getParent();
        if (parent != null) {
            parent.removeView(mConvertView);
        }

        return mConvertView;

    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            String title = "第" + i + "个";
            String summary = "这是一个商家";
            MerchantBean merchant = new MerchantBean();
            merchant.setName(title);
            merchant.setIntroduce(summary);
            merchantList.add(merchant);
        }
        mAdapter = new NearbyRecyclerAdapter(merchantList);
        mRecyclerView.setAdapter(mAdapter);

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.item_anim);
        LayoutAnimationController layoutAnimation = new LayoutAnimationController(animation);
        layoutAnimation.setDelay(0.2f);
        layoutAnimation.setOrder(LayoutAnimationController.ORDER_NORMAL);

        mRecyclerView.setLayoutAnimation(layoutAnimation);


    }

    private void initEvent() {
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {


//                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                    }

                }, 1000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        mRecyclerView.loadMoreComplete();
//                        mAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
    }

    private void initView() {
        mRecyclerView = (XRecyclerView) mConvertView.findViewById(R.id.recyclerView_second);

        LinearLayoutManager layoutManage = new LinearLayoutManager(getContext());
        layoutManage.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManage);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.refresh_icon);

    }

}
