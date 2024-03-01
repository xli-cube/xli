package com.xli.metadata.codeitems.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xli.dto.component.TreeNode;
import com.xli.dto.component.model.TableModel;
import com.xli.dto.component.model.TreeModel;
import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import com.xli.dto.validation.group.IGroup;
import com.xli.metadata.codeitems.entity.CodeItems;
import com.xli.metadata.codeitems.entity.dto.CodeItemsDTO;
import com.xli.metadata.codeitems.entity.mapper.ICodeItemsMapper;
import com.xli.metadata.codeitems.entity.vo.CodeItemsVO;
import com.xli.metadata.codeitems.service.ICodeItemsService;
import com.xli.metadata.codemain.entity.CodeMain;
import com.xli.metadata.codemain.service.ICodeMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/codeItems")
public class CodeItemsController {

    @Autowired
    ICodeMainService iCodeMainService;

    @Autowired
    ICodeItemsService iCodeItemsService;

    @PostMapping(value = "/add")
    public ResultVO<String> add(@RequestBody @Validated(IGroup.add.class) CodeItemsDTO dto) {
        if (iCodeItemsService.insert(ICodeItemsMapper.INSTANCE.toEntity(dto))) {
            return new ResultVO<>(Status.SUCCESS, "添加成功");
        }
        return new ResultVO<>(Status.FAILED, "添加失败");
    }

    @PostMapping(value = "/delete")
    public ResultVO<String> delete(@RequestBody List<String> ids) {
        int i = 0;
        for (String id : ids) {
            if (iCodeItemsService.delete(Long.valueOf(id))) {
                i++;
            }
        }
        return new ResultVO<>(Status.SUCCESS, i + "条数据删除成功");
    }

    @PostMapping(value = "/update")
    public ResultVO<String> update(@RequestBody @Validated(IGroup.update.class) CodeItemsDTO dto) {
        if (iCodeItemsService.update(ICodeItemsMapper.INSTANCE.toEntity(dto))) {
            return new ResultVO<>(Status.SUCCESS, "更新成功");
        }
        return new ResultVO<>(Status.FAILED, "更新失败");
    }

    @PostMapping(value = "/search")
    public ResultVO<String> search(@RequestBody @Validated(IGroup.search.class) CodeItemsDTO dto) {
        TableModel<CodeItemsVO> tableModel = new TableModel<>() {
            @Override
            public List<CodeItemsVO> fetch() {
                QueryWrapper<CodeItems> qw = new QueryWrapper<>();
                qw.lambda()
                        .eq(StrUtil.isNotBlank(dto.getCodeId()), CodeItems::getCode_id, dto.getCodeId())
                        .eq(StrUtil.isNotBlank(dto.getPid()), CodeItems::getPid, dto.getPid());
                Page<CodeItems> page = iCodeItemsService.findList(qw, dto.getCurrent(), dto.getPageSize());
                this.setTotal(page.getTotal());
                return page.convert(ICodeItemsMapper.INSTANCE::toVO).getRecords();
            }
        };
        return new ResultVO(Status.SUCCESS, "查询成功", tableModel);
    }

    @PostMapping(value = "/fetchTree")
    public ResultVO<String> fetchTree(@RequestBody @Validated(IGroup.detail.class) CodeItemsDTO dto) {
        CodeMain codeMain = iCodeMainService.find(Long.valueOf(dto.getCodeId()));
        TreeModel treeModel = new TreeModel() {
            @Override
            public List<TreeNode> fetch(TreeNode treeNode) {
                List<TreeNode> treeNodeList = new ArrayList<>();
                if (treeNode == null) {
                    List<CodeItems> codeItemsList = iCodeItemsService.getCodeItemsByCodeId(Long.valueOf(dto.getCodeId()));
                    for (CodeItems codeItems : codeItemsList) {
                        TreeNode childNode = new TreeNode();
                        childNode.setId(codeItems.getId().toString());
                        childNode.setText(codeItems.getItem_text());
                        childNode.setPid(codeItems.getPid() == null ? "" : codeItems.getPid().toString());
                        treeNodeList.add(childNode);
                    }
                }
                return treeNodeList;
            }
        };
        return new ResultVO(Status.SUCCESS, "查询成功", treeModel);
    }
}
