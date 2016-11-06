package com.goshopping.usx.goshopping.ui;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.ui.fragment.firstFragment;
import com.goshopping.usx.goshopping.ui.fragment.secondFragment;
import com.goshopping.usx.goshopping.ui.fragment.thirdFragment;
import com.goshopping.usx.goshopping.utils.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.goshopping.usx.goshopping.R.id.fragmentTabHost;
import static com.goshopping.usx.goshopping.utils.Util.dip2px;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar mToolbar;

    private DrawerLayout mDrawerLayout;

    private NavigationView mNavigationView;

    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentTabHost mTabHost;

    /**
     * tabHost的显示数据
     */
    private String[] tabTextArray = {"首页", "附近", "购物车","我的"};
    private int[] tabIconArray = {R.drawable.ic_home_grey600_24dp, R.drawable.ic_map_marker_grey600_24dp, R.drawable.ic_cart_grey600_24dp,R.drawable.ic_account_circle_grey600_24dp};
    private Class[] fragmentArray = {firstFragment.class, secondFragment.class, secondFragment.class,thirdFragment.class};
    private List<Set<View>> tabViewList = new ArrayList<Set<View>>();

    //设置退出在点击一次才能退出
    private long firstTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEven();
    }

    private void initEven() {
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int index = mTabHost.getCurrentTab();
                //初始化全部tab的颜色为灰色
                initTabViewColor();
                //设置选中的tab衍射
                setTabViewColor(index, Color.parseColor("#FF5722"));

                //初始化全部tab字体的大小
                initTabViewTextSize();
                //设置选中的tab字体的大小
                setTabViewTextSize(index,15);

                //初始化全部tab的图标topMargin
                initTabViewImageMargin();
                //设置选中的tab的图标的topMargin
                setTabViewImageMargin(index, dip2px(MainActivity.this,5));

                //重新初始化标题栏菜单,以实现菜单按钮的显示与隐藏
                invalidateOptionsMenu();
            }
        });
    }

    private void initTabViewColor() {
        for (int i = 0; i < tabTextArray.length; i++) {
            setTabViewColor(i,Color.parseColor("#757575"));
        }
    }

    private void setTabViewColor(int position,int color){
        Set<View> views = tabViewList.get(position);
        for (View view : views) {
            if(view instanceof ImageView){
                ((ImageView) view).setColorFilter(color);
            }else if(view instanceof TextView){
                ((TextView) view).setTextColor(color);
            }
        }
    }

    private void initTabViewTextSize(){
        for (int i = 0; i < tabTextArray.length; i++) {
            setTabViewTextSize(i,12);
        }
    }

    private void setTabViewTextSize(int position, float size){
        Set<View> views = tabViewList.get(position);
        for (final View view : views) {
            if(view instanceof TextView){
                int startSize = Util.px2sp(this,((TextView) view).getTextSize());
                if (startSize == size) return;


                final ValueAnimator animator = ValueAnimator.ofFloat(startSize,size);
                animator.setDuration(200);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ((TextView) view).setTextSize((Float) animator.getAnimatedValue());
                    }
                });
                animator.start();
            }
        }
    }

    private void initTabViewImageMargin(){
        for (int i = 0; i < tabTextArray.length; i++) {
            setTabViewImageMargin(i, dip2px(this,8));
        }
    }
    private void setTabViewImageMargin(int position,int margin){
        Set<View> views = tabViewList.get(position);
        for (final View view : views) {
            if(view instanceof ImageView){
                final LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) view.getLayoutParams();
                if(param.topMargin == margin) return;

                ValueAnimator animator = ValueAnimator.ofInt(param.topMargin,margin);
                animator.setDuration(200);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        param.topMargin = (int) animation.getAnimatedValue();
                        view.setLayoutParams(param);
                    }
                });
               animator.start();
            }
        }
    }

    private void initView() {


        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTabHost = (FragmentTabHost) findViewById(fragmentTabHost);


        addTabs();
        setTabViewTextSize(mTabHost.getCurrentTab(),15);
        setTabViewColor(mTabHost.getCurrentTab(),Color.parseColor("#FF5722"));
        setTabViewImageMargin(mTabHost.getCurrentTab(),dip2px(MainActivity.this,5));


    }

    private void addTabs() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realTabContent);

        for (int i = 0; i < tabTextArray.length; i++) {
            mTabHost.addTab(mTabHost.newTabSpec(tabTextArray[i]).setIndicator(getTabView(i)), fragmentArray[i], null);
        }


        mTabHost.getTabWidget().setDividerDrawable(null);
    }


    /**
     * 进行自定义页卡
     * @param position
     * @return
     */
    private View getTabView(int position) {

        View view = LayoutInflater.from(this).inflate(R.layout.item_tab_bottom, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.item_tab_bottom_image);
        TextView textView = (TextView) view.findViewById(R.id.item_tab_bottom_text);
        imageView.setImageResource(tabIconArray[position]);
        textView.setText(tabTextArray[position]);

        Set<View> views = new HashSet<View>();
        views.add(imageView);
        views.add(textView);
        tabViewList.add(views);

        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //获取设置按钮的菜单按钮
        MenuItem settingItem = menu.findItem(R.id.menu_setting);
        //获取地图按钮的菜单按钮
        MenuItem locationItem = menu.findItem(R.id.menu_location);
        int mCurrentTab = mTabHost.getCurrentTab();

        //设置菜单按钮的显示与隐藏
        switch (mCurrentTab){
            case 0:
                settingItem.setVisible(false);
                locationItem.setVisible(false);
                break;
            case 1:
                settingItem.setVisible(false);
                locationItem.setVisible(true);
                break;
            case 2:
                settingItem.setVisible(false);
                locationItem.setVisible(false);
                break;
            case 3:
                settingItem.setVisible(true);
                locationItem.setVisible(false);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_setting:
                startActivity(new Intent(this,AccountMessageSettingActivity.class));
                overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
                return true;
            case R.id.menu_location:

                startActivity(new Intent(this,MapActivity.class));
                overridePendingTransition(R.anim.activity_in,R.anim.activity_out);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            //设置退出应用程序退出时再按一次
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
