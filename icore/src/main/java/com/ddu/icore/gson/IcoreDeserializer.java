package com.ddu.icore.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by yzbzz on 2018/5/11.
 */
public class IcoreDeserializer<T> implements JsonDeserializer<T> {

    private final IcoreJsonParser<T> icoreJsonParser;

    public IcoreDeserializer() {
        icoreJsonParser = new IcoreJsonParser<>();
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement jsonElement = jsonObject.get(icoreJsonParser.getTypeElementName());
        String contentType = getString(jsonElement);
        T item;

        return null;
    }

    private String getString(JsonElement jsonElement) {
        return jsonElement.isJsonNull() ? "" : jsonElement.getAsString();
    }
}
