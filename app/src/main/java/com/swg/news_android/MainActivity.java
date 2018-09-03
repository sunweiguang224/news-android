package com.swg.news_android;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.graphics.Color;
import android.graphics.Point;
//import android.support.v4.widget.DrawerLayout;
//import android.graphics.Color;
import android.webkit.WebChromeClient;
import android.view.KeyEvent;


public class MainActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setStatusBar();

        init();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.webView);

        // 需要加载的网页的url
//        webView.loadUrl("https://18686604386.davdian.com/");
//        webView.loadUrl("http://localhost:7001/");
//        webView.loadUrl("http://127.0.0.1:7001/");
        webView.loadUrl("http://192.168.1.111:7001/");
        WebSettings settings = webView.getSettings();

        // 如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        settings.setUserAgentString(webView.getSettings().getUserAgentString() + " StatusBarHeight/" + getStatusBarHeightPx());
//        settings.setUserAgentString(webView.getSettings().getUserAgentString() + " StatusBarHeight/12312312321");

        // 解决alert不出现的问题
        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //等待证书响应
                handler.proceed();
            }
        });
    }


    /**
     * 全透状态栏
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

            int vis = decorView.getSystemUiVisibility();

            // 只能设置2钟颜色

            // 深色
            vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

            // 白色
//            vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

            decorView.setSystemUiVisibility(vis);


        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        // 6.0以上，状态栏文字深色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            View decorView = this.getWindow().getDecorView();
//            if (decorView != null) {
//                int vis = decorView.getSystemUiVisibility();
//                // 深色
//                if (true) {
//                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//
//                    // 白色
//                } else {
//                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//                }
//                decorView.getSystemUiVisibility();
//                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//                decorView.setSystemUiVisibility(vis);
//                decorView.setSystemUiVisibility(0x0056b568);
//            }

        }
    }


    private float getStatusBarHeightPx() {
        int result = 0;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = this.getResources().getDimensionPixelSize(resourceId);
        }

        float density = this.getResources().getDisplayMetrics().density;

        float px = result / density;

        return px;
    }

    /**
     * 返回按钮
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}