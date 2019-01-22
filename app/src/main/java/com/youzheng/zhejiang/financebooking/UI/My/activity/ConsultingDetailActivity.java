package com.youzheng.zhejiang.financebooking.UI.My.activity;


import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youzheng.zhejiang.financebooking.R;
import com.youzheng.zhejiang.financebooking.UI.BaseActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ConsultingDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView btnBack;
    /**
     * Smart
     */
    private TextView textHeadTitle;
    /**  */
    private TextView textHeadNext;
    private ImageView top_header_ll_right_iv_caidan;
    private RelativeLayout layout_header;
    private WebView web_detail;
    //字体颜色设为白色, “p”标签内的字体颜色  “*”定义了字体大小以及行高；
    public final static String CSS_STYLE ="<style>* {font-size:40px;line-height:40px;}</style>";

    private String web_html;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulting_detail);
        web_html=getIntent().getStringExtra("web_html");
        title=getIntent().getStringExtra("title");
        initView();
        initWebview();
        initData();
    }

    private void initData() {
        String html = "<html>" + web_html + "</html>";
        web_detail.loadDataWithBaseURL(null,CSS_STYLE+getNewContent(html), "text/html", "utf-8", null);
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        textHeadTitle.setText(title);
        textHeadNext = (TextView) findViewById(R.id.textHeadNext);
        top_header_ll_right_iv_caidan = (ImageView) findViewById(R.id.top_header_ll_right_iv_caidan);
        layout_header = (RelativeLayout) findViewById(R.id.layout_header);
        web_detail = (WebView) findViewById(R.id.web_detail);
    }


    private void initWebview() {
        // wv_detail.loadUrl("http://www.baidu.com");
        WebSettings webSettings = web_detail.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //优先使用缓存:
//        wv_introduce.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置自适应屏幕，两者合用
        webSettings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //允许混合（http，https）
            webSettings.setMixedContentMode((WebSettings.MIXED_CONTENT_ALWAYS_ALLOW));
        }
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);  //就是这句
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
//        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        web_detail.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    //progressBar.setVisibility(View.GONE);
                } else {
//                    if (progressBar.getVisibility() != View.VISIBLE) {
//                        progressBar.setVisibility(View.VISIBLE);
//                    }
//                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        web_detail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //imgReset();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }

    public static String getNewContent(String htmltext){

        try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }
            return doc.toString();
        }catch (Exception e){

        }
        return htmltext;
    }
}
