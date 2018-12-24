package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.user.MFinanceStatusDataEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MFinanceStatusEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.CommonAdapter;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.ViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExChangeActivity extends BaseActivity {

    String type ;
    TabLayout tabLayout ;

    ListView ls ;
    CommonAdapter<MFinanceStatusDataEntity> adapter ;
    List<MFinanceStatusDataEntity> data = new ArrayList<>();
    int position = 0 ;
    private int pageSize = 20 ,pageIndex =1 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_change_layout);

        initView();

        initEvent();

        initData();
    }

    private void initData() {
        if (position==0){
            initDataOne();
        }else if (position==1){
            initDataTwo();
        }else if (position==2){
            initDataThree();
        }
    }

    private void initDataThree() {
        data.clear();
        Map<String,Object> map = new HashMap<>();
        map.put("pageSize",pageSize);
        map.put("pageIndex",pageIndex);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.APPLAY_MONEY, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MFinanceStatusEntity statusEntity = gson.fromJson(response,MFinanceStatusEntity.class);
                if (statusEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                    data.addAll(statusEntity.getData());
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initDataTwo() {
        data.clear();
        Map<String,Object> map = new HashMap<>();
        map.put("pageSize",pageSize);
        map.put("pageIndex",pageIndex);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.MEMBER_RECORD, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MFinanceStatusEntity statusEntity = gson.fromJson(response,MFinanceStatusEntity.class);
                if (statusEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                    data.addAll(statusEntity.getData());
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                if (tab.getPosition()==0){
                    ((TextView)findViewById(R.id.textHeadTitle)).setText("交易记录");
                    findViewById(R.id.ll_two).setVisibility(View.GONE);
                    findViewById(R.id.ll_three).setVisibility(View.GONE);
                    findViewById(R.id.ll_one).setVisibility(View.VISIBLE);
                }else if (tab.getPosition()==1){
                    ((TextView)findViewById(R.id.textHeadTitle)).setText("充值记录");
                    findViewById(R.id.ll_three).setVisibility(View.GONE);
                    findViewById(R.id.ll_one).setVisibility(View.GONE);
                    findViewById(R.id.ll_two).setVisibility(View.VISIBLE);
                }else if (tab.getPosition()==2){
                    ((TextView)findViewById(R.id.textHeadTitle)).setText("提现记录");
                    findViewById(R.id.ll_two).setVisibility(View.GONE);
                    findViewById(R.id.ll_one).setVisibility(View.GONE);
                    findViewById(R.id.ll_three).setVisibility(View.VISIBLE);
                }
                initData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tab);
        type = getIntent().getStringExtra("type");
        if (type.equals("2")){
            ((TextView)findViewById(R.id.textHeadTitle)).setText("提现记录");
            tabLayout.getTabAt(2).select();
            findViewById(R.id.ll_two).setVisibility(View.GONE);
            findViewById(R.id.ll_one).setVisibility(View.GONE);
            findViewById(R.id.ll_three).setVisibility(View.VISIBLE);
            position=2;
        }else if (type.equals("1")){
            ((TextView)findViewById(R.id.textHeadTitle)).setText("充值记录");
            tabLayout.getTabAt(1).select();
            findViewById(R.id.ll_three).setVisibility(View.GONE);
            findViewById(R.id.ll_one).setVisibility(View.GONE);
            findViewById(R.id.ll_two).setVisibility(View.VISIBLE);
            position=1;
        }else if (type.equals("0")){
            ((TextView)findViewById(R.id.textHeadTitle)).setText("交易记录");
            tabLayout.getTabAt(0).select();
            findViewById(R.id.ll_two).setVisibility(View.GONE);
            findViewById(R.id.ll_three).setVisibility(View.GONE);
            findViewById(R.id.ll_one).setVisibility(View.VISIBLE);
            position=0;
        }
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ls = (ListView) findViewById(R.id.ls_details);

        adapter = new CommonAdapter<MFinanceStatusDataEntity>(mContext,data,R.layout.exchange_ls_item) {
            @Override
            public void convert(ViewHolder helper, final MFinanceStatusDataEntity item) {
                if (position==0) {
                    helper.setText(R.id.tv_name, item.getProductName());
                    helper.setText(R.id.tv_money, PublicUtils.formatToDouble("" + item.getProductAccount()));
                    helper.setText(R.id.tv_status, PublicUtils.formatToDouble("" + item.getOrderAccount()));
                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,InvestmentDetailsActivity.class);
                            intent.putExtra("data",item);
                            startActivity(intent);
                        }
                    });
                }else if (position==2){
                    helper.setText(R.id.tv_name, item.getApplycashDate());
                    helper.setText(R.id.tv_money, PublicUtils.formatToDouble("" + item.getApplycashAmount()));
                    if (item.getApplycashStatus()==0) {
                        helper.setText(R.id.tv_status, "待打款");
                    }else if (item.getApplycashStatus()==1){
                        helper.setText(R.id.tv_status, "已打款");
                    }else if (item.getApplycashStatus()==2){
                        helper.setText(R.id.tv_status, "打款失败");
                    }
                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }else if (position==1){
                    helper.setText(R.id.tv_name, item.getRechargeDate());
                    helper.setText(R.id.tv_money, PublicUtils.formatToDouble("" + item.getRechargeAmount()));
                    helper.setText(R.id.tv_status, "充值成功");
                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }
        };
        ls.setAdapter(adapter);
    }

    private void initDataOne() {
        data.clear();
        Map<String,Object> map = new HashMap<>();
        map.put("pageSize",pageSize);
        map.put("pageIndex",pageIndex);
        map.put("financingStatus",null);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.FINANCE_MANAGE, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MFinanceStatusEntity statusEntity = gson.fromJson(response,MFinanceStatusEntity.class);
                if (statusEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                    data.addAll(statusEntity.getData());
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
