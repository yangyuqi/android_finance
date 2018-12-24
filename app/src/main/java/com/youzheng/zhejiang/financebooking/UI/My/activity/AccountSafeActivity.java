package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.user.MGestureStatus;
import com.youzheng.zhejiang.financebooking.Model.user.MGestureStatusDataEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MInfoEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AccountSafeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_safe_layout);

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.USER_ONFO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MInfoEntity infoEntity = gson.fromJson(response,MInfoEntity.class);
                if (infoEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                    try {
                        if (infoEntity.getMemberAuth().getmRealNameStatus() == 2) {
                            ((TextView) findViewById(R.id.tv_auth)).setText("已认证");
                        } else if (infoEntity.getMemberAuth().getmRealNameStatus() == 1) {
                            ((TextView) findViewById(R.id.tv_auth)).setText("未认证");
                        } else if (infoEntity.getMemberAuth().getmRealNameStatus() == 3) {
                            ((TextView) findViewById(R.id.tv_auth)).setText("认证失败");
                            ((TextView)findViewById(R.id.tv_phone)).setText(infoEntity.getMemberAuth().getmPhone());
                            if (infoEntity.getAccount().getTraderPassword()!=null){
                                ((TextView)findViewById(R.id.tv_exchange_password)).setText("已设置");
                            }else {
                                ((TextView)findViewById(R.id.tv_exchange_password)).setText("未设置");
                            }

                            queryHand(infoEntity.getAccount().getmId());
                        }
                    }catch (Exception e){}

                }else if (infoEntity.getRespCode().equals(PublicUtils.RELOGIN)){

                }
            }
        });
    }

    private void queryHand(final Integer integern) {
        Map<String,Object> map = new HashMap<>();
        map.put("mId",integern);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.QUET_HAND_EXIST, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MGestureStatusDataEntity dataEntity = gson.fromJson(response,MGestureStatusDataEntity.class);
                if (dataEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                    final MGestureStatus status = dataEntity.getData();
                    if (status.getSize()==0){
                        ((TextView)findViewById(R.id.tv_sex)).setText("未设置");
                    }else if (status.getSize()==1){
                        ((TextView)findViewById(R.id.tv_sex)).setText("已设置");
                    }
                    findViewById(R.id.rl_select_sex).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,OpenHandPassword.class) ;
                            if (status.getGestureStatus()==null) {
                                intent.putExtra("state", 2);
                            }else {
                                intent.putExtra("state",status.getGestureStatus());
                            }
                            intent.putExtra("mId",integern);
                            startActivity(intent);
                        }
                    });
                }else {
                    showToast(PublicUtils.getMsg(dataEntity.getRespCode()));
                }
            }
        });
    }

    private void initView() {
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView)findViewById(R.id.textHeadTitle)).setText("账户安全");

        findViewById(R.id.rl_login_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DoLoginPasswordAndPhoneActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
            }
        });

        findViewById(R.id.rl_select_auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,AttentionInfoActivity.class));
            }
        });

        findViewById(R.id.rl_band_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext,DoLoginPasswordAndPhoneActivity.class);
//                intent.putExtra("type","2");
//                startActivity(intent);
            }
        });

        findViewById(R.id.rl_exchange_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DoLoginPasswordAndPhoneActivity.class);
                intent.putExtra("type","3");
                startActivity(intent);
            }
        });

//        findViewById(R.id.rl_select_sex).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(mContext,OpenHandPassword.class));
//            }
//        });
    }
}
