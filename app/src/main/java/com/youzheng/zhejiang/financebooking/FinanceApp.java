package com.youzheng.zhejiang.financebooking;

import android.app.Application;
import android.content.Context;

import com.youzheng.zhejiang.financebooking.UI.BaseActivity;

public class FinanceApp extends Application {

    public static FinanceApp financeApp ;

    @Override
    public void onCreate() {
        super.onCreate();
        financeApp = this ;
    }
}
