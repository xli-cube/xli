package com.xli.soa.role.framerolecategory.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xli.dto.component.TreeNode;
import com.xli.dto.component.model.TableModel;
import com.xli.dto.component.model.TreeModel;
import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import com.xli.dto.validation.group.IGroup;
import com.xli.soa.role.framerolecategory.entity.FrameRoleCategory;
import com.xli.soa.role.framerolecategory.entity.dto.RoleCategoryAddDTO;
import com.xli.soa.role.framerolecategory.entity.dto.RoleCategoryDTO;
import com.xli.soa.role.framerolecategory.entity.dto.RoleCategorySearchDTO;
import com.xli.soa.role.framerolecategory.entity.dto.RoleCategoryUpdateDTO;
import com.xli.soa.role.framerolecategory.entity.mapper.IFrameRoleCategoryMapper;
import com.xli.soa.role.framerolecategory.entity.vo.RoleCategoryVO;
import com.xli.soa.role.framerolecategory.service.IFrameRoleCategoryService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/frameRoleCategory")
public class FrameRoleCategoryController {

    @Autowired
    private IFrameRoleCategoryService iFrameRoleCategoryService;

    @PostMapping(value = "/add")
    public ResultVO<String> add(@RequestBody @Validated(IGroup.add.class) RoleCategoryAddDTO dto) {
        QueryWrapper<FrameRoleCategory> qw = new QueryWrapper<>();
        qw.lambda().eq(FrameRoleCategory::getCategory_name, dto.getCategoryName());
        if (iFrameRoleCategoryService.findCount(qw) <= 0) {
            if (iFrameRoleCategoryService.insert(IFrameRoleCategoryMapper.INSTANCE.toEntity(dto))) {
                return new ResultVO<>(Status.SUCCESS, "添加成功");
            }
        } else {
            return new ResultVO<>(Status.FAILED, "添加失败，分类名已存在");
        }
        return new ResultVO<>(Status.FAILED, "添加失败");
    }

    @PostMapping(value = "/delete")
    public ResultVO<String> delete(@RequestBody List<String> ids) {
        int i = 0;
        for (String id : ids) {
            if (iFrameRoleCategoryService.delete(Long.parseLong(id))) {
                i++;
            }
        }
        return new ResultVO<>(Status.SUCCESS, i + "条数据删除成功");
    }

    @PostMapping(value = "/update")
    public ResultVO<String> update(@RequestBody @Validated(IGroup.update.class) RoleCategoryUpdateDTO dto) {
        QueryWrapper<FrameRoleCategory> qw = new QueryWrapper<>();
        qw.lambda().eq(FrameRoleCategory::getCategory_name, dto.getCategoryName())
                .ne(FrameRoleCategory::getId, dto.getId());
        if (iFrameRoleCategoryService.findCount(qw) <= 0) {
            if (iFrameRoleCategoryService.update(IFrameRoleCategoryMapper.INSTANCE.toEntity(dto))) {
                return new ResultVO<>(Status.SUCCESS, "更新成功");
            }
        } else {
            return new ResultVO<>(Status.FAILED, "更新失败，分类名不能重复");
        }
        return new ResultVO<>(Status.FAILED, "更新失败");
    }

    @PostMapping(value = "/detail/{id}")
    public ResultVO<RoleCategoryVO> detail(@PathVariable("id") @NotBlank String id) {
        FrameRoleCategory frameRoleCategory = iFrameRoleCategoryService.find(Long.valueOf(id));
        if (frameRoleCategory != null) {
            return new ResultVO<>(Status.SUCCESS, "查询成功", IFrameRoleCategoryMapper.INSTANCE.toVO(frameRoleCategory));
        }
        return new ResultVO<>(Status.SUCCESS, "查询失败");
    }

    @PostMapping(value = "/search")
    public ResultVO<TableModel<RoleCategoryVO>> search(@RequestBody @Validated(IGroup.search.class) RoleCategorySearchDTO dto) {
        TableModel<RoleCategoryVO> tableModel = new TableModel<>() {
            @Override
            public List<RoleCategoryVO> fetch() {
                QueryWrapper<FrameRoleCategory> qw = new QueryWrapper<>();

                if (StrUtil.isNotBlank(dto.getFilter())) {
                    boolean isAscend = false;
                    if (StrUtil.isNotBlank(dto.getSort())) {
                        isAscend = "ascend".equals(dto.getSort());
                    }
                    qw.orderBy(true, isAscend, dto.getFilter());
                }

                Page<FrameRoleCategory> page = iFrameRoleCategoryService.findList(qw, dto.getCurrent(), dto.getPageSize());
                this.setTotal(page.getTotal());
                return page.convert(IFrameRoleCategoryMapper.INSTANCE::toVO).getRecords();
            }
        };
        return new ResultVO<>(Status.SUCCESS, "查询成功", tableModel);
    }

    @PostMapping(value = "/fetchTree")
    public ResultVO<TreeModel<RoleCategoryVO>> fetchTree() {
        TreeModel<RoleCategoryVO> treeModel = new TreeModel<>() {
            @Override
            public List<TreeNode<RoleCategoryVO>> fetch(TreeNode<RoleCategoryVO> treeNode) {
                List<TreeNode<RoleCategoryVO>> treeNodeList = new ArrayList<>();
                if (treeNode == null) {
                    List<FrameRoleCategory> frameRoleCategoryList = iFrameRoleCategoryService.findList(new QueryWrapper<>());
                    for (FrameRoleCategory frameRoleCategory : frameRoleCategoryList) {
                        TreeNode<RoleCategoryVO> node = new TreeNode<>();
                        node.setId(frameRoleCategory.getId().toString());
                        node.setText(frameRoleCategory.getCategory_name());
                        node.setPid("-1");
                        treeNodeList.add(node);
                    }
                }
                return treeNodeList;
            }
        };
        return new ResultVO<>(Status.SUCCESS, "查询成功", treeModel);
    }
}
