package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.content.Intent;
import android.net.Uri;
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

public class AllInvestmentActivity extends BaseActivity {

    String type ;
    TabLayout tabLayout ;

    ListView ls ;
    CommonAdapter<MFinanceStatusDataEntity> adapter ;
    List<MFinanceStatusDataEntity> data = new ArrayList<>();

    private Integer financingStatus ,pageSize =  50,pageIndex = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_investment_layout);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("pageSize",pageSize);
        map.put("pageIndex",pageIndex);
        map.put("financingStatus",financingStatus);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.FINANCE_MANAGE, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MFinanceStatusEntity statusEntity = gson.fromJson(response,MFinanceStatusEntity.class);
                if (statusEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                    data.clear();
                    data.addAll(statusEntity.getData());
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tab);
        type = getIntent().getStringExtra("type");
        if (type.equals("2")){
            tabLayout.getTabAt(2).select();
            financingStatus = 3 ;
        }else if (type.equals("1")){
            tabLayout.getTabAt(1).select();
            financingStatus = 2 ;
        }else if (type.equals("0")){
            tabLayout.getTabAt(0).select();
            financingStatus = null ;
        }else if (type.equals("3")){
            tabLayout.getTabAt(3).select();
            financingStatus = 5 ;
        }
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView)findViewById(R.id.textHeadTitle)).setText("所有投资");


        ls = (ListView) findViewById(R.id.ls_details);

        adapter = new CommonAdapter<MFinanceStatusDataEntity>(mContext,data,R.layout.exchange_ls_item) {
            @Override
            public void convert(final ViewHolder helper, final MFinanceStatusDataEntity item) {
                helper.setText(R.id.tv_name,item.getProductName());
                helper.setText(R.id.tv_money,PublicUtils.formatToDouble(""+item.getProductAccount()));
                helper.setText(R.id.tv_status,PublicUtils.formatToDouble(""+item.getOrderAccount()));

                if (item.getOrderStatus()==1){ //待支付
                    helper.getView(R.id.iv_book).setVisibility(View.GONE);
                }else { //已支付
                    helper.getView(R.id.iv_book).setVisibility(View.VISIBLE);
                }
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         if (helper.getView(R.id.iv_book).getVisibility()==View.VISIBLE){
                             Intent intent = new Intent(mContext,InvestmentDetailsActivity.class);
                             intent.putExtra("data",item);
                             intent.putExtra("type",1);
                             startActivity(intent);
                         }else {
                             Intent intent = new Intent(mContext,InvestmentDetailsActivity.class);
                             intent.putExtra("data",item);
                             intent.putExtra("type",2);
                             startActivity(intent);
                         }


                    }
                });


                helper.getView(R.id.ll_book).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("http://192.168.2.217:8088/"+item.getContract());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }
        };
        ls.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    financingStatus = null ;
                }else if (tab.getPosition()==1){
                    financingStatus = 2 ;
                }else if (tab.getPosition()==2){
                    financingStatus = 3 ;
                }else if (tab.getPosition()==3){
                    financingStatus = 5 ;
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
}
