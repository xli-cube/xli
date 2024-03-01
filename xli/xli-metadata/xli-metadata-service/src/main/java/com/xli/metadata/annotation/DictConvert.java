package com.xli.metadata.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DictConvert {

    /**
     * 字典类型 dict_type 如果是 List 则无需指定 该值
     */
    String value() default "";

    /**
     * 填写该字段后 该字段会拿到 refField 配置的字段的值并且根据字典转换
     */
    String refField() default "";

    /**
     * 如果转换的值不匹配是否要转换为NULL
     */
    boolean ifNotMatchConvertToNull() default false;

    /**
     * 带逗号的多字典 分隔符 默认逗号
     */
    String delimiter() default ",";

}
