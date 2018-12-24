package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.user.MInfoAccountEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MInfoEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MInvestmentActivityDataEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MInvestmentActivityEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvestmentActivity extends BaseActivity {

    PieChart pieChart ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.investment_layout);
        initView();

        initEvent();

        initData();
    }

    private void initData() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.INVEST_MANAGE, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MInvestmentActivityEntity entity = gson.fromJson(response,MInvestmentActivityEntity.class);
                if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                    ((TextView)findViewById(R.id.tv_wait_money)).setText(PublicUtils.formatToDouble(""+entity.getData().getWaitGetInvestment()));
                    ((TextView)findViewById(R.id.tv_has_money)).setText(PublicUtils.formatToDouble(""+entity.getData().getGetBackInvestment()));
                    ((TextView)findViewById(R.id.edt_money_one)).setText(PublicUtils.formatToDouble(""+entity.getData().getAllInvestment()));
                    ((TextView)findViewById(R.id.edt_money_two)).setText(PublicUtils.formatToDouble(""+entity.getData().getBenifits()));
                    ((TextView)findViewById(R.id.edt_money_three)).setText(PublicUtils.formatToDouble(""+entity.getData().getGetBenifits()));
                    ((TextView)findViewById(R.id.edt_money_four)).setText(PublicUtils.formatToDouble(""+entity.getData().getWaitBenifits()));

                    initPia(entity.getData());
                }
            }
        });

        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.USER_ONFO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MInfoEntity infoEntity = gson.fromJson(response,MInfoEntity.class);
                if (infoEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                    if (infoEntity.getMemberAuth()==null){
                        return;
                    }
                    ((TextView)findViewById(R.id.tv_name)).setText(PublicUtils.formatToDouble(""+infoEntity.getAccount().getAccountAmount()));
                }else if (infoEntity.getRespCode().equals(PublicUtils.RELOGIN)){
                    ((TextView)findViewById(R.id.tv_name)).setText(PublicUtils.formatToDouble(""+0.00));

                }
            }
        });
    }

    private void initEvent() {
        findViewById(R.id.tv_exchange_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,AllInvestmentActivity.class);
                intent.putExtra("type","2");
                startActivity(intent);
            }
        });

        findViewById(R.id.tv_exchange_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,AllInvestmentActivity.class);
                intent.putExtra("type","0");
                startActivity(intent);
            }
        });

        findViewById(R.id.tv_exchange_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,AllInvestmentActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
            }
        });

        findViewById(R.id.tv_has_exchange_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,AllInvestmentActivity.class);
                intent.putExtra("type","3");
                startActivity(intent);
            }
        });
    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText("投资管理");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pieChart = (PieChart) findViewById(R.id.consume_pie_chart);
    }

    private void initPia(MInvestmentActivityDataEntity account) {

        float one = (float) (account.getWaitGetInvestment()/account.getAllInvestment()*100);
        float two = (float) (account.getGetBackInvestment()/account.getAllInvestment()*100);
        pieChart.setUsePercentValues(false);
        pieChart.setDescriptionTextSize(20);
        pieChart.setDescription("");
        String centerText = PublicUtils.formatToDouble(""+account.getAllInvestment());
        pieChart.setCenterText(centerText);//设置中间的文字
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setRotationEnabled(true);
        pieChart.setTransparentCircleRadius(0f);
        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(one,"")); strings.add(new PieEntry(two,""));
        PieDataSet dataSet = new PieDataSet(strings,"");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.round_FCC006));
        colors.add(getResources().getColor(R.color.round_FF7A20));
        colors.add(getResources().getColor(R.color.round_FF474C));
        dataSet.setColors(colors);
        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(false);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
