package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.user.MMyFinanceActivityDataEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MMyFinanceActivityEntity;
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

public class MyFinanceActivity extends BaseActivity {

    TabLayout tabLayout ;

    ListView ls ;
    CommonAdapter<MMyFinanceActivityDataEntity> adapter ;
    List<MMyFinanceActivityDataEntity> data = new ArrayList<>();

    int position = 0 ,pageSize = 100,pageIndex =1;

    private String type ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_finance_layout);
        type = getIntent().getStringExtra("type");
        initView();

        initEvent();

        if (type==null) {
            initData(1);
        }else {
            position=1;
            initData(2);
        }
    }

    private void initNewData() {
        Map<String,Object> map = new HashMap<>();
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        map.put("type","1");
        map.put("assetStatus","1");
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ALL_INVERST, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MMyFinanceActivityEntity activityEntity = gson.fromJson(response,MMyFinanceActivityEntity.class);
                if (activityEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                    data.clear();
                    data.addAll(activityEntity.getData());
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initData(int position) {
        Map<String,Object> map = new HashMap<>();
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        map.put("type",position);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GET_MY_FONANCE, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MMyFinanceActivityEntity activityEntity = gson.fromJson(response,MMyFinanceActivityEntity.class);
                if (activityEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                    data.clear();
                    data.addAll(activityEntity.getData());
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText("我的融资");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tab);
        if (type==null) {
            tabLayout.getTabAt(0).select();
            position=0;
        }else {
            tabLayout.getTabAt(1).select();
            position=1;
        }

        ls = (ListView) findViewById(R.id.ls_details);

        adapter = new CommonAdapter<MMyFinanceActivityDataEntity>(mContext,data,R.layout.exchange_ls_item) {
            @Override
            public void convert(ViewHolder helper, final MMyFinanceActivityDataEntity item) {
                    helper.setText(R.id.tv_name,item.getApplicant_date());
                    helper.setText(R.id.tv_money, PublicUtils.formatToDouble("" + item.getApplicant_financing_amount()));
//                    TextView tv_status = helper.getView(R.id.tv_status);
//                    tv_status.setTextColor(R.color.);
                    if (item.getApply_financing_status()==1){
                        helper.setText(R.id.tv_status,"申请中");
                    }else if (item.getApply_financing_status()==2){
                        helper.setText(R.id.tv_status,"初审通过");
                    }else if (item.getApply_financing_status()==3){
                        helper.setText(R.id.tv_status,"初审失败");
                    }else if (item.getApply_financing_status()==4){
                        helper.setText(R.id.tv_status,"审核通过");
                    }else if (item.getApply_financing_status()==5){
                        helper.setText(R.id.tv_status,"审核失败");
                    }else if (item.getApply_financing_status()==6){
                        helper.setText(R.id.tv_status,"还款中");
                    }else if (item.getApply_financing_status()==7){
                        helper.setText(R.id.tv_status,"已还款");
                    }


                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Intent intent = new Intent(mContext,MortgageFinanceActivity.class);
                            intent.putExtra("type","1");
                            intent.putExtra("data",item);
                            startActivity(intent);
                    }
                });

            }
        };
        ls.setAdapter(adapter);
    }

    private void initEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    position = tab.getPosition();
                    ((TextView)findViewById(R.id.textHeadTitle)).setText("我的融资");
                    findViewById(R.id.ll_two).setVisibility(View.GONE);
                    findViewById(R.id.ll_one).setVisibility(View.VISIBLE);
                    if (position==0) {
                        initData(1);
                    }else {
                        initData(2);
                    }
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
