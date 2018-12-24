package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.MLoginEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.ActiivtyStack;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SetAppActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_app_layout);

        initView();
    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText("帮助中心");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<>();
                map.put("phone", SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.userName,""));
                map.put("terminal","Android");
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.LOGIN_OUT, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        MLoginEntity entity = gson.fromJson(response,MLoginEntity.class);
                        if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                            ActiivtyStack.getScreenManager().clearAllActivity();
                            startActivity(new Intent(mContext,LoginActivity.class));
                            SharedPreferencesUtils.clear(mContext);
                        }else {
                            showToast(PublicUtils.getMsg(entity.getRespCode()));
                        }
                    }
                });
            }
        });

        findViewById(R.id.tv_user_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,AboutAppActivity.class));
            }
        });
    }
}
