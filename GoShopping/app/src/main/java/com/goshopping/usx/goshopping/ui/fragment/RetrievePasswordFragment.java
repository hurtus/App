package com.goshopping.usx.goshopping.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.goshopping.usx.goshopping.R;

/**
 * Created by hwj on 2016-10-16.
 */

public class RetrievePasswordFragment extends Fragment implements View.OnClickListener{

    private View mConvertView;
    private Button VerificationCodeBtn;
    private EditText phoneEt;
    private EditText verificationCodeEt;
    private Button nextBtn;
    private String mVerificationCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mConvertView = inflater.inflate(R.layout.fragment_retrieve_password,container,false);

        initView();
        initEven();

        return mConvertView;
    }

    /**
     * 初始化事件
     */
    private void initEven() {
        VerificationCodeBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

    }
    /**
     * 初始化view
     */
    private void initView() {
        VerificationCodeBtn = (Button) mConvertView.findViewById(R.id.retrieve_password_verificationCode_change);
        phoneEt = (EditText) mConvertView.findViewById(R.id.retrieve_password_phone);
        verificationCodeEt = (EditText) mConvertView.findViewById(R.id.retrieve_password_verificationCode);
        nextBtn = (Button) mConvertView.findViewById(R.id.retrieve_password_next_btn);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.retrieve_password_verificationCode_change:      //获取验证码
                mVerificationCode = getVerificationCode(phoneEt.getText().toString());
                break;
            case R.id.retrieve_password_next_btn:                      //下一步
                nextStep();
                break;
        }
    }

    private void nextStep() {
        phoneEt.setError(null);
        verificationCodeEt.setError(null);

        String phone = phoneEt.getText().toString();
        String verificationCode = verificationCodeEt.getText().toString();
        boolean cancel = false;
        View focusView = null;

        //检测需要填写的信息
        if(TextUtils.isEmpty(phone)) {
            phoneEt.setError("手机号码不能为空");
            focusView = phoneEt;
            cancel = true;
        }else if(!isPhoneValid(phone)){
            phoneEt.setError("手机号码格式不正确");
            focusView = phoneEt;
            cancel = true;
        }else if(TextUtils.isEmpty(verificationCode)){
            verificationCodeEt.setError("验证码不能为空");
            focusView = verificationCodeEt;
            cancel = true;
        }

        if(cancel){
            //设置焦点在空的view上
            focusView.requestFocus();
        }else {
            //下一步的操作

        }
    }

    /**
     * 获取验证码
     * @return
     */
    private String getVerificationCode(String phone) {
        phoneEt.setError(null);
        if(TextUtils.isEmpty(phone)){
            phoneEt.setError("手机号码不能为空");
            phoneEt.requestFocus();
        }else if(!isPhoneValid(phone)){
            phoneEt.setError("手机号码格式不正确");
            phoneEt.requestFocus();
        }
        return null;
    }

    //判断手机号码长度是否有效
    private boolean isPhoneValid(String phone){
        return phone.length() == 11;
    }

}
