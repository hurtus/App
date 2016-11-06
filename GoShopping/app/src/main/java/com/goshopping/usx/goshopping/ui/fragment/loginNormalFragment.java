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
import android.widget.ImageView;

import com.goshopping.usx.goshopping.R;

/**
 * Created by hwj on 2016-10-16.
 */

public class loginNormalFragment extends Fragment implements View.OnClickListener{

    private View mConvertView;
    private Button VerificationCodeBtn;
    private EditText accountEt;
    private EditText verificationCodeEt;
    private EditText passwordEt;
    private Button loginBtn;
    private ImageView VerificationCodeIv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mConvertView = inflater.inflate(R.layout.fragment_login_normal,container,false);

        initView();
        initEven();

        return mConvertView;
    }

    /**
     * 初始化事件
     */
    private void initEven() {
        VerificationCodeBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

    }
    /**
     * 初始化view
     */
    private void initView() {
        VerificationCodeBtn = (Button) mConvertView.findViewById(R.id.login_verificationCode_change_normal);
        accountEt = (EditText) mConvertView.findViewById(R.id.login_account_normal);
        passwordEt = (EditText) mConvertView.findViewById(R.id.login_password_normal);
        verificationCodeEt = (EditText) mConvertView.findViewById(R.id.login_verificationCode_text_normal);
        loginBtn = (Button) mConvertView.findViewById(R.id.login_btn_normal);
        VerificationCodeIv = (ImageView) mConvertView.findViewById(R.id.login_verificationCode_image_normal);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.login_verificationCode_change_normal:      //获取验证码
                setVerificationCode();
                break;
            case R.id.login_btn_normal:                          //登录
                attemptLogin();
                break;
        }
    }

    /**
     * 准备登录
     */
    private void attemptLogin(){
        accountEt.setError(null);
        verificationCodeEt.setError(null);
        passwordEt.setError(null);

        String account = accountEt.getText().toString();
        String verificationCode = verificationCodeEt.getText().toString();
        String password = passwordEt.getText().toString();
        boolean cancel = false;
        View focusView = null;

        //检测需要填写的信息是否有空
        if(TextUtils.isEmpty(account)) {
            accountEt.setError("用户名不能为空");
            focusView = accountEt;
            cancel = true;
        }else if(TextUtils.isEmpty(password)){
            passwordEt.setError("密码不能为空");
            focusView = passwordEt;
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
            //登录操作
        }
    }

    /**
     * 获取验证码
     * @return
     */
    private void setVerificationCode() {


    }

}
