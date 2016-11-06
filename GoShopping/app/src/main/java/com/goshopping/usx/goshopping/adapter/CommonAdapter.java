package com.goshopping.usx.goshopping.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by hwj12 on 2016-08-16.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    private static final String TAG = "CommonAdapter";
    private Context mContext;
    private List<T> mDatas;
    private int mLayoutId;

    public CommonAdapter(Context context, List<T> datas, int layoutId){
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = ViewHolder.get(mContext,convertView,parent,
                mLayoutId);
        convert(holder,getItem(position),position);

        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder,T t,int position);
}
