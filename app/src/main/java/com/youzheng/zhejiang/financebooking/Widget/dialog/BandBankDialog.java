package com.youzheng.zhejiang.financebooking.Widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.MLoginEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BandBankDialog extends Dialog {

    private Context context;
    private Integer mid ;

    public BandBankDialog(@NonNull Context context,Integer id) {
        super(context, R.style.DeleteDialogStyle);
        this.context = context;
        mid = id ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.band_bank_dialog_layout, null);
        setContentView(view);
        view.findViewById(R.id.tv_no_band).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.tv_right_band).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<>();
                map.put("id",mid);
                OkHttpClientManager.postAsynJson(new Gson().toJson(map), UrlUtils.DELETE_BANK_CARD, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        MLoginEntity loginEntity = new Gson().fromJson(response,MLoginEntity.class);
                        if (loginEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                            dismiss();
                        }
                    }
                });
            }
        });
    }
}
