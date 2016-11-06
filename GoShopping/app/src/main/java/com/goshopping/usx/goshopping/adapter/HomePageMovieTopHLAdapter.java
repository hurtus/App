package com.goshopping.usx.goshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.bean.MovieItem;

import java.util.List;

/**
 * Created by Si on 2016/11/5.
 */

public class HomePageMovieTopHLAdapter extends BaseAdapter {

    private List<MovieItem> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public HomePageMovieTopHLAdapter(Context context,List<MovieItem> list) {
        mContext=context;
        mList = list;
        mInflater=LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null) {
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.homepage_movie_hl_item,parent,false);
            holder.movieIcon= (ImageView) convertView.findViewById(R.id.hl_movie_ic);
            convertView.setTag(holder);

        }
        if(holder==null) holder= (ViewHolder) convertView.getTag();
        holder.movieIcon.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }

    class ViewHolder {
        public ImageView movieIcon;
    }
}
