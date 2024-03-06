package com.xli.soa.ou.frameou.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xli.dto.component.TreeNode;
import com.xli.dto.component.model.TableModel;
import com.xli.dto.component.model.TreeModel;
import com.xli.dto.params.TreeNodeDTO;
import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import com.xli.dto.validation.group.IGroup;
import com.xli.metadata.utils.DictConvertUtil;
import com.xli.soa.ou.frameou.entity.FrameOu;
import com.xli.soa.ou.frameou.entity.dto.OuAddDTO;
import com.xli.soa.ou.frameou.entity.dto.OuSearchDTO;
import com.xli.soa.ou.frameou.entity.dto.OuUpdateDTO;
import com.xli.soa.ou.frameou.entity.mapper.IFrameOuMapper;
import com.xli.soa.ou.frameou.entity.vo.OuVO;
import com.xli.soa.ou.frameou.service.IFrameOuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "部门管理")
@RestController
@RequestMapping("/frameOu")
public class FrameOuController {

    @Autowired
    private IFrameOuService iFrameOuService;

    @Operation(summary = "新增部门")
    @PostMapping(value = "/add")
    public ResultVO<String> add(@RequestBody @Validated(IGroup.add.class) OuAddDTO dto) {
        if (iFrameOuService.insert(IFrameOuMapper.INSTANCE.toEntity(dto))) {
            return new ResultVO<>(Status.SUCCESS, "添加成功");
        }
        return new ResultVO<>(Status.FAILED, "添加失败");
    }

    @Operation(summary = "删除部门")
    @PostMapping(value = "/delete")
    public ResultVO<String> delete(@RequestBody List<Long> ids) {
        int i = 0;
        for (long id : ids) {
            if (iFrameOuService.delete(id)) {
                i++;
            }
        }
        return new ResultVO<>(Status.SUCCESS, i + "条数据删除成功");
    }

    @Operation(summary = "更新部门")
    @PostMapping(value = "/update")
    public ResultVO<String> update(@RequestBody @Validated(IGroup.update.class) OuUpdateDTO dto) {
        if (iFrameOuService.update(IFrameOuMapper.INSTANCE.toEntity(dto))) {
            return new ResultVO<>(Status.SUCCESS, "更新成功");
        }
        return new ResultVO<>(Status.FAILED, "更新失败");
    }

    @Operation(summary = "查询详情")
    @PostMapping(value = "/detail/{id}")
    public ResultVO<OuVO> detail(@PathVariable("id") @NotBlank String id) {
        OuVO frameOuVO = IFrameOuMapper.INSTANCE.toVO(iFrameOuService.find(Long.valueOf(id)));
        DictConvertUtil.convertToDictionary(frameOuVO);//代码项转换
        return new ResultVO<>(Status.SUCCESS, "查询成功", frameOuVO);
    }

    @Operation(summary = "查询列表")
    @PostMapping(value = "/search")
    public ResultVO<TableModel<OuVO>> search(@RequestBody @Validated(IGroup.search.class) OuSearchDTO dto) {
        TableModel<OuVO> tableModel = new TableModel<>() {
            @Override
            public List<OuVO> fetch() {
                QueryWrapper<FrameOu> qw = new QueryWrapper<>();
                qw.lambda()
                        .like(StrUtil.isNotBlank(dto.getOuName()), FrameOu::getOu_name, dto.getOuName())
                        .like(StrUtil.isNotBlank(dto.getOuCode()), FrameOu::getOu_code, dto.getOuCode())
                        .eq(StrUtil.isNotBlank(dto.getPid()), FrameOu::getPid, dto.getPid());
                Page<FrameOu> page = iFrameOuService.findList(qw, dto.getCurrent(), dto.getPageSize());
                this.setTotal(page.getTotal());
                return page.convert(IFrameOuMapper.INSTANCE::toVO).getRecords();
            }
        };
        return new ResultVO<>(Status.SUCCESS, "查询成功", tableModel);
    }

    @Operation(summary = "查询部门树")
    @PostMapping(value = "/fetchOuTree")
    public ResultVO<TreeModel<OuVO>> fetchOuTree(@RequestBody @Validated(IGroup.search.class) TreeNodeDTO dto) {
        TreeModel<OuVO> treeModel = new TreeModel<>() {
            @Override
            public List<TreeNode<OuVO>> fetch(TreeNode<OuVO> treeNode) {
                List<TreeNode<OuVO>> treeNodeList = new ArrayList<>();
                if (treeNode == null) {
                    List<FrameOu> frameOuList = iFrameOuService.findList(new QueryWrapper<>());
                    for (FrameOu frameOu : frameOuList) {
                        OuVO frameOuVO = IFrameOuMapper.INSTANCE.toVO(frameOu);
                        TreeNode<OuVO> childNode = new TreeNode<>();
                        childNode.setId(frameOuVO.getId());
                        childNode.setText(frameOuVO.getOuName());
                        childNode.setPid(frameOuVO.getPid() == null ? "" : frameOuVO.getPid());
                        treeNodeList.add(childNode);
                    }
                }
                return treeNodeList;
            }
        };
        return new ResultVO<>(Status.SUCCESS, "查询成功", treeModel);
    }
}
