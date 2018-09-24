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
import android.webkit.ValueCallback;
import android.util.Log;

import java.util.logging.Logger;

import com.swg.news_android.WebviewSetting;

public class MainActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        System.setStatusBar(getWindow());

        webView = (WebView) findViewById(R.id.webView);

        WebviewSetting.init(webView,"https://tiantianxiangkan.com", this.getResources());
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