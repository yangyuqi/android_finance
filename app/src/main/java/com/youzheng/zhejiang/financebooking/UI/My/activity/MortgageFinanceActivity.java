package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.youzheng.zhejiang.financebooking.Model.user.MMyFinanceActivityDataEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;

public class MortgageFinanceActivity extends BaseActivity {

    private String type ;
    MMyFinanceActivityDataEntity dataEntity ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mortgage_finance_layout);
        initView();
    }

    private void initView() {
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        type = getIntent().getStringExtra("type");
        dataEntity = (MMyFinanceActivityDataEntity) getIntent().getSerializableExtra("data");
        if (type.equals("1")){
            findViewById(R.id.ll_two).setVisibility(View.GONE);
            findViewById(R.id.ll_one).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.textHeadTitle)).setText("融资详情");
            initContent(dataEntity);
        }else if (type.equals("2")){
            findViewById(R.id.ll_two).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_one).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.textHeadTitle)).setText("抵押详情");
            initDiContent(dataEntity);
        }
    }

    private void initDiContent(MMyFinanceActivityDataEntity entity) {
        ((TextView)findViewById(R.id.edt_name)).setText(entity.getApplicantDate());
        ((TextView)findViewById(R.id.tv_di_start)).setText(entity.getMortgage_start());
        ((TextView)findViewById(R.id.tv_di_end)).setText(entity.getMortgage_end());
        ((TextView)findViewById(R.id.tv_goods)).setText(entity.getApplicant_name());
        ((TextView)findViewById(R.id.tv_dec_do)).setText(entity.getApplicationMortgageDesc());
        if (entity.getAssets_status()==1){
            ((TextView)findViewById(R.id.tv_di_status)).setText("抵押中");
        }else if (entity.getAssets_status()==2){
            ((TextView)findViewById(R.id.tv_di_status)).setText("已出库");
        }
    }

    private void initContent(MMyFinanceActivityDataEntity dataEntity) {
        ((TextView)findViewById(R.id.tv_phone)).setText(dataEntity.getApplicant_date());
        ((TextView)findViewById(R.id.tv_time)).setText(PublicUtils.formatToDouble(""+dataEntity.getApplicant_financing_amount()));
        try {
            ((TextView)findViewById(R.id.tv_card)).setText(PublicUtils.formatToDouble(""+dataEntity.getActual_amount()));
        }catch (Exception e){}
        ((TextView)findViewById(R.id.tv_pay_lixi)).setText(""+dataEntity.getBenifits());
        ((TextView)findViewById(R.id.tv_pay_year)).setText(""+dataEntity.getFinancing_accrual());
        if (dataEntity.getApplicant_is_mortgage()==1){
            ((TextView)findViewById(R.id.tv_mort)).setText("是");
        }else if (dataEntity.getApplicant_is_mortgage()==2){
            ((TextView)findViewById(R.id.tv_mort)).setText("否");
        }
        ((TextView)findViewById(R.id.tv_desc)).setText(dataEntity.getApplicant_financing_desc());
        if (dataEntity.getApply_financing_status()==1){
            ((TextView)findViewById(R.id.tv_get_status)).setText("申请中");
        }else if (dataEntity.getApply_financing_status()==2){
            ((TextView)findViewById(R.id.tv_get_status)).setText("初审通过");
        }else if (dataEntity.getApply_financing_status()==3){
            ((TextView)findViewById(R.id.tv_get_status)).setText("初审失败");
            ((TextView)findViewById(R.id.tv_des_mm)).setText("审核失败原因");
        }else if (dataEntity.getApply_financing_status()==4){
            ((TextView)findViewById(R.id.tv_get_status)).setText("审核通过");
        }else if (dataEntity.getApply_financing_status()==5){
            ((TextView)findViewById(R.id.tv_get_status)).setText("审核失败");
        }else if (dataEntity.getApply_financing_status()==6){
            ((TextView)findViewById(R.id.tv_get_status)).setText("还款中");
        }else if (dataEntity.getApply_financing_status()==7){
            ((TextView)findViewById(R.id.tv_get_status)).setText("已还款");
        }


  }
}
