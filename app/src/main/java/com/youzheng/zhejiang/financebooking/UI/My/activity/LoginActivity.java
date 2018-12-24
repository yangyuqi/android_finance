package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.MainActivity;
import com.youzheng.zhejiang.financebooking.Model.MLoginDetails;
import com.youzheng.zhejiang.financebooking.Model.MRegisterEntity;
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

public class LoginActivity extends BaseActivity {

    private EditText edt_phone ,edt_pass;
    private Button btn_login ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        initEvent();
    }

    private void initEvent() {
        findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,RegisterActivity.class));
            }
        });

        findViewById(R.id.tv_find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,FindPasswordActivity.class));
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PublicUtils.checkMobileNumber(edt_phone.getText().toString())){
                    showToast("请输入正确手机号");
                    return;
                }

                if (edt_pass.getText().toString().equals("")){
                    showToast("请输密码");
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("regPassword",PublicUtils.md5Password(edt_pass.getText().toString()));
                map.put("regPhone",edt_phone.getText().toString());
                map.put("loginTerminal","android");
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.LOGIN_USER, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        MLoginDetails entity = gson.fromJson(response, MLoginDetails.class);
                        if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                            SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.token, entity.getToken());
                            SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.userName, entity.getUserName());
                            SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.invitaionCode, entity.getInvitaionCode());
                            SharedPreferencesUtils.setParam(mContext,SharedPreferencesUtils.isAuth,entity.getIsAuth());
                            ActiivtyStack.getScreenManager().clearAllActivity();
                            startActivity(new Intent(mContext, MainActivity.class));
                        }else {
                            showToast(PublicUtils.getMsg(entity.getRespCode()));
                        }
                    }
                });
            }
        });
    }

}
