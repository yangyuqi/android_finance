package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.user.MInfoEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;

import java.io.IOException;
import java.util.HashMap;

public class UserCenterActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_layout);
        initView();

        initData();
    }

    private void initData() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.USER_ONFO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                    MInfoEntity infoEntity = gson.fromJson(response, MInfoEntity.class);
                    if (infoEntity.getRespCode().equals(PublicUtils.SUCCESS)) {
                        if (infoEntity.getMemberAuth() == null) {
                            return;
                        }
                        ((TextView) findViewById(R.id.tv_name)).setText(infoEntity.getMemberAuth().getmName());
                        ((TextView) findViewById(R.id.tv_card_id)).setText(infoEntity.getMemberAuth().getmIdcard());
                        ((TextView) findViewById(R.id.tv_phone)).setText(infoEntity.getMemberAuth().getmPhone());
                        ((TextView) findViewById(R.id.tv_time)).setText(infoEntity.getMemberAuth().getmBankphone());
                    }
            }
        });
    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText("基本信息");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
