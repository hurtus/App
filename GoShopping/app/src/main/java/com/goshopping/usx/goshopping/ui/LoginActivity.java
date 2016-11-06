package com.goshopping.usx.goshopping.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.ui.fragment.loginNormalFragment;
import com.goshopping.usx.goshopping.ui.fragment.loginPhoneFragment;

import java.util.ArrayList;
import java.util.List;

import static com.goshopping.usx.goshopping.R.id.item_fragment_login_tabhost_tv;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FragmentTabHost mTabHost;
    private String[] tabTextArray = { "账号密码登录","手机快捷登录" };
    private Class[] fragmentArray = {loginNormalFragment.class, loginPhoneFragment.class};
    private List<View> tabViewList = new ArrayList<View>();

    private Button registerBtn;
    private Button retrievePasswordBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupActionBar();
        setTitle("登录");

        initView();
        addTab();
        initEven();

    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

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

    private void initEven() {

        //设置tabHost点击时导航栏中的指示器的显示与隐藏
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < tabTextArray.length; i++) {
                    View view = tabViewList.get(i).findViewById(R.id.item_fragment_login_tabhost_v);
                    view.setVisibility(View.GONE);
                    if(tabTextArray[i].equals(tabId)){
                        view.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        //注册按钮设置点击事件
        registerBtn.setOnClickListener(this);
        //忘记密码按钮点击事件
        retrievePasswordBtn.setOnClickListener(this);
    }

    private void initView() {
        mTabHost = (FragmentTabHost) findViewById(R.id.login_fragmentTabHost);
        registerBtn = (Button) findViewById(R.id.register_btn);
        retrievePasswordBtn = (Button) findViewById(R.id.retrieve_password_btn);
    }

    private void addTab() {
        mTabHost.setup(this,getSupportFragmentManager(),R.id.login_realTabContent);

        for(int i=0;i<tabTextArray.length;i++){
            mTabHost.addTab(mTabHost.newTabSpec(tabTextArray[i]).setIndicator(getTabView(i)),fragmentArray[i],null);
        }

        //设置第一个tab的指示器显示
        View view = tabViewList.get(0).findViewById(R.id.item_fragment_login_tabhost_v);
        view.setVisibility(View.VISIBLE);

        mTabHost.getTabWidget().setDividerDrawable(null);
    }

    private View getTabView(int position){

        View view = LayoutInflater.from(this).inflate(R.layout.item_tab_fragment_login,null);

        TextView tv = (TextView) view.findViewById(item_fragment_login_tabhost_tv);
        tv.setText(tabTextArray[position]);
        tabViewList.add(view);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent(LoginActivity.this,LoginOtherActivity.class);;
        switch (id){
            case R.id.register_btn:             //注册
                intent.putExtra("fragment","register");

                break;
            case R.id.retrieve_password_btn:    //忘记密码
                intent.putExtra("fragment","retrievePassword");
                break;
        }

        startActivity(intent);
        overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
    }
}
