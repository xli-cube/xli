package com.xli.soa.role.framerole.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xli.dto.component.model.TableModel;
import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import com.xli.dto.validation.group.IGroup;
import com.xli.metadata.utils.DictConvertUtil;
import com.xli.soa.role.framerole.entity.FrameRole;
import com.xli.soa.role.framerole.entity.dto.FrameRoleDTO;
import com.xli.soa.role.framerole.entity.mapper.IFrameRoleMapper;
import com.xli.soa.role.framerole.entity.vo.FrameRoleVO;
import com.xli.soa.role.framerole.service.IFrameRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frameRole")
public class FrameRoleController {

    @Autowired
    private IFrameRoleService iFrameRoleService;

    @PostMapping(value = "/add")
    public ResultVO add(@RequestBody @Validated(IGroup.add.class) FrameRoleDTO dto) {
        QueryWrapper<FrameRole> qw = new QueryWrapper<>();
        qw.lambda().eq(FrameRole::getRole_name, dto.getRoleName());
        if (iFrameRoleService.findCount(qw) <= 0) {
            if (iFrameRoleService.insert(IFrameRoleMapper.INSTANCE.toEntity(dto))) {
                return new ResultVO(Status.SUCCESS, "添加成功");
            }
        } else {
            return new ResultVO(Status.FAILED, "添加失败，角色名已存在");
        }
        return new ResultVO(Status.FAILED, "添加失败");
    }

    @PostMapping(value = "/delete")
    public ResultVO delete(@RequestBody List<Long> ids) {
        int i = 0;
        for (long id : ids) {
            if (iFrameRoleService.delete(id)) {
                i++;
            }
        }
        return new ResultVO(Status.SUCCESS, i + "条数据删除成功");
    }

    @PostMapping(value = "/update")
    public ResultVO update(@RequestBody @Validated(IGroup.update.class) FrameRoleDTO dto) {
        if (iFrameRoleService.update(IFrameRoleMapper.INSTANCE.toEntity(dto))) {
            return new ResultVO(Status.SUCCESS, "更新成功");
        }
        return new ResultVO(Status.FAILED, "更新失败");
    }

    @PostMapping(value = "/detail")
    public ResultVO detail(@RequestBody @Validated(IGroup.detail.class) FrameRoleDTO dto) {
        FrameRoleVO frameRoleVO = IFrameRoleMapper.INSTANCE.toVO(iFrameRoleService.find(dto.getId()));
        DictConvertUtil.convertToDictionary(frameRoleVO);//代码项转换
        return new ResultVO(Status.SUCCESS, "查询成功", frameRoleVO);
    }

    @PostMapping(value = "/search")
    public ResultVO search(@RequestBody @Validated(IGroup.search.class) FrameRoleDTO dto) {
        TableModel<FrameRoleVO> tableModel = new TableModel<FrameRoleVO>() {
            @Override
            public List<FrameRoleVO> fetch() {
                QueryWrapper<FrameRole> qw = new QueryWrapper<>();
                qw.lambda()
                        .like(StrUtil.isNotBlank(dto.getRoleName()), FrameRole::getRole_name, dto.getRoleName())
                        .eq(StrUtil.isNotBlank(dto.getCategoryId()), FrameRole::getCategory_id, dto.getCategoryId());
                Page<FrameRole> page = iFrameRoleService.findList(qw, dto.getCurrent(), dto.getPageSize());
                this.setTotal(page.getTotal());
                return page.convert(IFrameRoleMapper.INSTANCE::toVO).getRecords();
            }
        };
        return new ResultVO(Status.SUCCESS, "查询成功", tableModel);
    }
}
