package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.MainActivity;
import com.youzheng.zhejiang.financebooking.Model.MLoginEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.ActiivtyStack;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.View.MyCountDownTimer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DoLoginPasswordAndPhoneActivity extends BaseActivity {

    String type ;

    private MyCountDownTimer timer ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.do_login_password_layout);
        initView();

        initEvent();
    }

    private void initEvent() {
        findViewById(R.id.btn_reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = (String) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.userName,"");
                ((EditText)findViewById(R.id.tv_phone_num)).setText(phone);
                if (((EditText)findViewById(R.id.edt_old_num)).getText().toString().equals("")){
                    showToast("请输入旧密码");
                    return;
                }
                if (((EditText)findViewById(R.id.tv_new_num)).getText().toString().equals("")){
                    showToast("请输入重置密码");
                    return;
                }
                if (!((EditText)findViewById(R.id.tv_new_num)).getText().toString().equals(((EditText)findViewById(R.id.tv_agein_num)).getText().toString())){
                    showToast("两次密码不一致,请重新输入");
                    return;
                }

                Map<String,Object> map = new HashMap<>();
                map.put("regPhone",((EditText)findViewById(R.id.tv_phone_num)).getText().toString());
                map.put("type",2);
                map.put("regPassword", PublicUtils.md5Password(((EditText)findViewById(R.id.tv_new_num)).getText().toString()));
                map.put("oldPassword",PublicUtils.md5Password(((EditText)findViewById(R.id.edt_old_num)).getText().toString()));
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.REVISE_PASSWORD, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        MLoginEntity entity = gson.fromJson(response,MLoginEntity.class);
                        if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                            ActiivtyStack.getScreenManager().clearAllActivity();
                            startActivity(new Intent(mContext, LoginActivity.class));
                            finish();
                        }
                    }
                });
            }
        });


        findViewById(R.id.btn_exchange_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((EditText)findViewById(R.id.tv_ex_code)).getText().toString().equals("")){
                    showToast("请输入验证码");
                    return;
                }
                if (((EditText)findViewById(R.id.edt_ex_passweord)).getText().toString().equals("")){
                    showToast("请输入交易密码");
                    return;
                }
                if (!((EditText)findViewById(R.id.edt_ex_passweord)).getText().toString().equals(((EditText)findViewById(R.id.tv_ex_new_again)).getText().toString())){
                    showToast("两次交易密码不一致");
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("traderPassword",PublicUtils.md5Password(((EditText)findViewById(R.id.edt_ex_passweord)).getText().toString()));
                map.put("code",((EditText)findViewById(R.id.tv_ex_code)).getText().toString());
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.EXCHANGE_PASSWORD, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        MLoginEntity entity = gson.fromJson(response,MLoginEntity.class);
                        if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                            finish();
                        }
                        showToast(PublicUtils.getMsg(entity.getRespCode()));
                    }
                });
            }
        });

        findViewById(R.id.btn_ex_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((EditText)findViewById(R.id.tv_ex_phone)).getText().toString().equals("")){
                    showToast("请输入手机号码");
                    return;
                }
                timer.start();
                Map<String,Object> map = new HashMap<>();
                map.put("regPhone",((EditText)findViewById(R.id.tv_ex_phone)).getText().toString());
                map.put("type",4);
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.SEND_CODE_ALL, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {

                    }
                });
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
        timer = new MyCountDownTimer((Button) findViewById(R.id.btn_ex_code),60000,1000);
        type = getIntent().getStringExtra("type");
        String phone = (String) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.userName,"");
        ((EditText)findViewById(R.id.tv_ex_phone)).setText(phone);
        if (type.equals("1")){
            ((TextView)findViewById(R.id.textHeadTitle)).setText("登录密码");
            findViewById(R.id.ll_two).setVisibility(View.GONE);
            findViewById(R.id.ll_one).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_three).setVisibility(View.GONE);
        }else if (type.equals("2")){
            ((TextView)findViewById(R.id.textHeadTitle)).setText("绑定手机");
            findViewById(R.id.ll_two).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_one).setVisibility(View.GONE);
            findViewById(R.id.ll_three).setVisibility(View.GONE);
        }else if (type.equals("3")){
            ((TextView)findViewById(R.id.textHeadTitle)).setText("交易密码");
            findViewById(R.id.ll_two).setVisibility(View.GONE);
            findViewById(R.id.ll_one).setVisibility(View.GONE);
            findViewById(R.id.ll_three).setVisibility(View.VISIBLE);
        }
    }
}
