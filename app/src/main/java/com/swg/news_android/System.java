package com.swg.news_android;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class System {

    /**
     * 设置状态栏，背景透明黑字
     */
    public static void setStatusBar(Window window) {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
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
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static float getStatusBarHeight(Resources resources) {
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }

        float density = resources.getDisplayMetrics().density;

        float px = result / density;

        return px;
    }
}
