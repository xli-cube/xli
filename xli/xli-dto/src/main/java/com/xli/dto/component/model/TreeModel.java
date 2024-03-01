package com.xli.dto.component.model;

import cn.hutool.core.util.StrUtil;
import com.xli.dto.component.TreeNode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class TreeModel<T> {

    private final List<TreeNode<T>> tempList;

    @Getter
    private List<TreeNode<T>> data;

    protected TreeModel() {
        tempList = this.fetch(null);
        if (tempList != null && !tempList.isEmpty()) {
            data = new ArrayList<>();

            for (TreeNode<T> treeNode : tempList) {
                if (StrUtil.isBlankIfStr(treeNode.getPid())) {
                    treeNode.setChildren(fetchChild(treeNode));
                    this.data.add(treeNode);
                }
            }
        }
    }

    public abstract List<TreeNode<T>> fetch(TreeNode<T> treeNode);

    public List<TreeNode<T>> fetchChild(TreeNode<T> treeNodeP) {
        List<TreeNode<T>> childNodeList = new ArrayList<>();
        if (treeNodeP != null) {
            for (TreeNode<T> treeNode : tempList) {
                if (treeNodeP.getId().equals(treeNode.getPid())) {
                    childNodeList.add(treeNode);
                }
            }
        }
        return childNodeList;
    }
}
