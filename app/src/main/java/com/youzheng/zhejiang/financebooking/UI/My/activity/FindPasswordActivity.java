package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import static com.youzheng.zhejiang.financebooking.R.id.btn_send_code;

public class FindPasswordActivity extends BaseActivity {

    private EditText edt_phone ,edt_code ,edt_pass ,edt_again_pas;
    private Button btn_send_code ,btn_confirm;
    private MyCountDownTimer timer ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password_layout);
        initView();
        initEvent();
    }

    private void initEvent() {
        btn_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PublicUtils.checkMobileNumber(edt_phone.getText().toString())){
                    showToast("请输入正确手机号");
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("regPhone",edt_phone.getText().toString());
                map.put("type","4");
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.SEND_CODE_ALL, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        MLoginEntity entity = gson.fromJson(response, MLoginEntity.class);
                        showToast(PublicUtils.getMsg(entity.getRespCode()));
                        if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                            timer.start();
                        }
                    }
                });
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PublicUtils.checkMobileNumber(edt_phone.getText().toString())){
                    showToast("请输入正确手机号");
                    return;
                }
                if (edt_code.getText().toString().equals("")){
                    showToast("请输入验证码");
                    return;
                }
                if (edt_pass.getText().toString().equals("")){
                    showToast("请输密码");
                    return;
                }
                if (edt_pass.getText().toString().length()<8){
                    showToast("密码不能小于8位");
                    return;
                }

                if (edt_again_pas.getText().toString().equals("")){
                    showToast("请输密码");
                    return;
                }
                if (!edt_pass.getText().toString().equals(edt_again_pas.getText().toString())){
                    showToast("两次密码不一致");
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("regPhone",edt_phone.getText().toString());
                map.put("regPassword",PublicUtils.md5Password(edt_pass.getText().toString()));
                map.put("smsCode",edt_code.getText().toString());
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.CHANGE_PASS, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        MLoginEntity entity = gson.fromJson(response, MLoginEntity.class);
                        if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                            startActivity(new Intent(mContext,LoginActivity.class));
                            finish();
                        }else {
                            showToast(PublicUtils.getMsg(entity.getRespCode()));
                        }
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
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        btn_send_code = (Button) findViewById(R.id.btn_send_code);
        edt_code = (EditText) findViewById(R.id.edt_code);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        edt_again_pas = (EditText) findViewById(R.id.edt_again_pas);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        timer = new MyCountDownTimer(btn_send_code,60000,1000);
    }
}
