package com.swg.news_android;

import android.webkit.JavascriptInterface;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONException;


public class ApiForJs extends Object {

    @JavascriptInterface
    public String test(String jsonStr) {
        JSONObject resultJson = new JSONObject();
        try {
            JSONObject options = new JSONObject(jsonStr);

            resultJson.put("status", 200);
            resultJson.put("content", options.get("msg") + "ffff");

        } catch (JSONException err) {
            Log.e("error", err.toString());
            try {
                resultJson.put("status", 500);
                resultJson.put("error", err.toString());
            } catch (JSONException error) {
            }
        }
        return resultJson.toString();
    }

}
