package com.dream.beautifullife.model;

import java.util.List;

/**
 * Created by admin on 2015/11/10.
 */
public class BackOrderInfo {


    /**
     * code : 0
     * data : [{"amount":"处理中","backType":2,"backTypeText":"在线支付"},{"amount":"处理中","backType":7,"backTypeText":"首单返现"}]
     * message : 成功
     */

    private int code;
    private String message;
    /**
     * amount : 处理中
     * backType : 2
     * backTypeText : 在线支付
     */

    private List<DataEntity> data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String amount;
        private int backType;
        private String backTypeText;

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public void setBackType(int backType) {
            this.backType = backType;
        }

        public void setBackTypeText(String backTypeText) {
            this.backTypeText = backTypeText;
        }

        public String getAmount() {
            return amount;
        }

        public int getBackType() {
            return backType;
        }

        public String getBackTypeText() {
            return backTypeText;
        }
    }
}
