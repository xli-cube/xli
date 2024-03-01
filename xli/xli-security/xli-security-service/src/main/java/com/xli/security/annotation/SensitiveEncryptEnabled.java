package com.xli.security.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SensitiveEncryptEnabled {

    /**
     * 是否开启加解密和脱敏模式
     *
     * @return SensitiveEncryptEnabled
     */
    boolean value() default true;
}
