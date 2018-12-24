package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.user.MGetBankEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BankCardDetailsActivity extends BaseActivity {

    private Integer id ,mId ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_card_details_layout);
        inotView();
        initData();
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("mId",mId);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.BANK_CARD_DETAILS, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MGetBankEntity bankEntity = gson.fromJson(response,MGetBankEntity.class);
                if (bankEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                    ((EditText)findViewById(R.id.tv_name)).setText(bankEntity.getName());
                    ((EditText)findViewById(R.id.tv_card_num)).setText(bankEntity.getIdCard());
                    ((EditText)findViewById(R.id.tv_card_name)).setText(bankEntity.getOpenBank());
                    ((EditText)findViewById(R.id.tv_card_)).setText(bankEntity.getBankCard());
                    ((EditText)findViewById(R.id.tv_card)).setText(bankEntity.getPhone());

                   ((TextView)findViewById(R.id.tv_bank_num)).setText(bankEntity.getBankCard());
                    ((TextView)findViewById(R.id.tv_bank_name)).setText(bankEntity.getOpenBank());
//                    Glide.with(mContext).load(UrlUtils.BASEL_PHOTO+bankEntity.getIdCardFont()).error(R.mipmap.group_12_1).into((ImageView) findViewById(R.id.iv_front_card));
//                    Glide.with(mContext).load(UrlUtils.BASEL_PHOTO+infoEntity.getIdCardBack()).error(R.mipmap.group_12_2).into((ImageView) findViewById(R.id.iv_front_back));
                    if (bankEntity.getCardType()==1){
                        ((EditText)findViewById(R.id.tv_card_type)).setText("借记卡");
                    }

                }
            }
        });
    }

    private void inotView() {
        ((TextView)findViewById(R.id.textHeadNext)).setText("修改");
        ((TextView)findViewById(R.id.textHeadTitle)).setText("银行卡管理");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        id = getIntent().getIntExtra("id",0);
        mId = getIntent().getIntExtra("mId",0);
    }
}
