package com.ddu.config.url;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yzbzz on 2017/12/21.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IDnsConfig {

    String test() default "";

    String release();

    String qa() default "";

    String pre() default "";

}
