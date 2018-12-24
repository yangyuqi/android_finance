package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.MLoginEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.View.MyCountDownTimer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddBankCardActivity extends BaseActivity {
    private MyCountDownTimer timer ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bank_card_layout);
        initView();
    }
    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText("添加银行卡");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        timer = new MyCountDownTimer(((Button)findViewById(R.id.btn_send_code)),60000,1000);
        findViewById(R.id.btn_add_bank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDesc();
            }
        });

        findViewById(R.id.btn_send_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((EditText)findViewById(R.id.tv_phone_num)).getText().toString().equals("")){
                    showToast("请输入银行预留手机号码");
                    return;
                }
                timer.start();
                Map<String,Object> map = new HashMap<>();
                map.put("regPhone",((EditText)findViewById(R.id.tv_phone_num)).getText().toString());
                map.put("type","1");
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.SEND_CODE_ALL, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        MLoginEntity entity = gson.fromJson(response,MLoginEntity.class);
                        showToast(entity.getRespMsg());
                    }
                });
            }
        });
    }

    private void initDesc() {
        if (((EditText)findViewById(R.id.tv_name)).getText().toString().equals("")){
            showToast("请输入真实姓名");
            return;
        }
        if (((EditText)findViewById(R.id.tv_name)).getText().toString().equals("")){
            showToast("请输入身份证号");
            return;
        }

        if (((EditText)findViewById(R.id.tv_bank)).getText().toString().equals("")){
            showToast("请输入开户银行");
            return;
        }

        if (((EditText)findViewById(R.id.tv_card_num)).getText().toString().equals("")){
            showToast("请输入银行卡号");
            return;
        }

        if (((EditText)findViewById(R.id.tv_phone_num)).getText().toString().equals("")){
            showToast("请输入银行预留手机号码");
            return;
        }

        if (((EditText)findViewById(R.id.tv_phone_code)).getText().toString().equals("")){
            showToast("请输入验证码");
            return;
        }

        Map<String,Object> map = new HashMap<>();
        map.put("realName",((EditText)findViewById(R.id.tv_name)).getText().toString());
        map.put("phone",((EditText)findViewById(R.id.tv_phone_num)).getText().toString());
        map.put("bankCard",((EditText)findViewById(R.id.tv_card_num)).getText().toString());
        map.put("idCard",((EditText)findViewById(R.id.tv_name)).getText().toString());
        map.put("openBank",((EditText)findViewById(R.id.tv_bank)).getText().toString());
        map.put("cardType",1);
        map.put("code",((EditText)findViewById(R.id.tv_phone_code)).getText().toString());
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ADD_BANK_CARD, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MLoginEntity entity = gson.fromJson(response,MLoginEntity.class);
                showToast(entity.getRespMsg());
                if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                    finish();
                }
            }
        });
    }
}
