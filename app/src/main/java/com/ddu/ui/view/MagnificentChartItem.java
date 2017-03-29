package com.ddu.ui.view;

/**
 * Created by Ahmed on 30.01.14.
 */
public class MagnificentChartItem {

// #MARK - Constants

    public int color;
    public int value;
    public String title;

// #MARK - Constructors

    public MagnificentChartItem(String title, int value, int color){
        this.color = color;
        this.value = value;
        this.title = title;
    }

}
