package com.goshopping.usx.goshopping.ui;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.goshopping.usx.goshopping.R;

/**
 * Created by hwj on 2016-10-17.
 */

public class AccountMessageSettingActivity extends AppCompatPreferenceActivity {

    //Preference改变监听器
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String Value = newValue.toString();

            preference.setSummary(Value);
            return true;
        }
    };

    /**
     * 绑定数据到对应的设置上summary上
     * @param preference
     */
    private static void bindPreferenceSummaryToValue(Preference preference){
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("个人信息");
        setupActionBar();

        addPreferencesFromResource(R.xml.account_message_setting_main);

        //Preference修改时显示数据到summary上
        bindPreferenceSummaryToValue(findPreference("nickName_accountMessage"));
        bindPreferenceSummaryToValue(findPreference("headPicture_accountMessage"));
        bindPreferenceSummaryToValue(findPreference("place_accountMessage"));
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
}
