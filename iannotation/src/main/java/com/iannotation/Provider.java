package com.iannotation;

import java.util.List;

public interface Provider {
    List<Class<?>> provide(String url);
}
