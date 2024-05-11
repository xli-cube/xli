package com.xli.cache.anno;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cached {

    /**
     * 如果要使用多后端缓存系统，可以在配置中设置多“缓存区域”，此属性指定要使用的“缓存区域”的名称。
     *
     * @return 缓存区域的名称
     */
    String area() default CacheConstants.DEFAULT_AREA;

    /**
     * 此缓存实例的名称，可选。如果未指定，将自动生成一个。该名称用于显示统计信息，并在使用远程缓存时作为键前缀的一部分。
     * 不要为具有相同区域的不同@Cached注释指定相同的名称。
     *
     * @return 缓存的名称
     */
    String name() default CacheConstants.UNDEFINED_STRING;


    /**
     * 指定是否启用方法缓存。如果设置为false，则可以在线程上下文中使用CacheContext.enableCache(Supplier<T> callback)
     *
     * @return 是否启用了方法缓存
     */
    boolean enabled() default CacheConstants.DEFAULT_ENABLED;

    /**
     * 指定过期的时间单位（默认秒）。
     *
     * @return 到期时间的时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 到期时间。如果不存在属性值，则使用全局配置，如果未定义全局配置，请改用infinity。
     *
     * @return 到期时间
     */
    int expire() default CacheConstants.UNDEFINED_INT;

    /**
     * 用于指定本地缓存的过期时间，当cacheType=CacheType.BOTH时，如果不存在，则使用“expire”。
     *
     * @return 本地缓存过期时间
     */
    int localExpire() default CacheConstants.UNDEFINED_INT;

    /**
     * 缓存实例的类型。可以是CacheType.REMOTE、CacheType.LOCAL、CacheType.BOTH。
     * 当值为CacheType.BOTH时，创建两级缓存（local+remote）。
     *
     * @return 缓存方法的缓存类型
     */
    CacheType cacheType() default CacheType.REMOTE;

    /**
     * 如果cacheType为CacheType.BOTH，并且远程缓存支持广播（或存在 BroadcastManager bean），则在执行put/remove操作后使所有进程的本地缓存失效。
     *
     * @return 是否同步本地缓存
     */
    boolean syncLocal() default false;

    /**
     * 当cacheType为CacheType.LOCAL或CacheType.BOTH时，指定本地内存中的最大元素。如果属性值不存在，则使用全局配置，如果未定义全局配置，请改用CacheConstants.DEFAULT_LOCAL_LIMIT。
     *
     * @return LOCAL/BOTH缓存的局部最大元素
     */
    int localLimit() default CacheConstants.UNDEFINED_INT;

    /**
     * 当cacheType为CacheType.REMOTE或CacheType.BOTH时，指定远程缓存的序列化策略。内置的serialPolicy是SerialPolicy.JAVA或SerialPolicy.KRYO。
     * 如果不存在属性值，则使用全局配置，如果未定义全局配置，请改用SerialPolicy.JAVA。
     *
     * @return 缓存值的序列化策略名称
     */
    String serialPolicy() default CacheConstants.UNDEFINED_STRING;

    /**
     * 指定密钥转换器。用于转换复杂键对象。内置的keyConvertor是KeyConvertor.FASTJSON或KeyConvertor.NONE。
     * NONE表示不转换，FASTJSON会使用fastjson将key对象转换为字符串。如果不存在属性值，请使用全局配置。
     *
     * @return 缓存键的转换器名称
     */
    String keyConvertor() default CacheConstants.UNDEFINED_STRING;

    /**
     * 通过表达式脚本指定键，可选。如果未指定，请使用目标方法和keyConvertor的所有参数来生成一个。
     *
     * @return 指定键的表达式脚本
     */
    String key() default CacheConstants.UNDEFINED_STRING;

    /**
     * 指定是否应缓存null值。
     *
     * @return 是否应缓存null值
     */
    boolean cacheNullValue() default CacheConstants.DEFAULT_CACHE_NULL_VALUE;

    /**
     * 用于调节方法缓存的表达式脚本，当评估结果为false时，不使用缓存。评估在实际方法调用之前进行。
     *
     * @return 默认表达式
     */
    String condition() default CacheConstants.UNDEFINED_STRING;

    /**
     * 用于调节缓存更新方法的表达式脚本，当评估结果为false时，缓存更新操作被否决。评估发生在实际方法调用之后，因此我们可以在脚本中引用结果 。
     *
     * @return 默认表达式
     */
    String postCondition() default CacheConstants.UNDEFINED_STRING;
}
