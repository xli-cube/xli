package com.xli.dto.component.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Data
public abstract class TableTreeModel<T> {

    private boolean success;

    private Long total;

    private List<T> data;

    private List<T> tempList;

    protected TableTreeModel() {
        success = true;
        total = 0L;
        tempList = this.fetch();
        if (tempList != null && !tempList.isEmpty()) {
            data = new ArrayList<>();

            for (T t : tempList) {
                if (existField(t, "pid")) {
                    if (checkFieldNotNull(t, "pid")) {
                        addChildrenField(t,fetchChild(t));
                        data.add(t);
                    }
                } else {
                    //如果没有pid，说明查出来的数据并不具备树节点，则忽略
                    data.add(t);
                }
            }
        }
    }

    public abstract List<T> fetch();

    public List<T> fetchChild(T t) {
        List<T> childList = new ArrayList<>();
        if (t != null) {
            for (T child : tempList) {
                if (checkFieldNotNull(t, "id") && checkFieldNotNull(child, "pid")) {
                    if (Objects.equals(getFieldValue(child, "pid"), getFieldValue(t, "id"))) {
                        childList.add(child);
                    }
                }
            }
        }
        return childList;
    }

    private boolean existField(T obj, String fieldName) {
        try {
            obj.getClass().getDeclaredField(fieldName);
            return true; // 字段存在
        } catch (NoSuchFieldException e) {
            e.printStackTrace(); // 处理异常或者适当的日志记录
            return false; // 字段不存在
        }
    }

    private boolean checkFieldNotNull(T obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(obj);
            return value != null && !StrUtil.isBlankIfStr(value); // 检查字段值是否不为空
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // 处理异常或者适当的日志记录
            return false;
        }
    }

    private Object getFieldValue(T obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // 处理异常或者适当的日志记录
            return null;
        }
    }


    private void addChildrenField(T obj,List<T> childList) {
        try {
            Field field = obj.getClass().getDeclaredField("children");
            field.setAccessible(true);
            // Assuming "TreeNode" is the type of your children field
            // You might need to replace "TreeNode" with the actual type of your children
            field.set(obj, childList); // Create a new children field
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // 处理异常或者适当的日志记录
        }
    }
}
