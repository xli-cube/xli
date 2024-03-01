package com.xli.soa.role.framerolecategory.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xli.dto.component.TreeNode;
import com.xli.dto.component.model.TableModel;
import com.xli.dto.component.model.TreeModel;
import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import com.xli.dto.validation.group.IGroup;
import com.xli.metadata.utils.DictConvertUtil;
import com.xli.soa.role.framerolecategory.entity.FrameRoleCategory;
import com.xli.soa.role.framerolecategory.entity.dto.FrameRoleCategoryDTO;
import com.xli.soa.role.framerolecategory.entity.mapper.IFrameRoleCategoryMapper;
import com.xli.soa.role.framerolecategory.entity.vo.FrameRoleCategoryVO;
import com.xli.soa.role.framerolecategory.service.IFrameRoleCategoryService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/frameRoleCategory")
public class FrameRoleCategoryController {

    @Autowired
    private IFrameRoleCategoryService iFrameRoleCategoryService;

    @PostMapping(value = "/add")
    public ResultVO add(@RequestBody @Validated(IGroup.add.class) FrameRoleCategoryDTO dto) {
        QueryWrapper<FrameRoleCategory> qw = new QueryWrapper<>();
        qw.lambda().eq(FrameRoleCategory::getCategory_name, dto.getCategoryName());
        if (iFrameRoleCategoryService.findCount(qw) <= 0) {
            if (iFrameRoleCategoryService.insert(IFrameRoleCategoryMapper.INSTANCE.toEntity(dto))) {
                return new ResultVO(Status.SUCCESS, "添加成功");
            }
        } else {
            return new ResultVO(Status.FAILED, "添加失败，分类名已存在");
        }
        return new ResultVO(Status.FAILED, "添加失败");
    }

    @PostMapping(value = "/delete")
    public ResultVO delete(@RequestBody List<Long> ids) {
        int i = 0;
        for (long id : ids) {
            if (iFrameRoleCategoryService.delete(id)) {
                i++;
            }
        }
        return new ResultVO(Status.SUCCESS, i + "条数据删除成功");
    }

    @PostMapping(value = "/update")
    public ResultVO update(@RequestBody @Validated(IGroup.update.class) FrameRoleCategoryDTO dto) {
        QueryWrapper<FrameRoleCategory> qw = new QueryWrapper<>();
        qw.lambda().eq(FrameRoleCategory::getCategory_name, dto.getCategoryName())
                .ne(FrameRoleCategory::getId, dto.getId());
        if (iFrameRoleCategoryService.findCount(qw) <= 0) {
            if (iFrameRoleCategoryService.update(IFrameRoleCategoryMapper.INSTANCE.toEntity(dto))) {
                return new ResultVO(Status.SUCCESS, "更新成功");
            }
        } else {
            return new ResultVO(Status.FAILED, "更新失败，分类名不能重复");
        }
        return new ResultVO(Status.FAILED, "更新失败");
    }

    @PostMapping(value = "/detail")
    public ResultVO detail(@RequestBody @NotBlank(message = "id不能为空") String id) {
        FrameRoleCategoryVO frameRoleCategoryVO = IFrameRoleCategoryMapper.INSTANCE.toVO(iFrameRoleCategoryService.find(Long.valueOf(id)));
        DictConvertUtil.convertToDictionary(frameRoleCategoryVO);//代码项转换
        return new ResultVO(Status.SUCCESS, "查询成功", frameRoleCategoryVO);
    }

    @PostMapping(value = "/search")
    public ResultVO<TableModel> search(@RequestBody @Validated(IGroup.search.class) FrameRoleCategoryDTO dto) {
        TableModel<FrameRoleCategoryVO> tableModel = new TableModel() {
            @Override
            public List<FrameRoleCategoryVO> fetch() {
                QueryWrapper<FrameRoleCategory> qw = new QueryWrapper<>();
                Page<FrameRoleCategory> page = iFrameRoleCategoryService.findList(qw, dto.getCurrent(), dto.getPageSize());
                this.setTotal(page.getTotal());
                return page.convert(IFrameRoleCategoryMapper.INSTANCE::toVO).getRecords();
            }
        };
        return new ResultVO(Status.SUCCESS, "查询成功", tableModel);
    }

    @PostMapping(value = "/fetchTree")
    public ResultVO fetchTree() {
        TreeModel treeModel = new TreeModel() {
            @Override
            public List<TreeNode> fetch(TreeNode treeNode) {
                List<TreeNode> treeNodeList = new ArrayList<>();
                if (treeNode == null) {
                    List<FrameRoleCategory> frameRoleCategoryList = iFrameRoleCategoryService.findList(new QueryWrapper<>());
                    for (FrameRoleCategory frameRoleCategory : frameRoleCategoryList) {
                        TreeNode node = new TreeNode();
                        node.setId(frameRoleCategory.getId().toString());
                        node.setText(frameRoleCategory.getCategory_name());
                        node.setPid("-1");
                        treeNodeList.add(node);
                    }
                }
                return treeNodeList;
            }
        };
        return new ResultVO(Status.SUCCESS, "查询成功", treeModel);
    }
}
