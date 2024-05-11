package com.xli.cache.anno;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheUpdate {

    /**
     * 如果要使用多后端缓存系统，可以在配置中设置多“缓存区域”，此属性指定要使用的“缓存区域”的名称。
     *
     * @return 缓存区域的名称
     */
    String area() default CacheConstants.DEFAULT_AREA;

    /**
     * 需要更新操作的此缓存实例的名称。
     *
     * @return 需要更新操作的缓存的名称
     */
    String name();

    /**
     * 通过表达式脚本指定键，可选。如果未指定，请使用目标方法和 keyConvertor 的所有参数来生成一个。
     *
     * @return 指定键的表达式脚本
     */
    String key() default CacheConstants.UNDEFINED_STRING;

    /**
     * 通过表达式脚本指定缓存值。
     *
     * @return 指定缓存值的表达式脚本
     */
    String value();

    /**
     * 用于调节缓存操作的表达式脚本，当评估结果为false时，该操作被否决。评估发生在实际方法调用之后，因此我们可以在脚本中引用 #result 。
     */
    String condition() default CacheConstants.UNDEFINED_STRING;

    /**
     * 如果计算的键和值都是java.lang.Iterable的数组或实例，则将multi设置为true表示将K/V对更新为缓存，而不是更新单个转换后的K/V。
     */
    boolean multi() default CacheConstants.DEFAULT_MULTI;
}
