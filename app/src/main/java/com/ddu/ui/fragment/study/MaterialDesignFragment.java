package com.ddu.ui.fragment.study;

import com.iannotation.IRouter;

/**
 * Created by yzbzz on 2018/8/14.
 */
@IRouter(path = "MD", text = "MD", color = "#34c749", description = "MaterialDesign")
public class MaterialDesignFragment extends ContentFragment {

    @Override
    public String getUrl() {
        return "MD";
    }
}
