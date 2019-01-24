package com.ddu.model;

import java.util.List;

public class AddressBean {

    private String label;
    private String value;
    private boolean status;
    private List<AddressBean> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<AddressBean> getChildren() {
        return children;
    }

    public void setChildren(List<AddressBean> children) {
        this.children = children;
    }


}
