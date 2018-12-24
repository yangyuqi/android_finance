package com.youzheng.zhejiang.financebooking;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.UI.Home.HomePageFragment;
import com.youzheng.zhejiang.financebooking.UI.My.UserCenterFragment;
import com.youzheng.zhejiang.financebooking.UI.finance.FinanceFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends BaseActivity {

    FrameLayout frameLayout ;
    RadioGroup group;
    private ArrayList<String> fragmentTags;
    private   String CURR_INDEX = "currIndex";
    private  int currIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData(savedInstanceState);
        initView();
        initEvent();
        showFragment();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX, currIndex);
    }


    private void initData(Bundle savedInstanceState) {
        fragmentTags = new ArrayList<>(Arrays.asList("HomePageFragment", "CategoryFragment","UserCenterFragment"));
        if(savedInstanceState != null) {
            currIndex = savedInstanceState.getInt(CURR_INDEX);
            hideSavedFragment();
        }
    }

    private void hideSavedFragment() {
        Fragment fragment = fm.findFragmentByTag(fragmentTags.get(currIndex));
        if(fragment != null) {
            fm.beginTransaction().hide(fragment).commit();
        }
    }

    private void initEvent() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.foot_bar_home : currIndex = 0 ;break;
                    case R.id.foot_bar_im : currIndex = 1 ;break;
                    case R.id.main_footbar_user : currIndex = 2 ;break;
                }
                showFragment();
            }
        });
    }

    private void initView() {
        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        group = (RadioGroup) findViewById(R.id.group);
    }

    private void showFragment() {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag(fragmentTags.get(currIndex));
        if(fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fm.findFragmentByTag(fragmentTags.get(i));
            if(f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fm.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0 :
                return new HomePageFragment();
            case 1:
               return new FinanceFragment();
            case 2:
                return new UserCenterFragment();

            default:
                return null;
        }
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if(true){
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(),
                            "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    currIndex=0;
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
