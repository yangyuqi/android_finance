package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.MLoginEntity;
import com.youzheng.zhejiang.financebooking.Model.MRegisterEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.View.MyCountDownTimer;
import com.youzheng.zhejiang.financebooking.Widget.common.PublishUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends BaseActivity {

    private MyCountDownTimer timer ;
    private Button btnGetCode ,btn_register;
    private EditText edt_phone ,edt_code ,edt_pass ,edt_again_pas,edt_invite_code;
    private CheckBox cb ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        initView();
        initEvent();
    }

    private void initEvent() {
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PublicUtils.checkMobileNumber(edt_phone.getText().toString())){
                    showToast("请输入正确手机号");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("regPhone",edt_phone.getText().toString());
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.SEND_CODE, new OkHttpClientManager.StringCallback() {
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

        btn_register.setOnClickListener(new View.OnClickListener() {
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
                    showToast("密码不能少于8位");
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
                if (!cb.isChecked()){
                    showToast("请勾选服务协议");
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("smsCode",edt_code.getText().toString());
                map.put("regPhone",edt_phone.getText().toString());
                map.put("regPassword",PublicUtils.md5Password(edt_pass.getText().toString()));
                map.put("meReg",edt_invite_code.getText().toString());
                map.put("regTerminal","android");
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.REGISTER_USER, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        MRegisterEntity entity = gson.fromJson(response, MRegisterEntity.class);
                        if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
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
        btnGetCode = (Button) findViewById(R.id.btn_send);
        timer = new MyCountDownTimer(btnGetCode,60000,1000);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        btn_register = (Button) findViewById(R.id.btn_register);
        edt_code = (EditText) findViewById(R.id.edt_code);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        edt_again_pas = (EditText) findViewById(R.id.edt_again_pas);
        cb = (CheckBox) findViewById(R.id.cb);
        edt_invite_code = (EditText) findViewById(R.id.edt_invite_code);
    }
}
