package com.youzheng.zhejiang.financebooking.UI.My.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.youzheng.zhejiang.financebooking.Model.MLoginEntity;
import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.dialog.BottomPhotoDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.utils.ImageCaptureManager;
import rx.functions.Action1;

public class CoApplayActivity extends BaseActivity {
    ImageView iv_photo_front, iv_photo_back ,iv_photo_id;
    List<String> photo = Arrays.asList("","");
    ImageCaptureManager captureManager;
    int front_photo ,applicantIsMortgage ;
    private OptionsPickerView pvCustomTime;
    private String front_id_photo ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.co_applay_layout);
        initView();
    }

    private void initView() {
        ((TextView)findViewById(R.id.textHeadTitle)).setText("企业融资申请");
        iv_photo_front = (ImageView) findViewById(R.id.iv_photo_front);
        iv_photo_back = (ImageView) findViewById(R.id.iv_photo_back);
        iv_photo_id = (ImageView) findViewById(R.id.iv_photo_id);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        findViewById(R.id.tv_sublimt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initClick();
            }
        });

        findViewById(R.id.tv_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<String> dataq = new ArrayList<>();
                dataq.add("是");dataq.add("否");
                pvCustomTime = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        if (dataq.get(options1).equals("是")){
                            applicantIsMortgage = 1;
                            ((TextView)findViewById(R.id.tv_card)).setText("是");
                            findViewById(R.id.ll_show).setVisibility(View.VISIBLE);
                        }else if (dataq.get(options1).equals("否")){
                            applicantIsMortgage = 2 ;
                            ((TextView)findViewById(R.id.tv_card)).setText("否");
                            findViewById(R.id.ll_show).setVisibility(View.GONE);
                        }
                    }
                }).setTitleText("是否抵押").build();
                pvCustomTime.setPicker(dataq);
                pvCustomTime.show();
            }
        });


        iv_photo_front.setOnClickListener(new View.OnClickListener() {
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

        iv_photo_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                front_photo = 2 ;
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

        iv_photo_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                front_photo = 3 ;
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


        findViewById(R.id.edt_future_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView timePickerView = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        ((TextView) findViewById(R.id.edt_future_time)).setText(sdf.format(date));
                    }
                }).setType(new boolean[]{true, true, true, false, false, false})
                        .setLabel("年", "月", "日", "", "", "") //设置空字符串以隐藏单位提示
                        .setDividerColor(Color.DKGRAY)
                        .setContentSize(20)
                        .setDate(Calendar.getInstance())
                        .build();

                timePickerView.show();
            }
        });

    }

    private void initClick() {
        if (((EditText) findViewById(R.id.tv_name)).getText().toString().equals("")) {
            showToast("请填写请输入企业名称");
            return;
        }
        if (((EditText) findViewById(R.id.edt_phone)).getText().toString().equals("")) {
            showToast("请填写法人姓名");
            return;
        }
        if (((EditText) findViewById(R.id.edt_person_num)).getText().toString().equals("")) {
            showToast("请填写法人身份证号码");
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
        if (((EditText) findViewById(R.id.edt_family_money_use)).getText().toString().equals("")){
            showToast("请填写企业年收入");
            return;
        }

        if (((EditText) findViewById(R.id.edt_need_money)).getText().toString().equals("")){
            showToast("请填写联系电话");
            return;
        }

        if (((EditText) findViewById(R.id.edt_pay_money_time)).getText().toString().equals("")){
            showToast("请填写电子邮箱");
            return;
        }
        if (((EditText) findViewById(R.id.edt_get_money_desc)).getText().toString().equals("")){
            showToast("请填写营业执照号");
            return;
        }

        if (front_id_photo==null){
            showToast("请上传营业执照");
            return;
        }

        if (((TextView)findViewById(R.id.tv_card)).getText().toString().equals("")){
            showToast("请选择是否有抵押");
            return;
        }

        if (((EditText) findViewById(R.id.edt_future)).getText().toString().equals("")){
            showToast("请填写抵押描述");
            return;
        }

        if (((EditText) findViewById(R.id.edt_future_money)).getText().toString().equals("")){
            showToast("请填写融资金额");
            return;
        }

        if (((TextView) findViewById(R.id.edt_future_time)).getText().toString().equals("")){
            showToast("请填写融资期限");
            return;
        }

        if (((EditText) findViewById(R.id.edt_future_des)).getText().toString().equals("")){
            showToast("请填写融资描述");
            return;
        }
        if (((EditText) findViewById(R.id.edt_future_hangye)).getText().toString().equals("")){
            showToast("请填写从事行业");
            return;
        }

        Map<String,Object> map = new HashMap<>();
        map.put("applicantName",((EditText) findViewById(R.id.tv_name)).getText().toString());
        map.put("applicantLegalName",((EditText) findViewById(R.id.edt_phone)).getText().toString());
//        map.put("applicantLegalPhone",((EditText) findViewById(R.id.edt_need_money)).getText().toString());
        map.put("applicantLegalIdcard",((EditText) findViewById(R.id.edt_person_num)).getText().toString());
        map.put("applicantLegalIdcardFront",photo.get(0));
        map.put("applicantLegalIdcardBack",photo.get(1));
        map.put("applicantComIncome", Integer.parseInt(((EditText) findViewById(R.id.edt_family_money_use)).getText().toString()));
        map.put("applicantComTelephone",((EditText) findViewById(R.id.edt_need_money)).getText().toString());
        map.put("applicantComEmail",((EditText) findViewById(R.id.edt_pay_money_time)).getText().toString());
        map.put("applicantSocialNum",((EditText) findViewById(R.id.edt_get_money_desc)).getText().toString());
        map.put("applicantSocialImg",front_id_photo);
        map.put("applicantIsMortgage",applicantIsMortgage);
        if (applicantIsMortgage==1) {
            map.put("applicantMortgageDesc", ((EditText) findViewById(R.id.edt_future)).getText().toString());
        }
        map.put("applicantFinancingAmount",Integer.parseInt(((EditText) findViewById(R.id.edt_future_money)).getText().toString()));
        map.put("applicantFinancingEnddate",((TextView) findViewById(R.id.edt_future_time)).getText().toString());
        map.put("applicantFinancingDesc",((EditText) findViewById(R.id.edt_future_des)).getText().toString());
        map.put("applicantIndustry",((EditText) findViewById(R.id.edt_future_hangye)).getText().toString());
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.APPLAY_CO_SELF, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                MLoginEntity entity = gson.fromJson(response,MLoginEntity.class);
                showToast(PublicUtils.getMsg(entity.getRespMsg()));
                if (entity.getRespCode().equals(PublicUtils.SUCCESS)){
                    finish();
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
                OkHttpClientManager.postAsyn(UrlUtils.APPLAY_CO_PHOTO, new OkHttpClientManager.StringCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONObject k = object.getJSONObject("data");
                            if (front_photo==1){
                                photo.set(0,k.getString("imagePath"));
                            }else if (front_photo==2){
                                photo.set(1,k.getString("imagePath"));
                            }else if (front_photo==3){
                                front_id_photo = k.getString("imagePath");
                            }

                            if (front_photo==1){
                                Glide.with(mContext).load(imagePaht).error(R.mipmap.group_12_1).into(iv_photo_front);
                            }else if (front_photo==2){
                                Glide.with(mContext).load(imagePaht).error(R.mipmap.group_12_2).into(iv_photo_back);
                            }else if (front_photo==3){
                                Glide.with(mContext).load(imagePaht).error(R.mipmap.group_32_1).into(iv_photo_id);
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


    private void takePhoto() {
        RxPermissions permissions = new RxPermissions((CoApplayActivity) mContext);
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
        RxPermissions permissions = new RxPermissions((CoApplayActivity) mContext);
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
                            .start(CoApplayActivity.this, PhotoPicker.REQUEST_CODE);
                }
            }
        });
    }
}
