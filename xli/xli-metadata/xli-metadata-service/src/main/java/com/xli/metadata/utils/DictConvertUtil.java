package com.xli.metadata.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.xli.metadata.annotation.DictConvert;
import com.xli.metadata.codeitems.entity.CodeItems;
import com.xli.metadata.codeitems.service.ICodeItemsService;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DictConvertUtil {

    public static <T> void convertToDictionary(T data) {
        convertToDictionary(data, null);
    }

    public static <T> void convertToDictionary(T data, Consumer<T> consumer) {
        if (Objects.isNull(data)) {
            return;
        }
        convert(data);
        if (consumer != null) {
            consumer.accept(data);
        }
    }

    public static <T> void convertToDictionaryList(List<T> data) {
        convertToDictionaryList(data, null);
    }

    public static <T> void convertToDictionaryList(List<T> data, Consumer<T> consumer) {
        if (Objects.isNull(data) || CollUtil.isEmpty(data)) {
            return;
        }
        data.parallelStream().forEach(d -> {
            convert(d);
            if (consumer != null) {
                consumer.accept(d);
            }
        });
    }

    private static <T> void convert(T data) {
        // 获取当前类的所有字段
        Field[] fields = data.getClass().getDeclaredFields();
        // 过滤 static、 final、private static final字段
        final List<Field> filteredFields = Stream.of(fields).filter(f -> !(f.getModifiers() == Modifier.FINAL || f.getModifiers() == Modifier.STATIC || f.getModifiers() == (Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL) || f.getAnnotation(DictConvert.class) == null)).collect(Collectors.toList());
        for (Field f : filteredFields) {
            // 获取当前字段注解
            DictConvert annotation = f.getAnnotation(DictConvert.class);
            // 没有加注解的字段不处理
            if (annotation == null) {
                continue;
            }
            // 字典类型
            String dictType = annotation.value();
            // 没有配置字典名称，跳过
            if (StrUtil.isEmpty(dictType)) {
                continue;
            }
            // 反射获取字段值
            Object value;
            // 如果是引用其他字段则值从其他字段取
            if (StrUtil.isNotEmpty(annotation.refField())) {
                value = ReflectUtil.getFieldValue(data, annotation.refField());
            } else {
                // 否则获取当前字段值
                value = ReflectUtil.getFieldValue(data, f);
            }
            // 转换字典时字段值为空 不进行后续处理
            if (value == null) {
                continue;
            }
            // 类型
            final Class<?> classType = value.getClass();
            // 如果不是基本类型
            if (!ClassUtil.isBasicType(classType) && classType != String.class) {
                // 是List 循环则递归调用
                if (value instanceof List) {
                    for (Object o : (List) value) {
                        convert(o);
                    }
                } else {
                    // 不是 List 则视为对象反射调用
                    convert(value);
                }
            }
            // 获取字典的对应 映射关系 （建议此处做缓存提高转换速度）
            ICodeItemsService iCodeItemsService = SpringUtil.getBean(ICodeItemsService.class);
            final List<CodeItems> items = iCodeItemsService.getCodeItemsByCodeName(dictType);
            // 是否匹配到了字典中的值
            boolean isMatchSuccess = false;
            // 获取当前字典值
            final String beanValue = Convert.toStr(value);
            // 支持类似 , 逗号隔开的字典转换, 如果需要支持其他 DictConvert#delimiter() 可在此设置
            // eg : 兴趣爱好 （足球,篮球,奥利给）
            // 转换后则为 （football,basketball,aoligei）
            final String delimiter = annotation.delimiter();
            final List<String> beanValues = StrUtil.splitTrim(beanValue, delimiter);
            // 逗号隔开字典转换支持
            if (CollUtil.isNotEmpty(beanValues) && beanValues.size() > 1) {
                final Map<String, String> dictMap = items.stream().collect(Collectors.toMap(CodeItems::getItem_value, CodeItems::getItem_text));
                final List<String> matchesDict = beanValues.stream().filter(dictMap::containsKey).map(dm -> Objects.nonNull(dictMap.get(dm)) ? dictMap.get(dm) : "").collect(Collectors.toList());
                if (CollUtil.isNotEmpty(matchesDict)) {
                    isMatchSuccess = true;
                    ReflectUtil.setFieldValue(data, f, CollUtil.join(matchesDict, delimiter));
                }
            } else {
                for (CodeItems item : items) {
                    if (Objects.equals(Convert.toStr(value), item.getItem_value())) {
                        ReflectUtil.setFieldValue(data, f, Objects.nonNull(item.getItem_text()) ? item.getItem_text() : value);
                        isMatchSuccess = true;
                        break;
                    }
                }
            }
            // 如果匹配不到字典中的值 且 字段中明确表示如果匹配不到就置为NULL
            if (!isMatchSuccess && annotation.ifNotMatchConvertToNull()) {
                ReflectUtil.setFieldValue(data, f, null);
            }
        }
    }
}
