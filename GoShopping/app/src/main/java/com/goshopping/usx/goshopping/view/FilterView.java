package com.goshopping.usx.goshopping.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.goshopping.usx.goshopping.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Si on 2016/11/5.
 */

public class FilterView extends LinearLayout implements View.OnClickListener {


    //filer的文字和图标
    @Bind(R.id.tv_brand)
    TextView tvBrand;
    @Bind(R.id.iv_brand_arrow)
    ImageView ivBrandArrow;
    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.iv_city_arrow)
    ImageView ivCityArrow;
    @Bind(R.id.tv_rating)
    TextView tvRating;
    @Bind(R.id.iv_rating_arrow)
    ImageView ivRatingArrow;

    //包含了两个listview的linearlayout
    @Bind(R.id.ll_content_list_view)
    LinearLayout llContentListView;
    //半透明的背景
    @Bind(R.id.view_mask_bg)
    View viewMaskBg;

    //左边的listview
    @Bind(R.id.lv_left)
    ListView lvLeft;
    //右边的listview
    @Bind(R.id.lv_right)
    ListView lvRight;

    //品牌
    @Bind(R.id.ll_brand)
    LinearLayout llBrand;
    //全城
    @Bind(R.id.ll_city)
    LinearLayout llCity;
    //好评
    @Bind(R.id.ll_rating)
    LinearLayout llRating;

    private Context mContext;
    private boolean isShowing = false;
    private int filterPosition = -1;
    private int panelHeight;
    private List<String> mBrandList;
    private List<String> mCityList;
    private List<String> mRatingList;
    private List<String> mCityDetailList;

    public FilterView(Context context) {
        super(context);
        Init(context);
    }

    public FilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(context);
    }


    private void Init(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_filter_layout, this);
        ButterKnife.bind(this, view);

        initData();
        initView();
        initListener();
    }

    private void initListener() {
        llBrand.setOnClickListener(this);
        llCity.setOnClickListener(this);
        llRating.setOnClickListener(this);
        viewMaskBg.setOnClickListener(this);

    }

    private void initView() {
        viewMaskBg.setVisibility(GONE);
        llContentListView.setVisibility(GONE);
    }

    private void initData() {
        mBrandList = new ArrayList<>();
        mBrandList.add("星美国际银城");
        mBrandList.add("大地影院");
        mBrandList.add("万达影院");
        mBrandList.add("咸亨国际影城");

        mCityList = new ArrayList<>();
        mCityList.add("诸暨市");
        mCityList.add("越城区");
        mCityList.add("柯桥区");
        mCityList.add("上虞区");

        mRatingList = new ArrayList<>();
        mRatingList.add("离我最近");
        mRatingList.add("好评优先");
        mRatingList.add("价格最低");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_brand:
                updateTabState(0);
                break;
            case R.id.ll_city:
                updateTabState(1);
                break;
            case R.id.ll_rating:
                updateTabState(2);

                break;
            case R.id.view_mask_bg:
                hide();
                break;
        }


    }


    //当发生点击事件的时候 ，判断点击的情况，更新tabs
    public void updateTabState(int clickTabPos) {
        hide();
        if (filterPosition == clickTabPos) {
            hide();
        } else {
            filterPosition = clickTabPos;
            if (clickTabPos == 0) {
                tvBrand.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
                ivBrandArrow.setImageResource(R.mipmap.home_up_arrow);
            } else if (clickTabPos == 1) {
                tvCity.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
                ivCityArrow.setImageResource(R.mipmap.home_up_arrow);
            } else {
                tvRating.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
                ivRatingArrow.setImageResource(R.mipmap.home_up_arrow);
            }
            show();
        }
    }

    public void hide() {
        isShowing = false;
        resetFilterStatus();
        viewMaskBg.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(llContentListView, "translationY", 0, -panelHeight).setDuration(200).start();
    }

    private void show() {
        isShowing = true;
        viewMaskBg.setVisibility(VISIBLE);
        llContentListView.setVisibility(VISIBLE);
        llContentListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llContentListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                panelHeight = llContentListView.getHeight();
                ObjectAnimator.ofFloat(llContentListView, "translationY", -panelHeight, 0).setDuration(200).start();
            }
        });

        //my
        lvRight.setVisibility(View.VISIBLE);

        lvRight.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mBrandList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final ViewHolder holder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.filter_right_item, null);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.tv.setText(mBrandList.get(position));
                return convertView;
            }


        });
    }

    // 复位筛选的显示状态
    public void resetFilterStatus() {
        filterPosition = -1;
        tvBrand.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivBrandArrow.setImageResource(R.mipmap.home_down_arrow);

        tvCity.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivCityArrow.setImageResource(R.mipmap.home_down_arrow);

        tvRating.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivRatingArrow.setImageResource(R.mipmap.home_down_arrow);
    }

    class ViewHolder {
        public TextView tv;

        ViewHolder(View v){
            tv= (TextView) v.findViewById(R.id.tv);
        }
    }


}
