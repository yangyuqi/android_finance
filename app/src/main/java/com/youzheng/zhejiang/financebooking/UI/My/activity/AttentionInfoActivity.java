package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.youzheng.zhejiang.financebooking.Model.MLoginEntity;
import com.youzheng.zhejiang.financebooking.Model.user.MAttenInfoEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.View.MyCountDownTimer;
import com.youzheng.zhejiang.financebooking.Widget.dialog.BottomPhotoDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.utils.ImageCaptureManager;
import rx.functions.Action1;

public class AttentionInfoActivity extends BaseActivity {

    private MyCountDownTimer timer ;
    int front_photo ;
    List<String> photo = Arrays.asList("","");
    ImageCaptureManager captureManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attention_info_layout);
        initView();
        initData();

        findViewById(R.id.btn_reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((EditText)findViewById(R.id.tv_name)).getText().toString().equals("")){
                    showToast("请输入真实姓名");
                    return;
                }
                if (((EditText)findViewById(R.id.tv_card)).getText().toString().equals("")){
                    showToast("请输入身份证号");
                    return;
                }
                if (((EditText)findViewById(R.id.tv_bank)).getText().toString().equals("")){
                    showToast("请输入开户银行");
                    return;
                }
                if (((EditText)findViewById(R.id.tv_card_num)).getText().toString().equals("")){
                    showToast("请输入银行卡号");
                    return;
                }
                if (((EditText)findViewById(R.id.tv_phone_num)).getText().toString().equals("")){
                    showToast("请输入手机号");
                    return;
                }
                if (((EditText)findViewById(R.id.tv_phone_code)).getText().toString().equals("")){
                    showToast("请输入验证码");
                    return;
                }
                if (photo.get(0).equals("")){
                    showToast("请上传身份证正面");
                    return;
                }
                if (photo.get(1).equals("")){
                    showToast("请上传身份证反面");
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("name",((EditText)findViewById(R.id.tv_name)).getText().toString());
                map.put("idNumType","1");
                map.put("idNum",((EditText)findViewById(R.id.tv_card)).getText().toString());
                map.put("idCardFront",photo.get(0));
                map.put("idCardBack",photo.get(1));
                map.put("openBank",((EditText)findViewById(R.id.tv_bank)).getText().toString());
                map.put("cardNoType","1");
                map.put("cardNo",((EditText)findViewById(R.id.tv_card_num)).getText().toString());
                map.put("mobile",((EditText)findViewById(R.id.tv_phone_num)).getText().toString());
                map.put("codeMsg",((EditText)findViewById(R.id.tv_phone_code)).getText().toString());
                final ProgressDialog dialog = new ProgressDialog(AttentionInfoActivity.this);
                dialog.setMessage("请稍等。。。");
                dialog.setCancelable(false);
                dialog.show();
                OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.CONFIRM_USER, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        MLoginEntity entity = gson.fromJson(response,MLoginEntity.class);
                        if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                            finish();
                        }else {
                            showToast(PublicUtils.getMsg(entity.getRespCode()));
                        }
                    }
                });
            }
        });

        findViewById(R.id.btn_sendf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((EditText)findViewById(R.id.tv_phone_num)).getText().toString().equals("")){
                    showToast("请输入手机号");
                    return;
                }
                timer.start();
                Map<String,Object> map = new HashMap<>();
                map.put("regPhone",((EditText)findViewById(R.id.tv_phone_num)).getText().toString());
                map.put("type","2");
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

        findViewById(R.id.iv_front_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                front_photo = 1 ;
                final BottomPhotoDialog dialog = new BottomPhotoDialog(mContext, R.layout.view_popupwindow);
                dialog.show();
                dialog.getTv_pick_phone().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        takePhoto();
                    }
                });

                dialog.getTv_select_photo().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        selectPhoto();
                    }
                });
            }
        });

        findViewById(R.id.iv_front_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                front_photo=2 ;
                final BottomPhotoDialog dialog = new BottomPhotoDialog(mContext, R.layout.view_popupwindow);
                dialog.show();
                dialog.getTv_pick_phone().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        takePhoto();
                    }
                });

                dialog.getTv_select_photo().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        selectPhoto();
                    }
                });
            }
        });
    }

    private void initData() {
        OkHttpClientManager.postAsynJson(gson.toJson(new HashMap<>()), UrlUtils.AUTH_INFO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MAttenInfoEntity infoEntity = gson.fromJson(response,MAttenInfoEntity.class);
                if (infoEntity.getRespCode().equals(PublicUtils.SUCCESS)){
                    findViewById(R.id.btn_reset_password).setVisibility(View.GONE);
                    findViewById(R.id.ll_code).setVisibility(View.GONE);
                    ((EditText)findViewById(R.id.tv_name)).setText(infoEntity.getmName());
                    ((EditText)findViewById(R.id.tv_card)).setText(infoEntity.getmIdcard());
                    if (infoEntity.getCardType()==1){
                        ((TextView)findViewById(R.id.tv_bank_type)).setText("借记卡");
                    };
                    ((EditText)findViewById(R.id.tv_bank)).setText(infoEntity.getOpenBank());
                    ((EditText)findViewById(R.id.tv_card_num)).setText(infoEntity.getmBankcard());
                    ((EditText)findViewById(R.id.tv_phone_num)).setText(infoEntity.getmBankphone());
                    Glide.with(mContext).load(UrlUtils.PHOTO_ADD+infoEntity.getIdCardFont()).error(R.mipmap.group_12_1).into((ImageView) findViewById(R.id.iv_front_card));
                    Glide.with(mContext).load(UrlUtils.PHOTO_ADD+infoEntity.getIdCardBack()).error(R.mipmap.group_12_2).into((ImageView) findViewById(R.id.iv_front_back));
                    findViewById(R.id.iv_front_card).setEnabled(false);
                    findViewById(R.id.iv_front_back).setEnabled(false);
                }else if (infoEntity.getRespCode().equals(PublicUtils.RELOGIN)){
                    startActivity(new Intent(mContext,LoginActivity.class));
                }else if (infoEntity.getRespCode().equals("1002")){
                    if (infoEntity.getmName()==null){
                        findViewById(R.id.btn_reset_password).setVisibility(View.VISIBLE);
                        findViewById(R.id.ll_code).setVisibility(View.VISIBLE);
                        ((TextView)findViewById(R.id.tv_bank_type)).setText("借记卡");
                    }
                }
            }
        });
    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText("实名认证");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        timer = new MyCountDownTimer((Button) findViewById(R.id.btn_sendf),60000,1000);
    }


    private void takePhoto() {
        RxPermissions permissions = new RxPermissions((AttentionInfoActivity) mContext);
        permissions.request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    captureManager = new ImageCaptureManager(mContext);
                    Intent intent = null;
                    try {
                        intent = captureManager.dispatchTakePictureIntent();
                        startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void selectPhoto() {
        RxPermissions permissions = new RxPermissions((AttentionInfoActivity) mContext);
        permissions.request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    //选择相册
                    PhotoPicker.builder()
                            .setPhotoCount(1)
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setPreviewEnabled(false)
                            .start(AttentionInfoActivity.this, PhotoPicker.REQUEST_CODE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if (captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();
                        // 照片地址
                        String imagePaht = captureManager.getCurrentPhotoPath();
                        updatePhoto(imagePaht);
                    }
                    break;
            }
        }
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                String imagePaht = photos.get(0);
                updatePhoto(imagePaht);
            }
        }
    }

    private void updatePhoto(final String imagePaht) {
        if (imagePaht != null) {
            File file = new File(imagePaht);
            try {
                OkHttpClientManager.postAsyn(UrlUtils.UPLIAD_MEN_PHOTO, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("ssss","--"+response);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONObject k = object.getJSONObject("data");
                            if (front_photo==1){
                                photo.set(0,k.getString("imagePath"));
                            }else if (front_photo==2){
                                photo.set(1,k.getString("imagePath"));
                            }

                            if (front_photo==1){
                                Glide.with(mContext).load(imagePaht).error(R.mipmap.group_12_1).into((ImageView) findViewById(R.id.iv_front_card));
                            }else if (front_photo==2){
                                Glide.with(mContext).load(imagePaht).error(R.mipmap.group_12_2).into((ImageView) findViewById(R.id.iv_front_back));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, file, "imgFile");
            } catch (IOException e) {
                e.printStackTrace();

            }

        }
    }


}
