package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.youzheng.zhejiang.financebooking.MainActivity;
import com.youzheng.zhejiang.financebooking.Model.MLoginDetails;
import com.youzheng.zhejiang.financebooking.Model.MLoginEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MInfoEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.ActiivtyStack;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.gesture.PatternLockUtils;
import com.youzheng.zhejiang.financebooking.Widget.gesture.PatternLockView;
import com.youzheng.zhejiang.financebooking.Widget.gesture.PatternLockViewListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GesturePasswordActivity extends BaseActivity {

    private PatternLockView mPatternLockView;

    private int is_has_hand ,add_num;
    String frist_pass = null ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture_password_layout);
        initView();

        initEvent();
    }

    private void initEvent() {
        findViewById(R.id.tv_forget_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,GestureForgetPassActivity.class));
            }
        });

        findViewById(R.id.tv_forget_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,LoginActivity.class));
            }
        });
    }

    private void initView() {
        is_has_hand = (int) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.hsa_hand_code,0);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (is_has_hand==0) {
            ((TextView) findViewById(R.id.textHeadTitle)).setText("绘制手势密码");
        }else if (is_has_hand==1){
            ((TextView) findViewById(R.id.textHeadTitle)).setText("手势密码登录");
            ((TextView)findViewById(R.id.tv_name)).setText("手势密码登录");
        }
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);


//        mPrompt.setText("低于4个点为错误手势，大于4个点为正确手势");
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);
        mPatternLockView.setTactileFeedbackEnabled(true);//设置触觉是否能震动
        mPatternLockView.resetPattern();//恢复初始位置
    }


    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {

        @Override
        public void onStarted() {
            onLockViewStarted();
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            onLockViewProgress(progressPattern);
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            onLockViewComplete(pattern);
        }

        @Override
        public void onCleared() {
            onLockViewCleared();
        }
    };

    private void onLockViewStarted() {
        Log.d("yyq", "手势开始");
    }

    private void onLockViewProgress(List<PatternLockView.Dot> progressPattern) {
        Log.d("yyq", "手势进度: " +
                PatternLockUtils.patternToString(mPatternLockView, progressPattern));
    }

    private void onLockViewComplete(List<PatternLockView.Dot> pattern) {
        Log.d("yyq", "手势最终密码: " +
                PatternLockUtils.patternToString(mPatternLockView, pattern));
        String password = PatternLockUtils.patternToString(mPatternLockView, pattern);
        mPatternLockView.resetPattern();

        if (password.length() < 4) {
            //设置错误模式
            mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
//            mPrompt.setText("连接4个点后显示的就是正常的颜色");
//            PatternLockUtils.shock(mPrompt);
            showToast("连接不能小于4个点");
            return;
        }

        if (is_has_hand==0){
            add_num++;
            if (add_num==1) {
                frist_pass = password;
                showToast("再绘制一下手势密码");
            }else if (add_num==2){  //手势添加
                if (frist_pass.equals(password)){
                    getUserInfo(password);
                }else {
                    showToast("两次手势密码不一致,请重新绘制");
                    add_num=0;
                    frist_pass = null ;
                }

            }
        }else if (is_has_hand==1){
            Map<String,Object> map = new HashMap<>();
            map.put("regPhone",SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.userName,""));
            map.put("regPassword", PublicUtils.md5Password(password));
            map.put("loginTerminal","Android");
            OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GESTURE_LOGIN, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(String response) {
                    MLoginDetails entity = gson.fromJson(response, MLoginDetails.class);
                    if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                        SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.token, entity.getToken());
                        SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.userName, entity.getUserName());
                        SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.invitaionCode, entity.getInvitaionCode());
                        SharedPreferencesUtils.setParam(mContext,SharedPreferencesUtils.isAuth,entity.getIsAuth());
                        ActiivtyStack.getScreenManager().clearAllActivity();
                        startActivity(new Intent(mContext, MainActivity.class));
                    }else {
                        showToast(PublicUtils.getMsg(entity.getRespCode()));
                    }
                }
            });
        }

    }

    private void getUserInfo(final String password) {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.USER_ONFO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MInfoEntity infoEntity = gson.fromJson(response,MInfoEntity.class);
                if (infoEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                    Map<String,Object> stringObjectMap = new HashMap<>();
                    stringObjectMap.put("mId",infoEntity.getAccount().getmId());
                    stringObjectMap.put("phone",SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.userName,""));
                    stringObjectMap.put("gesturePass",PublicUtils.md5Password(password));
                    stringObjectMap.put("gestureStatus","1");
                    stringObjectMap.put("gestureView","2");
                    OkHttpClientManager.postAsynJson(gson.toJson(stringObjectMap), UrlUtils.ADD_GESTURE, new OkHttpClientManager.StringCallback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(String response) {
                            MLoginEntity entity = gson.fromJson(response,MLoginEntity.class);
                            if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                                finish();
                            }else {
                                showToast(entity.getRespMsg());
                            }
                        }
                    });
                }
            }
        });
    }

    private void onLockViewCleared() {
        Log.d("liuyz", "手势取消");
    }
}
