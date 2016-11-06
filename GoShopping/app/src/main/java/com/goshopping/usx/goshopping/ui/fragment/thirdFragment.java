package com.goshopping.usx.goshopping.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.ui.LoginActivity;
import com.goshopping.usx.goshopping.view.CircleImageView;

/**
 * Created by hwj on 2016-10-15.
 */

public class thirdFragment extends Fragment {


    private View mConvertView;
    private CircleImageView circleImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mConvertView == null){
            mConvertView = inflater.inflate(R.layout.third_fragment_content,container,false);

            initView();
            initEven();
        }
        ViewGroup parent = (ViewGroup) mConvertView.getParent();
        if (parent != null){
            parent.removeView(mConvertView);
        }
        return mConvertView;

    }

    private void initEven() {
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
            }
        });
    }

    private void initView() {
        circleImageView = (CircleImageView) mConvertView.findViewById(R.id.account_message_circleImageView);
    }
}
