package com.ddu.ui.fragment.study;

import com.iannotation.IRouter;

/**
 * Created by yzbzz on 2017/5/16.
 */
@IRouter(path = "UI", text = "UI", color = "#437fda", description = "UI学习")
public class UIFragment extends ContentFragment {

    @Override
    public String getUrl() {
        return "UI";
    }
}
