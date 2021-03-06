package com.youzheng.zhejiang.financebooking.UI.finance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.youzheng.zhejiang.financebooking.MainActivity;
import com.youzheng.zhejiang.financebooking.Model.home.MVersionEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.common.PublishUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rx.functions.Action1;

public class SplashActivity extends BaseActivity  {

    int widWidth ,widHeight;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        widWidth = outMetrics.widthPixels;
        widHeight = outMetrics.heightPixels;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }
        },2000);
    }


    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("terminal","Android");
        map.put("versionNo","mock");
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.UPDAATE_VERSION, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    MVersionEntity entity = gson.fromJson(response, MVersionEntity.class);
                    if (entity.getRespCode().equals(PublicUtils.SUCCESS)) {
                        if (entity.getData().getAppVersion().getVersionToUpdate() == 1) {
                            if (entity.getData().getAppVersion().getVersionUpdate() == 1) {
                                final ProgressDialog dialog2 = ProgressDialog.show(mContext, "提示", "正在下载新的版本");
                                OkHttpClientManager.downloadAsyn(entity.getData().getAppVersion().getVersionUrl(), Environment.getExternalStorageDirectory().getPath(), new OkHttpClientManager.StringCallback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        if (!response.equals("")) {
                                            dialog2.dismiss();
                                            File file = new File(response);
                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                            //设置intent的数据类型是应用程序application
                                            intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
                                            //为这个新apk开启一个新的activity栈
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            //开始安装
                                            startActivity(intent);
                                            //关闭旧版本的应用程序的进程
                                            android.os.Process.killProcess(android.os.Process.myPid());
                                        }
                                    }
                                });

                            } else {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(mContext, MainActivity.class));
                                    }
                                }, 2000);
                            }
                        }
                    }
                }catch (Exception e){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(mContext, MainActivity.class));
                        }
                    }, 2000);
                }
            }
        });

    }



}
