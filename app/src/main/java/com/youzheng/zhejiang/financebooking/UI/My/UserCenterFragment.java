package com.youzheng.zhejiang.financebooking.UI.My;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.Model.user.MInfoEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MInvestmentActivityEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseFragment;
import com.youzheng.zhejiang.financebooking.UI.My.activity.AccountSafeActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.AttentionInfoActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.BankCardMangerActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.FinancingActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.GesturePasswordActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.HelpCenterActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.InvestmentActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.InviteActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.LoginActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.MoneyManageActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.OpenHandPassword;
import com.youzheng.zhejiang.financebooking.UI.My.activity.ReChargeMoneyActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.SetAppActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.UserCenterActivity;
import com.youzheng.zhejiang.financebooking.UI.My.activity.WithdrawCashActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;

import java.io.IOException;
import java.util.HashMap;

public class UserCenterFragment extends BaseFragment {

    View view ;
    TextView tv_user_info ,tv_all_money ,tv_use_money ,tv_has_money;
    TextView tv_name ;
    String accessToken ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_center_layout,null);
        initView();
        initEvent();
        return view;
    }

    private void initEvent() {
        tv_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accessToken.equals("")){
                    startActivity(new Intent(mContext,LoginActivity.class));
                    return;
                }
                startActivity(new Intent(mContext, UserCenterActivity.class));
            }
        });
        view.findViewById(R.id.tv_attention_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accessToken.equals("")){
                    startActivity(new Intent(mContext,LoginActivity.class));
                    return;
                }
                startActivity(new Intent(mContext, AttentionInfoActivity.class));
            }
        });

        view.findViewById(R.id.tv_card_manage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accessToken.equals("")){
                    startActivity(new Intent(mContext,LoginActivity.class));
                    return;
                }
                startActivity(new Intent(mContext, BankCardMangerActivity.class));
            }
        });

        view.findViewById(R.id.tv_money_manage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accessToken.equals("")){
                    startActivity(new Intent(mContext,LoginActivity.class));
                    return;
                }
                startActivity(new Intent(mContext, MoneyManageActivity.class));
            }
        });

        view.findViewById(R.id.rl_recharge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(mContext, ReChargeMoneyActivity.class));
            }
        });

        view.findViewById(R.id.rl_withdraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accessToken.equals("")){
                    startActivity(new Intent(mContext,LoginActivity.class));
                    return;
                }
                Intent intent = new Intent(mContext, WithdrawCashActivity.class);
                intent.putExtra("allMoney",tv_all_money.getText().toString());
                intent.putExtra("useMoney",tv_use_money.getText().toString());
                startActivity(intent);
            }
        });

        view.findViewById(R.id.tv_investment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accessToken.equals("")){
                    startActivity(new Intent(mContext,LoginActivity.class));
                    return;
                }
                startActivity(new Intent(mContext, InvestmentActivity.class));
            }
        });

        view.findViewById(R.id.tv_finance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accessToken.equals("")){
                    startActivity(new Intent(mContext,LoginActivity.class));
                    return;
                }
                startActivity(new Intent(mContext, FinancingActivity.class));
            }
        });

        view.findViewById(R.id.tv_account_safe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accessToken.equals("")){
                    startActivity(new Intent(mContext,LoginActivity.class));
                    return;
                }
                startActivity(new Intent(mContext, AccountSafeActivity.class));
            }
        });

        view.findViewById(R.id.tv_invite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(mContext, InviteActivity.class));
            }
        });

        view.findViewById(R.id.tv_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, HelpCenterActivity.class));
            }
        });

        view.findViewById(R.id.tv_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SetAppActivity.class));
            }
        });

    }

    private void initView() {
        tv_user_info = view.findViewById(R.id.tv_user_info);
        tv_name = view.findViewById(R.id.tv_name);
        tv_all_money = view.findViewById(R.id.tv_all_money);
        tv_use_money = view.findViewById(R.id.tv_use_money);
        tv_has_money = view.findViewById(R.id.tv_has_money);
        accessToken = (String) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.token,"");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDaat();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initDaat() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.USER_ONFO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    MInfoEntity infoEntity = gson.fromJson(response, MInfoEntity.class);
                    if (infoEntity.getRespCode().equals(PublicUtils.SUCCESS)) {
                        if (infoEntity.getMemberAuth() == null) {
                            return;
                        }
                        tv_name.setText(PublicUtils.phoneNum(infoEntity.getMemberAuth().getmBankphone()));
                        tv_all_money.setText(PublicUtils.formatToDouble("" + infoEntity.getAccount().getAccountAmount()));
                        tv_use_money.setText(PublicUtils.formatToDouble("" + infoEntity.getAccount().getAvailAmount()));
                        tv_has_money.setText(PublicUtils.formatToDouble("" + infoEntity.getAccount().getFreezeAmount()));
                    } else if (infoEntity.getRespCode().equals(PublicUtils.RELOGIN)) {
                        tv_name.setText("");
                        tv_all_money.setText(PublicUtils.formatToDouble("" + 0.00));
                        tv_use_money.setText(PublicUtils.formatToDouble("" + 0.00));
                        tv_has_money.setText(PublicUtils.formatToDouble("" + 0.00));
                        int shoushi = (int) SharedPreferencesUtils.getParam(mContext, SharedPreferencesUtils.shoushi, 0);
                        if (shoushi == 0 || shoushi == 2) {
                            startActivity(new Intent(mContext, LoginActivity.class));
                        } else if (shoushi == 1) {
                            startActivity(new Intent(mContext, GesturePasswordActivity.class));
                        }
                    }
                }catch (Exception e){}
            }
        });

        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.INVEST_MANAGE, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    MInvestmentActivityEntity entity = gson.fromJson(response,MInvestmentActivityEntity.class);
                    if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                        tv_has_money.setText(PublicUtils.formatToDouble(""+entity.getData().getBenifits()));
                    }
                }catch (Exception e){

                }
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            initDaat();
        }
    }
}
