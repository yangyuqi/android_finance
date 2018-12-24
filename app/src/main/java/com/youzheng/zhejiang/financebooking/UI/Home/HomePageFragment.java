package com.youzheng.zhejiang.financebooking.UI.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.jude.rollviewpager.RollPagerView;
import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.home.MAdHomeData;
import com.youzheng.zhejiang.financebooking.Model.home.MAdHomeDataBean;
import com.youzheng.zhejiang.financebooking.Model.home.MHomeDetailsEntity;
import com.youzheng.zhejiang.financebooking.Model.home.MHomeEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseFragment;
import com.youzheng.zhejiang.financebooking.Widget.CommonAdapter;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.View.NoScrollListView;
import com.youzheng.zhejiang.financebooking.Widget.ViewHolder;
import com.youzheng.zhejiang.financebooking.Widget.adapter.BannerNormalAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePageFragment extends BaseFragment {

    RollPagerView roll_view ;
    View view ;
    NoScrollListView ls ;
    CommonAdapter<MHomeDetailsEntity> adapter ;
    List<MHomeDetailsEntity> data = new ArrayList<>();

    List<MAdHomeDataBean> beanList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_page_fragment,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
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

        roll_view = view.findViewById(R.id.roll_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initData();
        initAd();
    }

    private void initAd() {
        Map<String,Object> map = new HashMap<>();
        map.put("terminal","mobile");
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.ADVERSTION_INFO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MAdHomeData data = gson.fromJson(response,MAdHomeData.class);
                if (data.getRespCode().equals(PublicUtils.SUCCESS)){
                    if (data.getData().size()>0){
                        roll_view.setAdapter(new BannerNormalAdapter(data.getData(), (String) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.token,"")));
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.HOME_INTRO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MHomeEntity entity = gson.fromJson(response,MHomeEntity.class);
                if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                    if (entity.getData().size()>0){
                        data.clear();
                        data.addAll(entity.getData());
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
}
