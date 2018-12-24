package com.youzheng.zhejiang.financebooking.UI.finance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.youzheng.zhejiang.financebooking.Model.home.BeanCate;
import com.youzheng.zhejiang.financebooking.Model.home.BeanCate2;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.UI.BaseFragment;
import com.youzheng.zhejiang.financebooking.Widget.CommonAdapter;
import com.youzheng.zhejiang.financebooking.Widget.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DetailsCategoryFragment extends BaseFragment {

    View view ;
    List<String> data = new ArrayList<>();
    ListView ls ;
    BeanCate type ;
    CommonAdapter<String> adapter ;
    int position ;


    public static DetailsCategoryFragment getInstance(BeanCate type) {
        DetailsCategoryFragment fragment = new DetailsCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.details_categroy_layout,null);
        initView();
        if (getArguments() != null) {
            type = (BeanCate) getArguments().getSerializable("type");
            if (type != null) {
                position = type.getPosition();
                if (type.getType().equals("four")){
                    data.add("全部");data.add("6.0-6.9%");data.add("7.0-7.9%");data.add("8.0%以上");
                }else if (type.getType().equals("three")){
                    data.add("全部");data.add("投资");data.add("已投满");data.add("还款中");
                }else if (type.getType().equals("one")){
                    data.add("全部");data.add("10-50万");data.add("50-100万");data.add("100-500万");data.add("500万以上");
                }else if (type.getType().equals("two")){
                    data.add("全部");data.add("30天以内");data.add("1-3个月");data.add("3-6个月");data.add("6个月以上");
                }
                initData();
            }
        }
        return view;
    }

    private void initData() {
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        ls = view.findViewById(R.id.cate_ls);
        adapter = new CommonAdapter<String>(mContext,data,R.layout.details_category_ls_item) {
            @Override
            public void convert(final ViewHolder helper, final String item) {
                helper.setText(R.id.tv_name,item);
                if (position==helper.getPosition()){
                    ((TextView)helper.getView(R.id.tv_name)).setTextColor(getResources().getColor(R.color.text_org));
                    ((ImageView)helper.getView(R.id.iv_icon)).setBackgroundResource(R.mipmap.group_9_1);
                }else {
                    ((TextView)helper.getView(R.id.tv_name)).setTextColor(getResources().getColor(R.color.text_drak_gray));
                    ((ImageView)helper.getView(R.id.iv_icon)).setBackgroundResource(R.mipmap.group_9_4);
                }
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new BeanCate2(type.getType(),helper.getPosition(),item));
                        view.findViewById(R.id.ll_show).setVisibility(View.GONE);
                    }
                });
            }
        };
        ls.setAdapter(adapter);
        view.findViewById(R.id.viee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.ll_show).setVisibility(View.GONE);
            }
        });
    }
}
