package com.iannotation;

import java.util.List;

public interface Provider {
    List<Tuple<String, Class<?>>> provide(String url);
}
