package com.xli.soa.role.framerole.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xli.dto.component.model.TableModel;
import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import com.xli.dto.validation.group.IGroup;
import com.xli.soa.role.framerole.entity.FrameRole;
import com.xli.soa.role.framerole.entity.dto.RoleAddDTO;
import com.xli.soa.role.framerole.entity.dto.RoleSearchDTO;
import com.xli.soa.role.framerole.entity.dto.RoleUpdateDTO;
import com.xli.soa.role.framerole.entity.mapper.IFrameRoleMapper;
import com.xli.soa.role.framerole.entity.vo.RoleVO;
import com.xli.soa.role.framerole.service.IFrameRoleService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frameRole")
public class FrameRoleController {

    @Autowired
    private IFrameRoleService iFrameRoleService;

    @PostMapping(value = "/add")
    public ResultVO<String> add(@RequestBody @Validated(IGroup.add.class) RoleAddDTO dto) {
        QueryWrapper<FrameRole> qw = new QueryWrapper<>();
        qw.lambda().eq(FrameRole::getRole_name, dto.getRoleName());
        if (iFrameRoleService.findCount(qw) <= 0) {
            if (iFrameRoleService.insert(IFrameRoleMapper.INSTANCE.toEntity(dto))) {
                return new ResultVO<>(Status.SUCCESS, "添加成功");
            }
        } else {
            return new ResultVO<>(Status.FAILED, "添加失败，角色名已存在");
        }
        return new ResultVO<>(Status.FAILED, "添加失败");
    }

    @PostMapping(value = "/delete")
    public ResultVO<String> delete(@RequestBody List<String> ids) {
        int i = 0;
        for (String id : ids) {
            if (iFrameRoleService.delete(Long.parseLong(id))) {
                i++;
            }
        }
        return new ResultVO<>(Status.SUCCESS, i + "条数据删除成功");
    }

    @PostMapping(value = "/update")
    public ResultVO<String> update(@RequestBody @Validated(IGroup.update.class) RoleUpdateDTO dto) {
        if (iFrameRoleService.update(IFrameRoleMapper.INSTANCE.toEntity(dto))) {
            return new ResultVO<>(Status.SUCCESS, "更新成功");
        }
        return new ResultVO<>(Status.FAILED, "更新失败");
    }

    @PostMapping(value = "/detail/{id}")
    public ResultVO<RoleVO> detail(@PathVariable("id") @NotBlank String id) {
        FrameRole frameRole = iFrameRoleService.find(Long.valueOf(id));
        if (frameRole != null) {
            return new ResultVO<>(Status.SUCCESS, "查询成功", IFrameRoleMapper.INSTANCE.toVO(frameRole));
        }
        return new ResultVO<>(Status.FAILED, "查询失败");
    }

    @PostMapping(value = "/search")
    public ResultVO<TableModel<RoleVO>> search(@RequestBody @Validated(IGroup.search.class) RoleSearchDTO dto) {
        TableModel<RoleVO> tableModel = new TableModel<>() {
            @Override
            public List<RoleVO> fetch() {
                QueryWrapper<FrameRole> qw = new QueryWrapper<>();

                if (StrUtil.isNotBlank(dto.getFilter())) {
                    boolean isAscend = false;
                    if (StrUtil.isNotBlank(dto.getSort())) {
                        isAscend = "ascend".equals(dto.getSort());
                    }
                    qw.orderBy(true, isAscend, dto.getFilter());
                }

                qw.lambda()
                        .like(StrUtil.isNotBlank(dto.getRoleName()), FrameRole::getRole_name, dto.getRoleName())
                        .eq(StrUtil.isNotBlank(dto.getCategoryId()), FrameRole::getCategory_id, dto.getCategoryId());

                Page<FrameRole> page = iFrameRoleService.findList(qw, dto.getCurrent(), dto.getPageSize());
                this.setTotal(page.getTotal());
                return page.convert(IFrameRoleMapper.INSTANCE::toVO).getRecords();
            }
        };
        return new ResultVO<>(Status.SUCCESS, "查询成功", tableModel);
    }
}
