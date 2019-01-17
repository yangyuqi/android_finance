package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.MLoginEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MFinanceStatusDataEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MFinanceStatusEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InvestmentDetailsActivity extends BaseActivity implements View.OnClickListener {

    MFinanceStatusDataEntity dataEntity ;
    private Button btn_pay;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.investment_details_layout);
        dataEntity = (MFinanceStatusDataEntity) getIntent().getSerializableExtra("data");
        type=getIntent().getIntExtra("type",0);
        initView();
    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText("投资明细");
        btn_pay= (Button) findViewById(R.id.btn_pay);

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((TextView)findViewById(R.id.tv_name)).setText(dataEntity.getProductName());
        ((TextView)findViewById(R.id.tv_card_money)).setText(PublicUtils.formatToDouble(""+dataEntity.getProductAccount()));
        ((TextView)findViewById(R.id.tv_phone)).setText(PublicUtils.formatToDouble(""+dataEntity.getOrderAccount()));
        ((TextView)findViewById(R.id.tv_time)).setText(""+dataEntity.getCricle());
        ((TextView)findViewById(R.id.tv_card)).setText(""+dataEntity.getProductAccrual());

        if (dataEntity.getFinancingStatus()==2){
            ((TextView)findViewById(R.id.tv_account_safe)).setText("投资中");
        }else if (dataEntity.getFinancingStatus()==1){
            ((TextView)findViewById(R.id.tv_account_safe)).setText("待投资");
        }else if (dataEntity.getFinancingStatus()==3){
            ((TextView)findViewById(R.id.tv_account_safe)).setText("待结算");
        }else if (dataEntity.getFinancingStatus()==4){
            ((TextView)findViewById(R.id.tv_account_safe)).setText("已结算");
        }else if (dataEntity.getFinancingStatus()==5){
            ((TextView)findViewById(R.id.tv_account_safe)).setText("已提现");
        }

        if (dataEntity.getOrderStatus()==1){
            ((TextView)findViewById(R.id.tv_account_statue)).setText("待付款");
        }else if (dataEntity.getOrderStatus()==2){
            ((TextView)findViewById(R.id.tv_account_statue)).setText("已付款");
        }

        if (type==1){
            btn_pay.setVisibility(View.GONE);
        }else {
            btn_pay.setVisibility(View.VISIBLE);
        }

        btn_pay.setOnClickListener(this);

    }

    private void intData() {
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",dataEntity.getOrderId());

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.PAY_ORDER, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                Log.e("支付返回",response);
                MLoginEntity statusEntity = gson.fromJson(response,MLoginEntity.class);
                if (statusEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                        finish();
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        intData();
    }
}
