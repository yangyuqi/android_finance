package com.youzheng.zhejiang.financebooking.Widget.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import com.youzheng.zhejiang.financebooking.Model.home.MAdHomeDataBean;
import com.youzheng.zhejiang.financebooking.R;

import java.util.List;

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
        Glide.with(container.getContext()).load(banner_date.get(position).getAdImg()).error(R.mipmap.group_3_4).into(view);
        new_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doGo(container.getContext(),banner_date.get(position));
            }
        });
        return new_view;
    }

    private void doGo(Context context, MAdHomeDataBean mAdHomeDataBean) {

    }


    @Override
    public int getCount() {
        return banner_date.size();
    }
}