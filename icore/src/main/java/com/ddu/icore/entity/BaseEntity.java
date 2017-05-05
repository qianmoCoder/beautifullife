package com.ddu.icore.entity;

public class BaseEntity {

    private int code;
    private String message = "null";


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public boolean isSuccess() {
        return this.code == 0;
    }
}
