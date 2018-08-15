package com.ddu.ui.fragment.study;

import com.iannotation.IRouter;

/**
 * Created by yzbzz on 2017/5/16.
 */
@IRouter(path = "customer", text = "自定义", color = "#437fda", description = "自定义的view和工具类")
public class CustomerFragment extends ContentFragment {

    @Override
    public String getUrl() {
        return "customer";
    }
}
