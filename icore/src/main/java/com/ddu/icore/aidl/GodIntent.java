package com.ddu.icore.aidl;

import android.os.Bundle;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class GodIntent implements Parcelable {

    private String action;
    private Message message;

    public String getAction() {
        return action == null ? "" : action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Message getMessage() {
        return message == null ? Message.obtain() : message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Bundle getData() {
        Bundle data = message.getData();
        if (null == data) { // 再检查一遍，避免不同Android版本带来的影响
            data = new Bundle();
            message.setData(data);
        }
        return data;
    }

    public void setWhat(int what) {
        message.what = what;
    }

    public void putInt(String key, int value) {
        getData().putInt(key, value);
    }

    public int getInt(String key, int defaultValue) {
        return getData().getInt(key, defaultValue);
    }

    public void putFloat(String key, float value) {
        getData().putFloat(key, value);
    }

    public float getFloat(String key, float defaultValue) {
        return getData().getFloat(key, defaultValue);
    }

    public void putLong(String key, long value) {
        getData().putLong(key, value);
    }

    public long getLong(String key, long defaultValue) {
        return getData().getLong(key, defaultValue);
    }

    public void putDouble(String key, double value) {
        getData().putDouble(key, value);
    }

    public double getDouble(String key, double defaultValue) {
        return getData().getDouble(key, defaultValue);
    }

    public void putString(String key, String value) {
        getData().putString(key, value);
    }

    public String getString(String key, String defaultValue) {
        return getData().getString(key, defaultValue);
    }

//    public <T> void putKey(String key, T value) {
//        if (value instanceof String) {
//            getData().putString(key, (String) value);
//        }
//    }

//    public <T> T getKey(String key, T defaultValue) {
//        T value = null;
//        if (defaultValue instanceof String) {
//            value = (T) getData().getString(key, (String) defaultValue);
//        }
//        return value;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.action);
        dest.writeParcelable(this.message, 0);
    }

    public GodIntent() {
        message = Message.obtain();
    }

    public GodIntent(String action, Message message) {
        this.action = action;
        this.message = message;
    }

    private GodIntent(@NonNull Parcel in) {
        this.action = in.readString();
        this.message = in.readParcelable(getClass().getClassLoader());
    }

    public static final Creator<GodIntent> CREATOR = new Creator<GodIntent>() {
        @NonNull
        @Override
        public GodIntent createFromParcel(@NonNull Parcel source) {
            return new GodIntent(source);
        }

        @NonNull
        @Override
        public GodIntent[] newArray(int size) {
            return new GodIntent[size];
        }
    };

    @NotNull
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (action != null) {
            b.append("{ action=");
            b.append(action);
        }
        if (message != null) {
            b.append(" message=");
            b.append(message);
        }
        b.append(" }");
        return b.toString();
    }
}
