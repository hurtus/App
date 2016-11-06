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
import android.widget.Toast;

import com.goshopping.usx.goshopping.R;

/**
 * Created by hwj on 2016-10-16.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private View mConvertView;

    private EditText phoneEt;
    private EditText verificationCodeEt;
    private EditText passwordEt;
    private EditText passwordAgainEt;
    private Button verificationCodeBtn;
    private Button registerBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mConvertView = inflater.inflate(R.layout.fragment_register,container,false);

        initView();
        initEven();

        return mConvertView;
    }

    private void initView() {
        phoneEt = (EditText) mConvertView.findViewById(R.id.register_account);
        verificationCodeEt = (EditText) mConvertView.findViewById(R.id.register_verificationCode);
        passwordAgainEt = (EditText) mConvertView.findViewById(R.id.register_password_again);
        passwordEt = (EditText) mConvertView.findViewById(R.id.register_password);
        verificationCodeBtn = (Button) mConvertView.findViewById(R.id.register_verificationCode_btn);
        registerBtn = (Button) mConvertView.findViewById(R.id.register_btn);
    }

    private void initEven() {
        verificationCodeBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.register_verificationCode_btn:        //获取验证码
                getVerificationCode(phoneEt.getText().toString());
                break;
            case R.id.register_btn:                         //注册
                attemptRegister();
                break;
        }
    }

    private void getVerificationCode(String phoneNumber) {
        phoneEt.setError(null);
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneEt.setError("手机号码不能为空");
            phoneEt.requestFocus();
        } else if (!isPhoneValid(phoneNumber)) {
            phoneEt.setError("手机号码格式不正确");
            phoneEt.requestFocus();
        }
    }

    private void attemptRegister() {
        phoneEt.setError(null);
        verificationCodeEt.setError(null);
        passwordEt.setError(null);
        passwordAgainEt.setError(null);

        String phoneNumber = phoneEt.getText().toString();
        String verificationCode = verificationCodeEt.getText().toString();
        String password = passwordEt.getText().toString();
        String passwordAgain = passwordAgainEt.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //检测需要填写的信息是否有误
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneEt.setError("手机号码不能为空");
            focusView = phoneEt;
            cancel = true;
        } else if (!isPhoneValid(phoneNumber)) {
            phoneEt.setError("手机号码格式不正确");
            focusView = phoneEt;
            cancel = true;
        } else if (TextUtils.isEmpty(verificationCode)) {
            verificationCodeEt.setError("验证码不能为空");
            focusView = verificationCodeEt;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            passwordEt.setError("密码不能为空");
            focusView = passwordEt;
            cancel = true;
        } else if (TextUtils.isEmpty(passwordAgain)) {
            passwordAgainEt.setError("不能为空");
            focusView = passwordAgainEt;
            cancel = true;
        } else if (!password.equals(passwordAgain)) {
            Toast.makeText(getActivity(), "两次输入的密码不一样,请重新输入", Toast.LENGTH_SHORT).show();
            passwordAgainEt.setText("");
            passwordEt.setText("");
            focusView = passwordEt;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();

        } else {
            //进行注册操作
        }
    }

    //判断输入的手机号码的位数是否符合
    private boolean isPhoneValid(String phone) {
        return phone.length() == 11;
    }
}
