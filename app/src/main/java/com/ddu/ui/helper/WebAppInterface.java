package com.ddu.ui.helper;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.webkit.JavascriptInterface;

import com.ddu.ui.dialog.LoginDialog;

public class WebAppInterface {

    Context mContext;

    public WebAppInterface(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    public void invoke(String json) {

//        Log.v("lhz", "json: " + json);
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

        LoginDialog loginDialog = LoginDialog.newInstance();
        loginDialog.showDialog((FragmentActivity) mContext);

    }

}
