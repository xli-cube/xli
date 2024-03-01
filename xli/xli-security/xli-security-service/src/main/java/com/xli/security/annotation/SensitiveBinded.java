package com.xli.security.annotation;

import com.xli.security.type.SensitiveType;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SensitiveBinded {

    /**
     * 该属性从哪个字段取得
     *
     * @return 返回字段名
     */
    String bindField();

    /**
     * 脱敏类型
     * 不同的脱敏类型置换*的方式不同
     *
     * @return SensitiveType
     */
    SensitiveType value();

}
