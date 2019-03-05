package com.iannotation;

import java.util.List;

public interface IElementProvider {
    List<Tuple<String, Class<?>>> provide(String url);
}
