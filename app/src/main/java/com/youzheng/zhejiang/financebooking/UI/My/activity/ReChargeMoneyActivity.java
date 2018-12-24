package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;

public class ReChargeMoneyActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.re_charge_money_layout);

        initView();
    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText("充值");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
