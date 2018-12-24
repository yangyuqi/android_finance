package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.financebooking.Widget.View.MyCountDownTimer;

public class GestureForgetPassActivity extends BaseActivity {

    private MyCountDownTimer timer ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture_forget_pass_layout);

        initData();
    }

    private void initData() {
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView)findViewById(R.id.textHeadTitle)).setText("账户安全");
        timer = new MyCountDownTimer((Button) findViewById(R.id.btn_send_code),60000,1000);
        String phone = (String) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.userName,"");
        ((EditText)findViewById(R.id.tv_phone_num)).setText(phone);

        findViewById(R.id.btn_send_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((EditText)findViewById(R.id.tv_phone_num)).getText().toString().equals("")){
                    showToast("请输入手机号");
                    return;
                }
                timer.start();

            }
        });
    }
}
