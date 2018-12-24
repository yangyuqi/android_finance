package com.youzheng.zhejiang.financebooking.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.google.gson.Gson;

public class BaseFragment extends Fragment {
    protected Context mContext;
    protected Gson gson ;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        gson = new Gson();
    }

}
