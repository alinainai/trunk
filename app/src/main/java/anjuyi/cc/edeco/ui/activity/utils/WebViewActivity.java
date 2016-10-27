package anjuyi.cc.edeco.ui.activity.utils;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.progressbar)
    ProgressBar bar;
    @BindView(R.id.webview)
    WebView webView;

    private String PAGENAME = "";
    private String url;
    private ArrayList<String> str;
    private Boolean loginchangetitle = false;
    private String title;


    @Override
    protected int initLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


        url = getIntent().getStringExtra("url");//地址
        title = getIntent().getStringExtra("title");//标题
    }

    @Override
    public void setListener() {

        webView.setWebChromeClient(new WebChromeClient() {

            //设置进度条
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    bar.setVisibility(View.GONE);
                } else {

                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(progress);
                }
                super.onProgressChanged(view, progress);
            }

            //设置标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if(!TextUtils.isEmpty(title))
                mainCartTitle.setText(title);
            }

        });
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }

        });

        WebSettings settings = webView.getSettings();
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //自适应屏幕
        //   settings.setDomStorageEnabled(true);  //开启DOM
        //   settings.setDefaultTextEncodingName("utf-8"); //设置编码
        //  settings.setAllowFileAccess(true);// 支持文件流
        // settings.setUseWideViewPort(true);// 调整到适合webview大小
        //   settings.setLoadWithOverviewMode(true);// 调整到适合webview大小
        //    settings.setBlockNetworkImage(true); //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        //   String t = Uri.parse(url).getQueryParameter("title");

    }

    @Override
    public void initData() {
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null) {
                if (webView.canGoBack()) {
                    webView.goBack();// 返回前一个页面
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {

        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }

    @OnClick({R.id.img_back, R.id.ll_back, R.id.main_cart_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_back:
                break;
            case R.id.main_cart_title:
                break;
        }
    }
}
