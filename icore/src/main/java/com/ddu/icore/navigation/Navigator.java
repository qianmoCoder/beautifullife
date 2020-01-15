package com.ddu.icore.navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.ddu.icore.ui.activity.ShowDetailActivity;

/**
 * Created by yzbzz on 2016/10/28.
 */

public class Navigator {
    private static final String ICORE_SCHEME = "icore";

    public static void navigation(Uri uri) {
        String scheme = uri.getScheme();
        if (ICORE_SCHEME.equalsIgnoreCase(scheme)) {
            String host = uri.getHost();
            if ("6".equalsIgnoreCase(host)) {

            }
        }
    }

    public static void navigation(String url) {
        Uri uri = Uri.parse(url);
    }

    public static void startShowDetailActivity(@NonNull Context context, @NonNull Class<?> fragment) {
        startShowDetailActivity(context, fragment.getName(), new Bundle());
    }

    public static void startShowDetailActivity(@NonNull Context context, @NonNull Class<?> fragment, Bundle bundle) {
        startShowDetailActivity(context, fragment.getName(), bundle);
    }

    public static void startShowDetailActivity(@NonNull Context context, String fragmentName, Bundle bundle) {
        Intent intent = ShowDetailActivity.getShowDetailIntent(context, fragmentName, bundle);
        context.startActivity(intent);
    }

//    public static void startShareFragmentDialog(@NonNull FragmentActivity activity) {
//        ShareDialogFragmentT shareDialogFragment = ShareDialogFragmentT.newInstance();
//        shareDialogFragment.show(activity.getSupportFragmentManager(), "shareDialogFragment");
//    }

}
