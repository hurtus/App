package com.goshopping.usx.goshopping.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hwj12 on 2016-08-15.
 */
public class ViewHolder {
    private SparseArray<View> mView;
    private View mConvertView;


    public ViewHolder(Context context, ViewGroup parent, int layoutId){
        this.mView = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        mConvertView.setTag(this);
    }

    public static ViewHolder get(Context context,View convertView,
                                 ViewGroup parent,int layoutId){
        if (convertView == null){
            return new ViewHolder(context,parent,layoutId);
        }else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            return holder;
        }

    }

    public <T extends View> T getView(int viewId){
        View view = mView.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            mView.put(viewId,view);
        }

        return (T) view;
    }
    public View getConvertView() {

        return mConvertView;
    }

    public ViewHolder setText(int viewId,String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId){
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

//    public ViewHolder setImageUrl(int viewId, String Uri){
//        ImageView iv = getView(viewId);
//        iv.setImageURI(Uri);
//        return this;
//    }
}
