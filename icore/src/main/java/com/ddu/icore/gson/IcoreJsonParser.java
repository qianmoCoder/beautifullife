package com.ddu.icore.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

/**
 * Created by yzbzz on 2018/5/11.
 */
public class IcoreJsonParser<T> {

    private HashMap<String, Class<?>> typeClassMap = new HashMap<>();
    private Gson parseGson;

    private String typeElementName;
    private String typeElementValue;

    public String getTypeElementName() {
        return typeElementName == null ? "" : typeElementName;
    }

    public void setTypeElementName(String typeElementName) {
        this.typeElementName = typeElementName;
    }

    public String getTypeElementValue() {
        return typeElementValue == null ? "" : typeElementValue;
    }

    public void setTypeElementValue(String typeElementValue) {
        this.typeElementValue = typeElementValue;
    }

    public HashMap<String, Class<?>> getTypeClassMap() {
        return typeClassMap;
    }

    public Gson getParseGson() {
        return parseGson;
    }

    public void setParseGson(Gson parseGson) {
        this.parseGson = parseGson;
    }

    public boolean checkHasRegistered(String typeValue) {
        return typeClassMap.containsKey(typeValue);
    }

    public void build() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        parseGson = gsonBuilder.create();
    }


}
