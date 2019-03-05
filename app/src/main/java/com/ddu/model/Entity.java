package com.ddu.model;

import java.util.List;

/**
 * @author yuyh.
 * @date 2017/1/18.
 */

public class Entity {

    public List<Row> rows;

    public static class Row {
        public long timestamp;

        public String src;

        public String dest;

        public double amount;

        public boolean isChecked;
    }


}
