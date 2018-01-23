package com.ddu.icore.ui.help;

/**
 * Created by yzbzz on 2018/1/23.
 */

public interface IShape {

    int DEFAULT = 0x01;
    int OVAL = 0x02;
    int ROUND = 0x04;
    int ROUND_RECT = 0x08;
    int SEGMENT = 0x010;

    int DIRECTION_LEFT = 0x0010;
    int DIRECTION_RIGHT = 0x0020;
    int DIRECTION_TOP = 0x0040;
    int DIRECTION_BOTTOM = 0x0080;
}
