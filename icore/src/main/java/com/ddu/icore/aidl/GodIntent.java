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
    private Bundle bundle;

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

    public void putString(String key, String value) {
        bundle.putString(key, value);
    }

    public String getString(String key) {
        return bundle.getString(key, "");
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
}
