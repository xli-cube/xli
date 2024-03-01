package com.xli.dto.component;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TreeNode<T> {

    private String id;

    private String text;

    private String pid;

    private List<TreeNode<T>> children;

    //存放节点中其他元素
    private Map<String, Object> extend;

    public TreeNode() {
        this.extend =new HashMap<>();
        this.children = new ArrayList<>();
    }

    public TreeNode(String id, String text, String pid) {
        this.id = id;
        this.text = text;
        this.pid = pid;
        this.extend =new HashMap<>();
        this.children = new ArrayList<>();
    }

    public TreeNode(String id, String text, String pid, Map<String, Object> extend) {
        this.id = id;
        this.text = text;
        this.pid = pid;
        this.extend = extend;
        this.children = new ArrayList<>();
    }
}
