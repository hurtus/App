package com.goshopping.usx.goshopping.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.ui.fragment.RegisterFragment;
import com.goshopping.usx.goshopping.ui.fragment.RetrievePasswordFragment;

/**
 * 装载Login页面中的其他fragment界面
 */
public class LoginOtherActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_other);
        setupActionBar();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //获取需要装载的fragment标识
        Intent intent = getIntent();
        String tag = intent.getStringExtra("fragment");

        if(tag.equals("register")){                 //装载注册页面
            setTitle("注册");
            RegisterFragment fragment = new RegisterFragment();
            transaction.replace(R.id.login_other_frame,fragment);

        }else if(tag.equals("retrievePassword")){   //装载找回密码的页面
            setTitle("找回密码");
            RetrievePasswordFragment fragment = new RetrievePasswordFragment();
            transaction.replace(R.id.login_other_frame,fragment);
        }
        transaction.commit();
    }

    /**
     * 设置返回按钮
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 设置返回按钮的点击销毁Activity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        overridePendingTransition(R.anim.activity_in_back,R.anim.activity_out_back);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.activity_in_back,R.anim.activity_out_back);
        super.onBackPressed();
    }
}
