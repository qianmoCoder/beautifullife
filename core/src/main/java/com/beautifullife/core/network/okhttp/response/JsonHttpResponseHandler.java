package com.beautifullife.core.network.okhttp.response;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class JsonHttpResponseHandler<T> extends SyncHttpResponseHandler {

    private Gson mGson;

    private Type type;

    public JsonHttpResponseHandler() {
        type = getSuperclassTypeParameter(getClass());
        mGson = new Gson();
    }

    private Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if ((superclass instanceof Class)) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public final Type getType() {
        return type;
    }

    @Override
    public void onUIResponse(String string) {
        try {
            Type type = getType();
            T t = mGson.fromJson(string, type);
            // gson.fromJson(response.body().charStream(), type.getClass());
            onUIResponse(t);
        } catch (Exception e) {
            onUIError(e);
        }
    }

    public abstract void onUIResponse(T response);

}
