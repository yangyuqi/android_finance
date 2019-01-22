package com.youzheng.zhejiang.financebooking.Widget.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.MLoginEntity;
import com.youzheng.zhejiang.financebooking.Model.home.ADBeanDatas;
import com.youzheng.zhejiang.financebooking.Model.home.MAdHomeDataBean;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.Home.FinaceProjectDetailsActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.ConsultingDetailActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qiuweiyu on 2018/2/7.
 */

public class BannerNormalAdapter extends StaticPagerAdapter {

    private List<MAdHomeDataBean> banner_date;
    private String accessToken ;

    public BannerNormalAdapter(List<MAdHomeDataBean> entity, String accessToken) {
        banner_date = entity;
        this.accessToken = accessToken ;
    }

    @Override
    public View getView(final ViewGroup container, final int position) {
        View new_view = LayoutInflater.from(container.getContext()).inflate(R.layout.image_new_layout,null);
        ImageView view = (ImageView) new_view.findViewById(R.id.iv_new);
        Glide.with(container.getContext()).load("http://image.quzhoumr.com/"+banner_date.get(position).getAdImg()).error(R.mipmap.group_3_4).into(view);
        new_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                doGo(container.getContext(),banner_date.get(position));
            }
        });
        return new_view;
    }

    private void doGo(final Context context, final MAdHomeDataBean mAdHomeDataBean) {
        final Intent intent = new Intent(context, ConsultingDetailActivity.class);
        if (mAdHomeDataBean.getAdTypeinfo()!=null){
            try {
                final Map<String,Object> map = new HashMap<>();
                if (mAdHomeDataBean.getAdType()==1) {
                    map.put("id", "63");
                    comMethon(map,intent,context ,mAdHomeDataBean);
                }else if (mAdHomeDataBean.getAdType()==2){
                    map.put("id","65");
                    comMethon(map,intent,context,mAdHomeDataBean);
                }else if (mAdHomeDataBean.getAdType()==3){
                    Intent intent1 = new Intent(context, FinaceProjectDetailsActivity.class);
                    intent1.putExtra("id",33);
                    context.startActivity(intent1);
                }else if (mAdHomeDataBean.getAdType()==4){
                    Uri uri = Uri.parse("");
                    Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent2);
                }
            }catch (Exception e){

            }
        }


    }


    public void comMethon(Map<String, Object> map ,final Intent intent ,final Context context ,final MAdHomeDataBean mAdHomeDataBean){
        OkHttpClientManager.postAsynJson(new Gson().toJson(map), UrlUtils.AD_DETAOLS, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MLoginEntity mLoginEntity = new Gson().fromJson(response,MLoginEntity.class);
                if (mLoginEntity.getRespCode().equals("1000")){
                    ADBeanDatas datas = new Gson().fromJson(new Gson().toJson(mLoginEntity.getData()),ADBeanDatas.class);
                    intent.putExtra("web_html",datas.getInfoText());
                    intent.putExtra("title",mAdHomeDataBean.getAdTitle());
                    context.startActivity(intent);

                }
            }
        });

    }

    @Override
    public int getCount() {
        return banner_date.size();
    }
}