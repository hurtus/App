package com.goshopping.usx.goshopping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by hwj on 2016-10-22.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter implements View.OnTouchListener{

    private List<T> mList;
    private RecyclerView.ViewHolder mCurrentViewHolder;
    private Context mContext;
    private boolean isFirst = false;
    private boolean isLast = false;
    private int lastY;

    public BaseRecyclerAdapter(List<T> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public boolean isFirst(){
        return isFirst;
    }

    public boolean isLast(){
        return isLast;
    }

    public abstract void bottomEnterAnim(RecyclerView.ViewHolder viewHolder);
    public abstract void topEnterAnim(RecyclerView.ViewHolder viewHolder);

    public abstract int getRootLayoutId();
    public abstract void onBind(BaseViewHolder viewHolder,int position);


    public class BaseViewHolder extends RecyclerView.ViewHolder{

        private View rootView;
        private SparseArray<View> views = new SparseArray<View>();

        public BaseViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
        }

        public <T extends View> T getViewById(int id){
            View v = views.get(id);
            if(v == null){
                v = rootView.findViewById(id);
                views.put(id,v);
            }
            return (T) v;
        }
    }

    public Context getContext(){
        return mContext;
    }

    public T getListItem(int position){
        if(position >= mList.size()){
            return null;
        }
        return mList.get(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder innerViewHolder =
                new BaseViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(getRootLayoutId(),parent,false));

//        parent.setOnTouchListener(this);

        return innerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mCurrentViewHolder = holder;
        if(position == mList.size() - 1){
            isLast = true;
        }else{
            isLast = false;
        }

        if(position == 0){
            isFirst = true;
        }else{
            isFirst = false;
        }
        onBind((BaseViewHolder) holder,position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - lastY;
                if (deltaY < 0){
                    if(!isLast()){
                        bottomEnterAnim(mCurrentViewHolder);
                    }
                }else{
                    if (!isFirst()){
                        topEnterAnim(mCurrentViewHolder);
                    }
                }
                break;
        }
        lastY = y;
        return false;
    }
}
