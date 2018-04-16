package com.ddu.ui.helper;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.ddu.ui.fragment.TestFragment;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class WebAppInterface {

    Context mContext;
    TestFragment mFagment;


    public WebAppInterface(Context context) {
        mContext = context;
    }

    public WebAppInterface(TestFragment context) {
        mFagment = context;
    }

    @JavascriptInterface
    public void invoke(String json) {
        try {
            Log.v("lhz", "json: " + json);
            getUserStatusInfo(new JSONObject(json));
        } catch (Exception e) {

        }

//
//        try {
//            JSONObject jsonString = new JSONObject(json);
//            String callback = jsonString.getString("callback");
//            GodIntent godIntent = new GodIntent();
//            godIntent.setAction(Actions.TEST_ACTION);
//            Bundle bundle = new Bundle();
//            bundle.putString("method",callback);
//            godIntent.setBundle(bundle);
//            ObserverManager.getInstance().notify(godIntent);
//        } catch (Exception e) {
//
//        }

//        LoginDialog loginDialog = LoginDialog.newInstance();
//        loginDialog.showDialog((FragmentActivity) mContext);

    }

    public void getUserStatusInfo(JSONObject jsonString) {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("userId", "10086");


            String callBack = jsonString.getString("callback");
            if (!TextUtils.isEmpty(callBack)) {
                if (null != mFagment) {
                    mFagment.excute(callBack, jsonObject.toString());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
