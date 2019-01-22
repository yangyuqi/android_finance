package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.youzheng.zhejiang.financebooking.Model.user.AccountOrConsultingEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MInfoEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.UI.My.OnRecyclerViewAdapterItemClickListener;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.View.RecycleViewDivider;
import com.youzheng.zhejiang.financebooking.Widget.adapter.AccountOrConsultingAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountOrConsultingActivity extends BaseActivity implements View.OnClickListener, OnRecyclerViewAdapterItemClickListener {

    private ImageView btnBack;
    /**
     * Smart
     */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView top_header_ll_right_iv_caidan;
    private RelativeLayout layout_header;
    private PullLoadMoreRecyclerView pr_list;
    private int page=1;
    private int pageSize=30;
    private int type;
    private List<AccountOrConsultingEntity.DataBean> list=new ArrayList<>();
    private AccountOrConsultingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_or_consulting);
        type=getIntent().getIntExtra("type",0);
        initView();
        setListener();
    }

    private void setListener() {
        pr_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                page=1;
                list.clear();
                initData(page,pageSize,type);

            }

            @Override
            public void onLoadMore() {
                page++;
                initData(page,pageSize,type);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        initData(page,pageSize,type);
    }

    private void initData(int page,int pageSize,int type) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("pageIndex",page);
        map.put("pageSize",pageSize);
        map.put("type",type);

        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ACCOUNT_OR_CONSULTING, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                   pr_list.setPullLoadMoreCompleted();
            }

            @Override
            public void onResponse(String response) {
                pr_list.setPullLoadMoreCompleted();
                AccountOrConsultingEntity consultingEntity = gson.fromJson(response,AccountOrConsultingEntity.class);
                if (consultingEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                   setData(consultingEntity);
                }
            }
        });



    }

    private void setData(AccountOrConsultingEntity consultingEntity) {
            if (consultingEntity.getData()==null) return;

            List<AccountOrConsultingEntity.DataBean> beans=consultingEntity.getData();
            if (beans.size()!=0){
                list.addAll(beans);
                adapter.setUI(beans);
            }else {
                showToast(getString(R.string.no_more_data));
            }
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        if (type==1){
            textHeadTitle.setText(getString(R.string.consulting));
        }else {
            textHeadTitle.setText(getString(R.string.account_dong));
        }

        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        top_header_ll_right_iv_caidan = (ImageView) findViewById(R.id.top_header_ll_right_iv_caidan);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        pr_list = (PullLoadMoreRecyclerView) findViewById(R.id.pr_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pr_list.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 5, getResources().getColor(R.color.bg_background)));
        pr_list.setLinearLayout();
        pr_list.setColorSchemeResources(R.color.colorPrimary);

        adapter=new AccountOrConsultingAdapter(list,this);
        pr_list.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent(this,ConsultingDetailActivity.class);
        intent.putExtra("title",list.get(position).getInfoTitle());
        intent.putExtra("web_html",list.get(position).getInfoText());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
