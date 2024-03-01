package com.xli.soa.user.frameuser.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xli.dto.component.SelectItem;
import com.xli.dto.component.model.TableModel;
import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import com.xli.dto.validation.group.IGroup;
import com.xli.metadata.utils.CodeModalFactory;
import com.xli.soa.ou.frameou.entity.FrameOu;
import com.xli.soa.ou.frameou.service.IFrameOuService;
import com.xli.soa.user.frameuser.entity.FrameUser;
import com.xli.soa.user.frameuser.entity.dto.UserAddDTO;
import com.xli.soa.user.frameuser.entity.dto.UserSearchDTO;
import com.xli.soa.user.frameuser.entity.dto.UserUpdateDTO;
import com.xli.soa.user.frameuser.entity.mapper.IFrameUserMapper;
import com.xli.soa.user.frameuser.entity.vo.UserVO;
import com.xli.soa.user.frameuser.service.IFrameUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/frameUser")
public class FrameUserController {

    @Autowired
    IFrameUserService iFrameUserService;

    @Autowired
    IFrameOuService iFrameOuService;

    @Operation(summary = "新增用户")
    @PostMapping(value = "/add")
    public ResultVO<String> add(@RequestBody @Validated(IGroup.add.class) UserAddDTO dto) {
        FrameUser frameUser = iFrameUserService.getFrameUserByLoginId(dto.getLoginId());
        if (frameUser == null) {
            frameUser = IFrameUserMapper.INSTANCE.toEntity(dto);
            frameUser.setId(IdUtil.getSnowflakeNextId());
            frameUser.setPwd("");
            if (iFrameUserService.insert(frameUser)) {
                return new ResultVO<>(Status.SUCCESS, "添加成功");
            }
            return new ResultVO<>(Status.FAILED, "添加失败");
        } else {
            return new ResultVO<>(Status.FAILED, "添加失败，登录账号已存在");
        }
    }

    @Operation(summary = "删除用户")
    @PostMapping(value = "/delete")
    public ResultVO<String> delete(@RequestBody List<String> ids) {
        int i = 0;
        for (String id : ids) {
            if (iFrameUserService.delete(Long.parseLong(id))) {
                i++;
            }
        }
        return new ResultVO<>(Status.SUCCESS, i + "条数据删除成功");
    }

    @Operation(summary = "更新用户")
    @PostMapping(value = "/update")
    public ResultVO<String> update(@RequestBody @Validated(IGroup.update.class) UserUpdateDTO dto) {
        FrameUser frameUser = IFrameUserMapper.INSTANCE.toEntity(dto);
        if (iFrameUserService.update(frameUser)) {
            return new ResultVO<>(Status.SUCCESS, "更新成功");
        }
        return new ResultVO<>(Status.FAILED, "更新失败");
    }

    @Operation(summary = "查询详情")
    @PostMapping(value = "/detail/{id}")
    public ResultVO<UserVO> detail(@PathVariable("id") @NotBlank String id) {
        FrameUser frameUser = iFrameUserService.find(Long.valueOf(id));
        if (frameUser != null) {
            return new ResultVO<>(Status.SUCCESS, "查询成功", IFrameUserMapper.INSTANCE.toVO(frameUser));
        }
        return new ResultVO<>(Status.FAILED, "查询失败");
    }

    @Operation(summary = "查询列表")
    @PostMapping(value = "/search")
    public ResultVO<TableModel<UserVO>> search(@RequestBody @Validated(IGroup.search.class) UserSearchDTO dto) {
        TableModel<UserVO> tableModel = new TableModel<>() {
            @Override
            public List<UserVO> fetch() {
                QueryWrapper<FrameUser> qw = new QueryWrapper<>();

                if (StrUtil.isNotBlank(dto.getFilter())) {
                    boolean isAscend = false;
                    if (StrUtil.isNotBlank(dto.getSort())) {
                        isAscend = "ascend".equals(dto.getSort());
                    }
                    qw.orderBy(true, isAscend, dto.getFilter());
                }

                qw.lambda()
                        .like(StrUtil.isNotBlank(dto.getDisplayName()), FrameUser::getDisplay_name, dto.getDisplayName())
                        .like(StrUtil.isNotBlank(dto.getLoginId()), FrameUser::getLogin_id, dto.getLoginId())
                        .eq(StrUtil.isNotBlank(dto.getOuId()), FrameUser::getOu_id, dto.getOuId())
                        .eq(StrUtil.isNotBlank(dto.getGender()), FrameUser::getGender, dto.getGender())
                        .eq(StrUtil.isNotBlank(dto.getIsEnabled()), FrameUser::getIs_enabled, dto.getIsEnabled());

                Page<FrameUser> page = iFrameUserService.findList(qw, dto.getCurrent(), dto.getPageSize());
                List<UserVO> frameUserVOList = page.convert(IFrameUserMapper.INSTANCE::toVO).getRecords();
                for (UserVO frameUserVO : frameUserVOList) {
                    if (StrUtil.isNotBlank(frameUserVO.getOuId())) {
                        FrameOu frameOu = iFrameOuService.find(Long.parseLong(frameUserVO.getOuId()));
                        if (frameOu != null) {
                            frameUserVO.setOuName(frameOu.getOu_name());
                        }
                    }
                }
                this.setTotal(page.getTotal());
                return frameUserVOList;
            }
        };
        return new ResultVO<>(Status.SUCCESS, "查询成功", tableModel);
    }

    @Operation(summary = "查询性别")
    @PostMapping(value = "/getGenderModel")
    public ResultVO<List<SelectItem>> getGenderModel() {
        List<SelectItem> genderList = CodeModalFactory.factory("性别", false);
        return new ResultVO<>(Status.SUCCESS, "查询成功", genderList);
    }

    @Operation(summary = "查询账号状态")
    @PostMapping(value = "/getIsEnabledModel")
    public ResultVO<List<SelectItem>> getIsEnabledModel() {
        List<SelectItem> genderList = CodeModalFactory.factory("账号状态", false);
        return new ResultVO<>(Status.SUCCESS, "查询成功", genderList);
    }
}
