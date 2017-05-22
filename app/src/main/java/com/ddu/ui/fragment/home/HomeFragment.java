package com.ddu.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ddu.R;
import com.ddu.icore.ui.fragment.BaseFragment;
import com.ddu.icore.ui.viewpager.Banner;
import com.ddu.icore.ui.viewpager.Holder;
import com.ddu.icore.ui.viewpager.ViewHolderCreator;
import com.ddu.icore.util.sys.ViewUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by yzbzz on 16/8/12.
 */
public class HomeFragment extends BaseFragment {

    private View mView;
    private Banner mConvenientBanner;
    @NonNull
    private ArrayList<Integer> localImages = new ArrayList<>();

    @Override
    public void initData(Bundle savedInstanceState) {
        for (int position = 0; position < 7; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
    }

    @Override
    public View getContentView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        mConvenientBanner = ViewUtils.findViewById(mView, R.id.home_banner);
        mConvenientBanner
                .setPages(new ViewHolderCreator() {
                    @NonNull
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages);
        mConvenientBanner.setPointViewVisible(true);
        mConvenientBanner.setManualPageable(true);
        mConvenientBanner.setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
        return mView;
    }

    public static int getResId(String variableName, @NonNull Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public class LocalImageHolderView implements Holder<Integer> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

}
