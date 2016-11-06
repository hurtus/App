package com.goshopping.usx.goshopping.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.bean.MerchantBean;

import java.util.List;

public class NearbyRecyclerAdapter extends RecyclerView.Adapter<NearbyRecyclerAdapter.ViewHolder> {

    private List<MerchantBean> datas = null;
    public NearbyRecyclerAdapter(List<MerchantBean> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_merchant_nearby, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("wrap", "onClick: ");
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.titleTv.setText(datas.get(position).getName());
        viewHolder.summaryTv.setText(datas.get(position).getIntroduce());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTv;
        TextView summaryTv;
        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image_item_merchant_nearby);
            titleTv = (TextView) view.findViewById(R.id.title_item_merchant_nearby);
            summaryTv = (TextView) view.findViewById(R.id.summary_item_merchant_nearby);
            linearLayout = (LinearLayout) view.findViewById(R.id.ll_item_merchant_nearby);
        }
    }
}
