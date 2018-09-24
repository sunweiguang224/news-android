package com.swg.news_android;

import android.content.res.Resources;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.net.Uri;
import java.util.HashMap;
import java.util.Set;

import com.swg.news_android.System;
import com.swg.news_android.ApiForJs;

public class WebviewSetting {

    /**
     * 设置WebView
     */
    public static void init(final WebView webView, String url, Resources resources) {

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // 解决alert不出现的问题
        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //等待证书响应
                handler.proceed();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                /*// 调用js方法
                WebviewSetting.execJsApi(webView, "test", "哈哈哈", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        //此处为 js 返回的结果
                        Log.i("msg", value);
                    }
                });*/
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                // 步骤2：根据协议的参数，判断是否是所需要的url
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）

                Uri uri = Uri.parse(url);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                /*if ( uri.getScheme().equals("app")) {

                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {

                        //  步骤3：
                        // 执行JS所需要调用的逻辑
                        Log.i("msg","js调用了Android的方法");
                        // 可以在协议上带有参数并传递到Android上
                        HashMap<String, String> params = new HashMap<>();
                        Set<String> collection = uri.getQueryParameterNames();

                    }

                    return true;
                }*/
                return super.shouldOverrideUrlLoading(view, url);
            }

        });


        // 提供api供js调用
        webView.addJavascriptInterface(new ApiForJs(), "native");


        WebSettings settings = webView.getSettings();

        // 如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        settings.setJavaScriptEnabled(true);

//        System.out.print(WebSettings.LayoutAlgorithm);

        // UA注入app标识
        settings.setUserAgentString(settings.getUserAgentString() + " app/news");

        // UA注入状态栏高度
        settings.setUserAgentString(settings.getUserAgentString() + " StatusBarHeight/" + System.getStatusBarHeight(resources));

        // 需要加载的网页的url
        webView.loadUrl(url);
    }

    /**
     * 调用js工具方法
     * @param webView
     * @param apiName
     * @param jsonParam
     * @param cb
     */
    public static void execJsApi(WebView webView, String apiName, String jsonParam, ValueCallback<String> cb) {
        String api = "javascript:window.apiForNative." + apiName + "('" + jsonParam + "')";

        // android >= 4.4，优点：执行效率高，能拿到JS执行返回结果
        if (Build.VERSION.SDK_INT >= 19) {
            webView.evaluateJavascript(api, cb);

            // android < 4.4
        } else {
            webView.loadUrl(api);
        }
    }
}
