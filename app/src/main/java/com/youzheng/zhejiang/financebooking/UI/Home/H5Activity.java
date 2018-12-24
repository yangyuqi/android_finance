package com.youzheng.zhejiang.financebooking.UI.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;

public class H5Activity extends BaseActivity {

    String url ;
    WebView lsDetails ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h5_layout);

        url = getIntent().getStringExtra("url");
        ((TextView)findViewById(R.id.textHeadTitle)).setText("");
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lsDetails = (WebView) findViewById(R.id.ls_details);

         Uri uri = Uri.parse(url);
         Intent intent = new Intent(Intent.ACTION_VIEW, uri);
         startActivity(intent);

//        if (url!=null){
//            WebSettings webSettings = lsDetails.getSettings();
//            webSettings.setJavaScriptEnabled(true);
//            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//            webSettings.setAppCacheEnabled(true);
//            webSettings.setDomStorageEnabled(true);
//            webSettings.supportMultipleWindows();
//            webSettings.setAllowContentAccess(true);
//            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//            webSettings.setUseWideViewPort(true);
//            webSettings.setLoadWithOverviewMode(true);
//            webSettings.setSavePassword(true);
//            webSettings.setSaveFormData(true);
//            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//            webSettings.setLoadsImagesAutomatically(true);
//
//            lsDetails.setWebChromeClient(new WebChromeClient());
//            lsDetails.loadUrl(url);
//        }
    }


}
