package com.goshopping.usx.goshopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.bean.TopNavItem;
import com.goshopping.usx.goshopping.ui.HomePageViewMovieActivity;

import java.util.List;

/**
 * Created by Si on 2016/10/20.
 */
public class NavGridViewAdapter extends BaseAdapter {
    private List<TopNavItem> mDatas;
    private LayoutInflater inflater;
    private Context mContext;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    /**
     * 每一页显示的个数
     */
    private int pageSize;

    public NavGridViewAdapter(Context context, List<TopNavItem> mDatas, int curIndex, int pageSize) {
        mContext=context;
        inflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.curIndex = curIndex;
        this.pageSize = pageSize;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (curIndex+1)*pageSize,
     * 如果够，则直接返回每一页显示的最大条目个数pageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - curIndex * pageSize);(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return mDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mDatas.size() - curIndex * pageSize);

    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position + curIndex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.top_nav_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv = (TextView) convertView.findViewById(R.id.textView);

            viewHolder.iv = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 1 && curIndex == 0) {
                        mContext.startActivity(new Intent(mContext, HomePageViewMovieActivity.class
                        ));

                    }
                    Log.d("ss", "onClick: ");
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize，
         */
        int pos = position + curIndex * pageSize;
        viewHolder.tv.setText(mDatas.get(pos).getName());
        viewHolder.iv.setImageResource(mDatas.get(pos).getIconRes());
        return convertView;
    }


    class ViewHolder {
        public TextView tv;
        public ImageView iv;
    }
}
