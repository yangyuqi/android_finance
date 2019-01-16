package com.youzheng.zhejiang.financebooking;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.squareup.okhttp.Request;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.youzheng.zhejiang.financebooking.Thrid.network.OkHttpClientManager;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;
import com.youzheng.zhejiang.financebooking.UI.Home.HomePageFragment;
import com.youzheng.zhejiang.financebooking.UI.My.UserCenterFragment;
import com.youzheng.zhejiang.financebooking.UI.finance.FinanceFragment;
import com.youzheng.zhejiang.financebooking.UI.finance.SplashActivity;
import com.youzheng.zhejiang.financebooking.Widget.Utils.PublicUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.SharedPreferencesUtils;
import com.youzheng.zhejiang.financebooking.Widget.Utils.UrlUtils;
import com.youzheng.zhejiang.financebooking.Widget.common.IntenetUtil;
import com.youzheng.zhejiang.financebooking.Widget.common.PublishUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

public class MainActivity extends BaseActivity implements AMapLocationListener {

    FrameLayout frameLayout ;
    RadioGroup group;
    private ArrayList<String> fragmentTags;
    private   String CURR_INDEX = "currIndex";
    private  int currIndex = 0;
    int widWidth ,widHeight;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;
    private Map<String,Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        widWidth = outMetrics.widthPixels;
        widHeight = outMetrics.heightPixels;
        getLocation();

        initData(savedInstanceState);
        initView();
        initEvent();
        showFragment();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX, currIndex);
    }


    private void initData(Bundle savedInstanceState) {
        fragmentTags = new ArrayList<>(Arrays.asList("HomePageFragment", "CategoryFragment","UserCenterFragment"));
        if(savedInstanceState != null) {
            currIndex = savedInstanceState.getInt(CURR_INDEX);
            hideSavedFragment();
        }
    }

    private void hideSavedFragment() {
        Fragment fragment = fm.findFragmentByTag(fragmentTags.get(currIndex));
        if(fragment != null) {
            fm.beginTransaction().hide(fragment).commit();
        }
    }

    private void initEvent() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.foot_bar_home : currIndex = 0 ;break;
                    case R.id.foot_bar_im : currIndex = 1 ;break;
                    case R.id.main_footbar_user : currIndex = 2 ;break;
                }
                showFragment();
            }
        });
    }

    private void initView() {
        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        group = (RadioGroup) findViewById(R.id.group);
    }

    private void showFragment() {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag(fragmentTags.get(currIndex));
        if(fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fm.findFragmentByTag(fragmentTags.get(i));
            if(f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fm.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0 :
                return new HomePageFragment();
            case 1:
               return new FinanceFragment();
            case 2:
                return new UserCenterFragment();

            default:
                return null;
        }
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if(true){
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(),
                            "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    currIndex=0;
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            try {
                JSONObject object = new JSONObject(gson.toJson(aMapLocation));
                if (object.getString("q") != null) {
                    if (object.getString("q").equals("success")) {
                        map.put("longitude",object.getDouble("u"));
                        map.put("latitude",object.getDouble("t"));
                        mlocationClient.stopLocation();
                    }else {
                        showToast("请给应用赋予定位权限并把GSP按钮打开");
                    }

                }else {
                    map.put("longitude","");
                    map.put("latitude","");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                map.put("longitude","");
                map.put("latitude","");
            }
            pushData();
        }
    }

    private void getLocation() {
        mlocationClient = new AMapLocationClient(mContext);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mlocationClient.setLocationOption(mLocationOption);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RxPermissions permissions = new RxPermissions((MainActivity) this);
            permissions.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE).subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    if (aBoolean) {
                        mlocationClient.startLocation();
                    }
                }
            });
        } else {
            mlocationClient.startLocation();
        }
    }

    public String getCarrie(){
        TelephonyManager tm = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        String operator = "";
        @SuppressLint("MissingPermission") String IMSI = tm.getSubscriberId();

        if( IMSI == null){
            return "unknow";
        }
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            operator = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            operator = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            operator = "中国电信";
        }
        return operator;
    }

    private void pushData() {
        map.put("ip", PublishUtils.getIPAddress(mContext));
        map.put("screen_width",PublishUtils.px2dip(widWidth));
        map.put("screen_height",PublishUtils.px2dip(widHeight));
        map.put("os","android");
        map.put("os_version", Build.VERSION.RELEASE + "");
        String accessToken = (String) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.token,"0");
        if (accessToken.equals("0")) {
            map.put("is_login", "0");
        }else {
            map.put("is_login", "1");
        }

        map.put("latest_login_user",accessToken);
        String d = (String) SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.frist,"");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (d.equals(""+year+month+day)){
            map.put("is_first_day","0");
        }else {
            map.put("is_first_day","1");
        }
        SharedPreferencesUtils.setParam(mContext,SharedPreferencesUtils.frist,""+year+month+day);

        map.put("app_version", PublicUtils.getAppVersionName(mContext));
        map.put("login_user",SharedPreferencesUtils.getParam(mContext,SharedPreferencesUtils.userName,"0"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RxPermissions permissions = new RxPermissions(MainActivity.this);
            permissions.request(Manifest.permission.READ_PHONE_STATE).subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    if (aBoolean) {
                        map.put("device_id",PublishUtils.getIMEI(mContext));
                    }else {
                        map.put("device_id","");
                    }
                }
            });
        }else {
            map.put("device_id",PublishUtils.getIMEI(mContext));
        }
        map.put("network_type", IntenetUtil.getNetworkState(mContext));
        map.put("manufacturer", android.os.Build.MANUFACTURER);
        map.put("model",android.os.Build.MODEL);
        map.put("carrier",getCarrie());
        OkHttpClientManager.postAsynJson(gson.toJson(map), UrlUtils.GET_INFO, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {

            }
        });
    }
}
