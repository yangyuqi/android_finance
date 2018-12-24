package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.MLoginEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MBankCardsDetailsEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MBankCardsEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.View.MyCountDownTimer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WithdrawCashActivity extends BaseActivity {
    private OptionsPickerView pvCustomTime;
    List<MBankCardsDetailsEntity> data = new ArrayList<>();
    List<String> num_data = new ArrayList<>();
    private MyCountDownTimer timer ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw_cash_layout);
        initView();
    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText("提现");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        timer = new MyCountDownTimer(((Button)findViewById(R.id.btn_send_code)),60000,1000);
        ((TextView)findViewById(R.id.tv_all_money)).setText(getIntent().getStringExtra("allMoney"));
        ((TextView)findViewById(R.id.tv_use_money)).setText(getIntent().getStringExtra("useMoney"));

        findViewById(R.id.edt_select_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

        findViewById(R.id.btn_send_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((EditText)findViewById(R.id.edt_phone)).getText().toString().equals("")){
                    showToast("请填写手机号码");
                    return;
                }
                timer.start();
                Map<String,Object> map = new HashMap<>();
                map.put("regPhone",((EditText)findViewById(R.id.edt_phone)).getText().toString());
                map.put("type","5");
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.SEND_CODE_ALL, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        MLoginEntity entity = gson.fromJson(response,MLoginEntity.class);
                        showToast(PublicUtils.getMsg(entity.getRespCode()));
                    }
                });
            }
        });

        findViewById(R.id.btn_with).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((TextView)findViewById(R.id.edt_select_card)).getText().toString().equals("")){
                    showToast("请选择银行卡号");
                    return;
                }
                if (((EditText)findViewById(R.id.edt_true_name)).getText().toString().equals("")){
                    showToast("请填写真实姓名");
                    return;
                }

                if (((EditText)findViewById(R.id.edt_true_id)).getText().toString().equals("")){
                    showToast("请填写真实身份证号");
                    return;
                }

                if (((EditText)findViewById(R.id.edt_get_money)).getText().toString().equals("")){
                    showToast("请填写提取额度");
                    return;
                }

//                if (((EditText)findViewById(R.id.edt_again_pas)).getText().toString().equals("")){
//                    showToast("请填写交易密码");
//                    return;
//                }

                if (((EditText)findViewById(R.id.edt_phone)).getText().toString().equals("")){
                    showToast("请填写手机号码");
                    return;
                }

                if (((EditText)findViewById(R.id.edt_code)).getText().toString().equals("")){
                    showToast("请填写验证码");
                    return;
                }

                Map<String,Object> map = new HashMap<>();
                map.put("name",((EditText)findViewById(R.id.edt_true_name)).getText().toString());
                map.put("idNum",((EditText)findViewById(R.id.edt_true_id)).getText().toString());
                map.put("cardNo",((TextView)findViewById(R.id.edt_select_card)).getText().toString());
                map.put("mobile",((EditText)findViewById(R.id.edt_phone)).getText().toString());
                map.put("codeMsg",((EditText)findViewById(R.id.edt_code)).getText().toString());
                map.put("idNumType","1");
                map.put("applyCashAmount",Double.parseDouble(((EditText)findViewById(R.id.edt_get_money)).getText().toString()));
                if (((EditText)findViewById(R.id.edt_again_pas)).getText().toString().length()>0) {
                    map.put("tradePass", PublicUtils.md5Password(((EditText) findViewById(R.id.edt_again_pas)).getText().toString()));
                }
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.APPLAY_GET_MONEY, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        MLoginEntity entity = gson.fromJson(response,MLoginEntity.class);
                        showToast(PublicUtils.getMsg(entity.getRespCode()));
                        if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                            finish();
                        }
                    }
                });
            }
        });

    }

    private void initData() {
        data.clear();
        num_data.clear();
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.GET_CARD_LIST, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MBankCardsEntity cardsEntity = gson.fromJson(response,MBankCardsEntity.class);
                if (cardsEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                    data.addAll(cardsEntity.getData());
                    for (MBankCardsDetailsEntity entity : data){
                        num_data.add(entity.getBankCard());
                    }

                    if (num_data.size()>0) {
                        pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                ((TextView)findViewById(R.id.edt_select_card)).setText(num_data.get(options1));
                                ((EditText)findViewById(R.id.edt_true_name)).setText(data.get(options1).getRealName());
                                ((EditText)findViewById(R.id.edt_true_id)).setText(data.get(options1).getIdCard());
                            }
                        }).setTitleText("选择银行卡").build();
                        pvCustomTime.setPicker(num_data);
                        pvCustomTime.show();
                    }
                }
            }
        });
    }
}
