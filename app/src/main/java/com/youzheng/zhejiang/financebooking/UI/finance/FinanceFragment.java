package com.youzheng.zhejiang.financebooking.UI.finance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.home.BeanCate;
import com.youzheng.zhejiang.financebooking.Model.home.BeanCate2;
import com.youzheng.zhejiang.financebooking.Model.home.MHomeDetailsEntity;
import com.youzheng.zhejiang.financebooking.Model.home.MHomeEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseFragment;
import com.youzheng.zhejiang.financebooking.UI.Home.FinaceProjectDetailsActivity;
import com.youzheng.zhejiang.financebooking.Widget.CommonAdapter;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinanceFragment extends BaseFragment {

    ListView ls ;
    View view ;
    RadioButton jiantou_footbar_user ,jiantou_bar_home,jiantou_footbar_four,jiantou_bar_im;
    protected FragmentManager fm;
    TabLayout tab ;

    List<MHomeDetailsEntity> data = new ArrayList<>();
    CommonAdapter<MHomeDetailsEntity> adapter ;
    int position1 ,position2,position3,position4 ;
    Map<String,Object> map = new HashMap<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onEvent(BeanCate2 beanCate){
        if (beanCate.getType().equals("one")){
            position1=beanCate.getPosition();
            if (beanCate.getPosition()!=0) {
                jiantou_bar_home.setText(beanCate.getName());
                if (beanCate.getName().equals("500万以上")) {
                    map.put("minAccount", "500");
                }else {
                    String[] strings = beanCate.getName().substring(0, beanCate.getName().length() - 1).split("-");
                    map.put("minAccount", strings[0]);
                    map.put("maxAccount", strings[1]);
                }
            }else {
                jiantou_bar_home.setText("产品金额");
                if (map.containsKey("minAccount")){
                    map.remove("minAccount");
                }
                if (map.containsKey("maxAccount")){
                    map.remove("maxAccount");
                }
            }
        }
        if (beanCate.getType().equals("two")){
            position2=beanCate.getPosition();
            if (beanCate.getPosition()!=0) {
                jiantou_bar_im.setText(beanCate.getName());
                if (beanCate.getName().equals("30天以内")){
                    map.put("maxTimeLimit","30");
                }else if (beanCate.getName().equals("6个月以上")){
                    map.put("minTimeLimit","6");
                }else {
                    String [] strings = beanCate.getName().substring(0,beanCate.getName().length()-2).split("-");
                    map.put("minTimeLimit",strings[0]);
                    map.put("maxTimeLimit",strings[1]);
                }
            }else {
                jiantou_bar_im.setText("产品期限");
                if (map.containsKey("minTimeLimit")){
                    map.remove("minTimeLimit");
                }
                if (map.containsKey("maxTimeLimit")){
                    map.remove("maxTimeLimit");
                }
            }
        }
        if (beanCate.getType().equals("three")){
            position3=beanCate.getPosition();
            if (beanCate.getPosition()!=0) {
                jiantou_footbar_user.setText(beanCate.getName());
                if (beanCate.getName().equals("投资")){
                    map.put("productStatus","3");
                }else if (beanCate.getName().equals("已投满")){
                    map.put("productStatus","5");
                }else if (beanCate.getName().equals("还款中")){
                    map.put("productStatus","4");
                }
            }else {
                jiantou_footbar_user.setText("产品状态");
                if (map.containsKey("productStatus")){
                    map.remove("productStatus");
                }
            }
        }
        if (beanCate.getType().equals("four")){
            position4=beanCate.getPosition();
            if (beanCate.getPosition()!=0) {
                jiantou_footbar_four.setText(beanCate.getName());
                if (beanCate.getName().equals("8.0%以上")){
                    map.put("maxProductAccrual", "8.0");
                }else {
                    String[] strings = beanCate.getName().substring(0, beanCate.getName().length() - 1).split("-");
                    map.put("minProductAccrual", strings[0]);
                    map.put("maxProductAccrual", strings[1]);
                }
            }else {
                jiantou_footbar_four.setText("年化收益");
                if (map.containsKey("minProductAccrual")){
                    map.remove("minProductAccrual");
                }
                if (map.containsKey("maxProductAccrual")){
                    map.remove("maxProductAccrual");
                }
            }
        }
        initData(map);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.finance_layout,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        fm = getFragmentManager();
        tab = view.findViewById(R.id.tab);
        jiantou_bar_im = view.findViewById(R.id.jiantou_bar_im);
        jiantou_bar_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction1 = fm.beginTransaction();
                transaction1.replace(R.id.fl_content, DetailsCategoryFragment.getInstance(new BeanCate("two",position2))).commit();
            }
        });
        jiantou_bar_home = view.findViewById(R.id.jiantou_bar_home);
        jiantou_bar_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction1 = fm.beginTransaction();
                transaction1.replace(R.id.fl_content, DetailsCategoryFragment.getInstance(new BeanCate("one",position1))).commit();
            }
        });
        jiantou_footbar_four = view.findViewById(R.id.jiantou_footbar_four);
        jiantou_footbar_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction1 = fm.beginTransaction();
                transaction1.replace(R.id.fl_content, DetailsCategoryFragment.getInstance(new BeanCate("four",position4))).commit();
            }
        });
        jiantou_footbar_user = view.findViewById(R.id.jiantou_footbar_user);
        jiantou_footbar_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction1 = fm.beginTransaction();
                transaction1.replace(R.id.fl_content, DetailsCategoryFragment.getInstance(new BeanCate("three",position3))).commit();
            }
        });
        ls = view.findViewById(R.id.ls);
        ls.setFocusable(false);
        adapter = new CommonAdapter<MHomeDetailsEntity>(mContext,data,R.layout.home_page_ls_item) {
            @Override
            public void convert(ViewHolder helper, final MHomeDetailsEntity item) {
                helper.setText(R.id.tv_name,item.getProductName());
                helper.setText(R.id.tv_productAccural,""+PublicUtils.formatToDouble(""+item.getProductAccural())+"%");
                helper.setText(R.id.tv_productCycle,""+item.getProductCycle());
                Double pay ;
                if (item.getSubscribeMin()==null){
                    pay = 0.00;
                }else {
                    pay = item.getSubscribeMin();
                }
                helper.setText(R.id.tv_can_use,"剩余可投"+PublicUtils.formatToDouble(""+item.getResidueAccount())+"，  "+PublicUtils.formatToDouble(""+pay)+"元起投");
                ImageView imageView = helper.getView(R.id.iv_show);
                if (item.getProductStatus()==4||item.getProductStatus()==5){
                    imageView.setVisibility(View.VISIBLE);
                }else {
                    imageView.setVisibility(View.GONE);
                }
                ProgressBar seekBar = helper.getView(R.id.seek_bar);
                int p =  (int)item.getBuyStatus();
                seekBar.setProgress(p);
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,FinaceProjectDetailsActivity.class) ;
                        intent.putExtra("id",item.getId());
                        startActivity(intent);
                    }
                });
            }
        };
        ls.setAdapter(adapter);
        map.put("productType",2);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addListener();
    }

    private void addListener() {
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    map.put("productType",2);
                }else if (tab.getPosition()==1){
                    map.put("productType",3);
                }
                initData(map);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        map.put("pageIndex",1);
        map.put("pageSize",40);
        initData(map);
    }

    private void initData(Object o) {
        data.clear();
        OkHttpClientManager.postAsynJson(gson.toJson(o), UrlUtils.NEW_INVEST, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MHomeEntity entity = gson.fromJson(response,MHomeEntity.class);
                if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                    if (entity.getData().size()>0){
                        data.addAll(entity.getData());
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
