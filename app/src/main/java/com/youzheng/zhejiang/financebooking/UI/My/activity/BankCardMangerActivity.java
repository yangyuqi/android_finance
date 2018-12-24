package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.user.MBankCardsDetailsEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MBankCardsEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.CommonAdapter;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.ViewHolder;
import com.youzheng.zhejiang.financebooking.Widget.common.PublishUtils;
import com.youzheng.zhejiang.financebooking.Widget.dialog.BandBankDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BankCardMangerActivity extends BaseActivity {

    ListView ls ;
    List<MBankCardsDetailsEntity> data = new ArrayList<>();
    CommonAdapter<MBankCardsDetailsEntity> adapter ;

    int widWidth ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_card_manger_layout);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        widWidth = outMetrics.widthPixels;

        initView();

        initData();
    }

    private void initData() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.GET_CARD_LIST, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MBankCardsEntity cardsEntity = gson.fromJson(response,MBankCardsEntity.class);
                if (cardsEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                    data.addAll(cardsEntity.getData());
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText("银行卡管理");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.top_header_ll_right_iv_caidan).setVisibility(View.VISIBLE);

        ls = (ListView) findViewById(R.id.ls);
        adapter = new CommonAdapter<MBankCardsDetailsEntity>(mContext,data,R.layout.bank_manager_ls_item) {
            @Override
            public void convert(ViewHolder helper, final MBankCardsDetailsEntity item) {
                helper.setText(R.id.tv_bank_num,item.getBankCard());
                helper.setText(R.id.tv_bank_name,item.getOpenBank());
                RelativeLayout view = helper.getView(R.id.rl_view);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                params.width = (int) PublishUtils.dip2px(PublishUtils.px2dip(widWidth)-30);
                view.setLayoutParams(params);

                helper.getView(R.id.main_right_drawer_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new BandBankDialog(mContext,item.getId()).show();
                    }
                });

                helper.getView(R.id.hor_sv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,BankCardDetailsActivity.class);
                        intent.putExtra("id",item.getId());
                        intent.putExtra("mId",item.getmId());
                        startActivity(intent);
                    }
                });
            }
        };
        ls.setAdapter(adapter);

        findViewById(R.id.top_header_ll_right_iv_caidan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,AddBankCardActivity.class));
            }
        });
    }
}
