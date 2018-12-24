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
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MoneyManageActivity extends BaseActivity {

    PieChart pieChart ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_manage_layout);

        initView();
        initData();
        initEvent();

    }

    private void initEvent() {
        findViewById(R.id.tv_exchange_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ExChangeActivity.class);
                intent.putExtra("type","2");
                startActivity(intent);
            }
        });

        findViewById(R.id.tv_exchange_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ExChangeActivity.class);
                intent.putExtra("type","0");
                startActivity(intent);
            }
        });

        findViewById(R.id.tv_exchange_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ExChangeActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
            }
        });

        findViewById(R.id.rl_tixian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WithdrawCashActivity.class);
                intent.putExtra("allMoney",((TextView)findViewById(R.id.tv_name)).getText().toString());
                intent.putExtra("useMoney",((TextView)findViewById(R.id.use_money)).getText().toString());
                startActivity(intent);
            }
        });

    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText("资金管理");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pieChart = (PieChart) findViewById(R.id.consume_pie_chart);

    }

    private void initData() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.USER_ONFO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                try{
                MInfoEntity infoEntity = gson.fromJson(response, MInfoEntity.class);
                if (infoEntity.getRespCode().equals(PublicUtils.SUCCESS)) {
                    if (infoEntity.getAccount() == null) {
                        return;
                    }
                    ((TextView) findViewById(R.id.tv_name)).setText(PublicUtils.formatToDouble("" + infoEntity.getAccount().getAccountAmount()));
                    ((TextView) findViewById(R.id.use_money)).setText(PublicUtils.formatToDouble("" + infoEntity.getAccount().getAvailAmount()));
                    ((TextView) findViewById(R.id.tv_wait_money)).setText(PublicUtils.formatToDouble("" + infoEntity.getAccount().getFreezeAmount()));
                    ((TextView) findViewById(R.id.tv_get_money)).setText(PublicUtils.formatToDouble("" + infoEntity.getAccount().getRedPacket()));
                    initPia(infoEntity.getAccount());
                }
            }catch (Exception e){}
            }
        });
    }

    private void initPia(MInfoAccountEntity account) {

        float one  = (float) (account.getAvailAmount()/account.getAccountAmount()*100);
        float two = (float) (account.getFreezeAmount()/account.getAccountAmount()*100);
        float three = (float) (account.getRedPacket()/account.getAccountAmount()*100);

        pieChart.setUsePercentValues(false);
        pieChart.setDescriptionTextSize(20);
        pieChart.setDescription("");
        String centerText = PublicUtils.formatToDouble(""+account.getAccountAmount());
        pieChart.setCenterText(centerText);//设置中间的文字
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setRotationEnabled(true);
        pieChart.setTransparentCircleRadius(0f);
        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(one,"")); strings.add(new PieEntry(two,""));strings.add(new PieEntry(three,""));
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
