package com.xli.metadata.utils.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CodeItemValidator.class})
public @interface CodeItem {

    /**
     * 是否强制校验
     *
     * @return 是否强制校验的boolean值
     */
    boolean required() default true;

    /**
     * 字典名
     */
    String name() default "";

    /**
     * 默认字典值，为空时查数据库代码项
     */
    String[] values() default {};

    /**
     * 校验不通过时的报错信息
     *
     * @return 校验不通过时的报错信息
     */
    String message() default "字典值错误，不在允许的范围内";

    /**
     * 将validator进行分类，不同的类group中会执行不同的validator操作
     *
     * @return validator的分类类型
     */
    Class<?>[] groups() default {};

    /**
     * 主要是针对bean，很少使用
     *
     * @return 负载
     */
    Class<? extends Payload>[] payload() default {};
}
