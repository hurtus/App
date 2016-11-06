package com.goshopping.usx.goshopping.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.utils.PreferenceUtils;

/**
 *
 */
public class SplashActivity extends AppCompatActivity {


    //动画显示图片
    private ImageView mSplashImage;

    // 动画执行时间
    private static final int ANIMATION_DURATION = 2000;

    private static final int GO_HOME = 100;

    private static final int GO_LOGIN = 200;


    //判断是否加载注册界面
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case GO_HOME:
                    animateImage(0);
                    break;
                case GO_LOGIN:
                    animateImage(1);
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {

        super.onResume();
        boolean login = PreferenceUtils.getBoolean("login", false);
        if (login) {
            mHandler.sendEmptyMessageDelayed(GO_HOME, 1000);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, 1000);
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }


    private void animateImage(final int flag) {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mSplashImage, "scaleX", 1f, 1.13f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mSplashImage, "scaleY", 1f, 1.13f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                if (flag == 0) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
    }


}
