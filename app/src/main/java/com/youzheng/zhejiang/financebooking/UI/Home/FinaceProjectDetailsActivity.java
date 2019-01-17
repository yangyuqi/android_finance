package com.youzheng.zhejiang.financebooking.UI.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.home.MFinanceEntity;
import com.youzheng.zhejiang.financebooking.Model.home.MHomeEntity;
import com.youzheng.zhejiang.financebooking.Model.home.ProjectstateDataEntity;
import com.youzheng.zhejiang.financebooking.Model.home.ProjectstateEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.LoginActivity;
import com.youzheng.zhejiang.financebooking.Widget.CommonAdapter;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.ViewHolder;
import com.youzheng.zhejiang.financebooking.Widget.dialog.GoFinanceDialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FinaceProjectDetailsActivity extends BaseActivity {

    TextView tv_go_finance  ;
    TabLayout tab ;
    int id ,iiiiid ;
    LinearLayout ll_content ;

    String productManual ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_project_details_layout);
        initView();
        initPosition(0);
        initData();
    }

    private void initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.HOME_DETAILS, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                final MFinanceEntity entity = gson.fromJson(response,MFinanceEntity.class);
                if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                    ProgressBar seekBar = (ProgressBar) findViewById(R.id.seek_bar);
                    int p =  (int)entity.getData().getBuyStatus();
                    seekBar.setProgress(p);
                    productManual = entity.getData().getProductManual();
                    ((TextView)findViewById(R.id.tv_name)).setText(entity.getData().getProductName());
                    ((TextView)findViewById(R.id.tv_percent)).setText(PublicUtils.formatToDouble(""+entity.getData().getProductAccural())+"%");
                    ((TextView)findViewById(R.id.tv_time)).setText(""+entity.getData().getProductCycle());
                    ((TextView)findViewById(R.id.tv_get_finance)).setText(""+entity.getData().getProductCycle()+"天");
                    ((TextView)findViewById(R.id.tv_get_data)).setText(entity.getData().getProductEnddate());
                    ((TextView)findViewById(R.id.tv_money_get)).setText("T+2(工作日)");
                    ((TextView)findViewById(R.id.tv_start_meney)).setText(PublicUtils.formatToDouble(""+entity.getData().getSubscribeMin()));
                    ((TextView)findViewById(R.id.tv_baodan)).setText(entity.getData().getRiskGuarantee());
                    ((TextView)findViewById(R.id.tv_test)).setText(PublicUtils.formatToDouble(""+entity.getData().getProductAccount())+"元");
                    ((TextView)findViewById(R.id.tv_test_one)).setText(PublicUtils.formatToDouble(""+entity.getData().getResidueAccount()));
                    if (entity.getData().getProductStatus()==4||entity.getData().getProductStatus()==5){
                        tv_go_finance.setBackgroundResource(R.color.text_gray);
                        tv_go_finance.setEnabled(false);
                    }
                    iiiiid = entity.getData().getId();
                    final String accessToken = (String) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.token,"");
                    tv_go_finance.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!accessToken.equals("")) {
                                    GoFinanceDialog dialog = new GoFinanceDialog(mContext, entity.getData().getId());
                                    dialog.show();
                                }else {
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                }
                            }
                        });

                }
            }
        });
    }

    private void initView() {
        id = getIntent().getIntExtra("id",0);
        ((TextView)findViewById(R.id.textHeadTitle)).setText("理财管理");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tab = (TabLayout) findViewById(R.id.tab);
        tv_go_finance = (TextView) findViewById(R.id.tv_go_finance);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initPosition(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initPosition(int position) {
        if (position==0){
            ll_content.removeAllViews();
            View view = LayoutInflater.from(mContext).inflate(R.layout.project_state_layout,null);
            TextView textView = view.findViewById(R.id.tv_introduce);
            textView.setText(((TextView)findViewById(R.id.tv_name)).getText().toString()+"说明书");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(mContext,H5Activity.class);
//                    intent.putExtra("url","http://image.ughen.com/"+productManual);
//                    startActivity(intent);
                    Uri uri = Uri.parse("http://image.ughen.com/"+productManual);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
            ll_content.addView(view);
        }else if (position==1){
            ll_content.removeAllViews();
            View view = LayoutInflater.from(mContext).inflate(R.layout.project_state_layout_two,null);
            ll_content.addView(view);
//        }else if (position==2){
//            ll_content.removeAllViews();
//            View view = LayoutInflater.from(mContext).inflate(R.layout.project_state_layout_three,null);
//            ll_content.addView(view);
        }else if (position==2){
            ll_content.removeAllViews();
            View view = LayoutInflater.from(mContext).inflate(R.layout.project_state_layout_four,null);
            TextView textView = view.findViewById(R.id.tv_all);
            final TextView tv_num = view.findViewById(R.id.tv_num);
            textView.setText("投标总额:"+((TextView)findViewById(R.id.tv_test)).getText().toString());
            final ListView listView = view.findViewById(R.id.ls_bottom);
            Map<String,Object> map= new HashMap<>();
            map.put("pageIndex",1);
            map.put("pageSize",100);
            map.put("id",id);
            OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.TOUZI_RECODE, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    CommonAdapter<ProjectstateDataEntity> madapter ;
                    ProjectstateEntity projectstateEntity = gson.fromJson(response,ProjectstateEntity.class);
                    if (projectstateEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                        if (projectstateEntity.getData().size()>0){
                            tv_num.setText("加入人数: "+projectstateEntity.getData().size()+"人");
                            madapter = new CommonAdapter<ProjectstateDataEntity>(mContext,projectstateEntity.getData(),R.layout.project_ls_four_item) {
                                @Override
                                public void convert(ViewHolder helper, ProjectstateDataEntity item) {
                                    helper.setText(R.id.tv_one,""+(helper.getPosition()+1));
                                    if (item.getBidder()==null){
                                        helper.setText(R.id.tv_two,"匿名");
                                    }else {
                                        helper.setText(R.id.tv_two,item.getBidder());
                                    }
                                    helper.setText(R.id.tv_three,item.getEffectiveDate());
                                    helper.setText(R.id.tv_four,PublicUtils.formatToDouble(""+item.getOrderAccount()));
                                }
                            };
                            listView.setAdapter(madapter);
                        }
                    }
                }
            });
            ll_content.addView(view);
        }
    }
}
