package com.goshopping.usx.goshopping.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goshopping.usx.goshopping.R;

/**
 * Created by hwj on 2016-10-17.
 */

public class itemMapBottomFragment extends Fragment{
    private View mConvertView;
    private TextView titleMessageTv;
    private TextView summaryMessageTv;

    public static itemMapBottomFragment newInstance(Bundle args){
        itemMapBottomFragment fragment = new itemMapBottomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mConvertView = inflater.inflate(R.layout.item_bottom_map,container,false);
        initView();
        initData();
        return mConvertView;
    }

    private void initView() {
        titleMessageTv = (TextView) mConvertView.findViewById(R.id.title_item_map_bottom);
        summaryMessageTv = (TextView) mConvertView.findViewById(R.id.summary_item_map_bottom);
    }

    private void initData() {
        Bundle args = getArguments();

        String title = args.getString("title");
        String summary = args.getString("summary");
        if( title != null){
            titleMessageTv.setText(title);
        }
        if(summary != null){
            summaryMessageTv.setText(summary);
        }
    }

}
