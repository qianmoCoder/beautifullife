package com.ddu.ui.helper;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.ddu.icore.aidl.GodIntent;
import com.ddu.logic.common.LogicActions;
import com.ddu.logic.common.ObserverManager;

import org.json.JSONObject;

public class WebAppInterface {

    Context mContext;

    public WebAppInterface(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    public void invoke(String json) {

        Log.v("lhz", "json: " + json);

        try {
            JSONObject jsonString = new JSONObject(json);
            String callback = jsonString.getString("callback");
            GodIntent godIntent = new GodIntent();
            godIntent.setAction(LogicActions.TEST_ACTION);
            Bundle bundle = new Bundle();
            bundle.putString("method",callback);
            godIntent.setBundle(bundle);
            ObserverManager.getInstance().notifyUi(godIntent);
        } catch (Exception e) {

        }


    }

}
