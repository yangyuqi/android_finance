package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class MyMortgageActivity extends BaseActivity {

    private int pageSize = 100,pageIndex =1;

    ListView ls ;
    CommonAdapter<MMyFinanceActivityDataEntity> adapter ;
    List<MMyFinanceActivityDataEntity> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_mortgage_layout);

        ((TextView)findViewById(R.id.textHeadTitle)).setText("我的抵押");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ls = (ListView) findViewById(R.id.ls_details);
        adapter = new CommonAdapter<MMyFinanceActivityDataEntity>(mContext,data,R.layout.exchange_ls_item) {
            @Override
            public void convert(ViewHolder helper, final MMyFinanceActivityDataEntity item) {
                    helper.setText(R.id.tv_money, item.getFinancing_data());
                    helper.setText(R.id.tv_name,item.getApplicantDate());
                    try {
                        if (item.getAssets_status()==1){
                            helper.setText(R.id.tv_status,"抵押中");
                        }else if (item.getAssets_status()==2){
                            helper.setText(R.id.tv_status,"已出库");
                        }
                    }catch (Exception e){}


                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Intent intent = new Intent(mContext,MortgageFinanceActivity.class);
                            intent.putExtra("type","2");
                            intent.putExtra("data",item);
                            startActivity(intent);
                    }
                });

            }
        };
        ls.setAdapter(adapter);

        initNewData();
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

}
