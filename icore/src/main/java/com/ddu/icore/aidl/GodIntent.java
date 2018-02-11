package com.ddu.icore.aidl;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by yzbzz on 16/5/13.
 */
public class GodIntent implements Parcelable {

    private int action;
    private Bundle bundle = new Bundle();

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void putInt(String key, int value) {
        bundle.putInt(key, value);
    }

    public int getInt(String key) {
        return bundle.getInt(key);
    }

    public void putLong(String key, long value) {
        bundle.putLong(key, value);
    }

    public void putString(String key, String value) {
        bundle.putString(key, value);
    }

    public String getString(String key) {
        return bundle.getString(key, "");
    }

    public <T> T getKey(String key) {
        return (T) bundle.get(key);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.action);
        dest.writeBundle(this.bundle);
    }

    public GodIntent() {
    }

    protected GodIntent(@NonNull Parcel in) {
        this.action = in.readInt();
        this.bundle = in.readBundle();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("action: " + action);
        sb.append(bundle.toString());
        return sb.toString();
    }
}
