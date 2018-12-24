package com.youzheng.zhejiang.financebooking.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.youzheng.zhejiang.financebooking.Widget.Utils.ActiivtyStack;

public class BaseActivity extends AppCompatActivity {
    protected Context mContext ;
    protected FragmentManager fm;
    protected Gson gson ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        mContext = this ;
        gson = new Gson();
        // 每次加入stack
        ActiivtyStack.getScreenManager().pushActivity(this);
    }

    protected void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        // 退出时弹出stack
        ActiivtyStack.getScreenManager().popActivity(this);
        super.onDestroy();
    }
}
