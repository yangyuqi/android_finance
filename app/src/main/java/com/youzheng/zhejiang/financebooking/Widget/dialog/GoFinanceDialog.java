package com.youzheng.zhejiang.financebooking.Widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.MLoginEntity;
import com.youzheng.zhejiang.financebooking.Model.home.MAvailMoneyEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.View.MyCountDownTimer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GoFinanceDialog extends Dialog {

    private Context context;
    private int productId ;
    private TextView tv_phone ;
    private Double availMoney ,productAvailAmount ,minInvest , maxInvest ;
    private MyCountDownTimer timer ;
    private TextView tv_confrim ;
    private EditText edt_code ,edt_money ;
    String productType ;
    public GoFinanceDialog(@NonNull Context context ,int id ,String type) {
        super(context, R.style.DeleteDialogStyle);
        this.context = context;
        this.productId = id ;
        this.productType = type;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.go_finance_dialog_layout, null);
        setContentView(view);
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_phone = view.findViewById(R.id.tv_phone);
        tv_confrim = view.findViewById(R.id.tv_confrim);
        edt_money  = view.findViewById(R.id.edt_money);
        edt_code = view.findViewById(R.id.edt_code);
        timer = new MyCountDownTimer((Button) view.findViewById(R.id.btn_send),60000,1000);
        view.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_phone.getText().toString().length()!=11){
                    Toast.makeText(context,"请输入正确手机号",Toast.LENGTH_SHORT).show();
                    return;
                }
                timer.start();
                initCode();
            }
        });
        initData();
    }

    private void initCode() {
        Map<String,Object> map = new HashMap<>();
        map.put("regPhone",tv_phone.getText().toString());
        map.put("type","6");
        OkHttpClientManager.postAsynJson(new Gson().toJson(map), UrlUtils.SEND_CODE_ALL, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MLoginEntity entity = new Gson().fromJson(response, MLoginEntity.class);
                Toast.makeText(context,PublicUtils.getMsg(entity.getRespCode()),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        String phone = (String) SharedPreferencesUtils.getParam(context,SharedPreferencesUtils.userName,"");
        Map<String,Object> map = new HashMap<>();
        map.put("productId", productId);
        if (!phone.equals("")) {
            map.put("phone",phone);
        }
        String u = null;
        if (productType.equals("3")){
            u = UrlUtils.AVAIL_MONEY;
        }else {
            u = UrlUtils.AVAIL_MONEY1;
        }

        OkHttpClientManager.postAsynJson(new Gson().toJson(map), u, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MAvailMoneyEntity entity = new Gson().fromJson(response,MAvailMoneyEntity.class);
                if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                    maxInvest = entity.getMaxInvest();
                    minInvest = entity.getMinInvest();
                    availMoney = entity.getAvailMoney();
                    tv_phone.setText(entity.getPhone());

                    tv_confrim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (edt_code.getText().toString().equals("")){
                                Toast.makeText(context,"请输入验证码",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (edt_money.getText().toString().equals("")){
                                Toast.makeText(context,"请输入金额",Toast.LENGTH_SHORT).show();
                                return;
                            }

                            int isAuth = (int) SharedPreferencesUtils.getParam(context,SharedPreferencesUtils.isAuth,0);
                            if (isAuth==2) {
                                if (Double.parseDouble(edt_money.getText().toString()) <= maxInvest && Double.parseDouble(edt_money.getText().toString()) >= minInvest) {

                                    if (Double.parseDouble(edt_money.getText().toString()) <= availMoney) {
                                        Map<String, Object> objectMap = new HashMap<>();
                                        objectMap.put("investMoney", edt_money.getText().toString());
                                        objectMap.put("code", edt_code.getText().toString());
                                        objectMap.put("productId", productId);
                                        objectMap.put("terminal", "Android");
                                        objectMap.put("productType",productType);
                                        String url ;
                                        if (productType.equals("3")){
                                            url = UrlUtils.READY_COMMINT_JIEKOU;
                                        }else {
                                            url =  UrlUtils.TOUZI_JIEKOU ;
                                        }

                                        OkHttpClientManager.postAsynJson(new Gson().toJson(objectMap), url, new OkHttpClientManager.StringCallback() {
                                            @Override
                                            public void onFailure(Request request, IOException e) {

                                            }

                                            @Override
                                            public void onResponse(String response) {
                                                MLoginEntity entity = new Gson().fromJson(response, MLoginEntity.class);
                                                Toast.makeText(context, PublicUtils.getMsg(entity.getRespCode()), Toast.LENGTH_SHORT).show();
                                                if (entity.getRespCode().equals(PublicUtils.SUCCESS)) {
                                                    dismiss();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(context, "由于您的账户余额不足,无法完成支付操作 ! 如确认下单 ,请于24小时内充值完成支付 !", Toast.LENGTH_SHORT).show();
                                        Map<String, Object> _map = new HashMap<>();
                                        _map.put("investMoney", edt_money.getText().toString());
                                        _map.put("code", edt_code.getText().toString());
                                        _map.put("productId", productId);
                                        _map.put("terminal", "Android");
                                        _map.put("productType",productType);
                                        OkHttpClientManager.postAsynJson(new Gson().toJson(_map), UrlUtils.COMMINT_JIEKOU, new OkHttpClientManager.StringCallback() {
                                            @Override
                                            public void onFailure(Request request, IOException e) {

                                            }

                                            @Override
                                            public void onResponse(String response) {
                                                MLoginEntity entity = new Gson().fromJson(response, MLoginEntity.class);
                                                Toast.makeText(context, PublicUtils.getMsg(entity.getRespCode()), Toast.LENGTH_SHORT).show();
                                                if (entity.getRespCode().equals(PublicUtils.SUCCESS)) {
                                                    dismiss();
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(context, "项目起投:" + PublicUtils.formatToDouble("" + minInvest) + "元 , " + "最大投资金额 :" + PublicUtils.formatToDouble("" + maxInvest) + "元", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(context, "请先实名认证后再投资下单", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
