package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.MLoginEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OpenHandPassword extends BaseActivity {

    Switch aSwitch;

    private int state ,mid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_hand_password);
        state = getIntent().getIntExtra("state",2);
        mid = getIntent().getIntExtra("mId",0);
        initView();
    }

    private void initView() {
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView)findViewById(R.id.textHeadTitle)).setText("手势密码");

        aSwitch = (Switch) findViewById(R.id.sw_switch);

        if (state==1){
            aSwitch.setChecked(true);
        }else if (state==2){
            aSwitch.setChecked(false);
        }



        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(mContext,GesturePasswordActivity.class));
                if (state==1){
                    state = 2;
                }else if (state==2){
                    state =1 ;
                }
                Log.e("sssssssss","--"+state);
                Map<String,Object> map = new HashMap<>();
                map.put("mId",mid);
                map.put("gestureStatus",state);
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GESTURE_IS_OPEN, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        MLoginEntity entity = gson.fromJson(response,MLoginEntity.class);
                        if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                            SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.shoushi, state);
                        }
                    }
                });
            }
        });
    }
}
