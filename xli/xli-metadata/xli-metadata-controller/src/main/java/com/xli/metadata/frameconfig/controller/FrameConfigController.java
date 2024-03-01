package com.xli.metadata.frameconfig.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xli.dto.component.model.TableModel;
import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import com.xli.dto.validation.group.IGroup;
import com.xli.metadata.frameconfig.entity.FrameConfig;
import com.xli.metadata.frameconfig.entity.dto.FrameConfigDTO;
import com.xli.metadata.frameconfig.entity.mapper.IFrameConfigMapper;
import com.xli.metadata.frameconfig.entity.vo.FrameConfigVO;
import com.xli.metadata.frameconfig.service.IFrameConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "系统参数")
@RestController
@RequestMapping("/frameConfig")
public class FrameConfigController {

    @Autowired
    private IFrameConfigService iFrameConfigService;

    @Operation(summary = "添加参数", description = "添加系统参数")
    @PostMapping(value = "/add")
    public ResultVO<String> add(@RequestBody @Validated(IGroup.add.class) FrameConfigDTO dto) {
        if (iFrameConfigService.insert(IFrameConfigMapper.INSTANCE.toEntity(dto))) {
            return new ResultVO<>(Status.SUCCESS, "添加成功");
        }
        return new ResultVO<>(Status.FAILED, "添加失败");
    }

    @Operation(summary = "删除参数", description = "删除系统参数")
    @PostMapping(value = "/delete")
    public ResultVO<String> delete(@RequestBody @Validated(IGroup.delete.class) List<FrameConfigDTO> list) {
        int i = 0;
        for (FrameConfigDTO dto : list) {
            if (iFrameConfigService.delete(dto.getId())) {
                i++;
            }
        }
        return new ResultVO<>(Status.SUCCESS, i + "条数据删除成功");
    }

    @Operation(summary = "更新参数", description = "更新系统参数")
    @PostMapping(value = "/update")
    public ResultVO<String> update(@RequestBody @Validated(IGroup.update.class) FrameConfigDTO dto) {
        if (iFrameConfigService.update(IFrameConfigMapper.INSTANCE.toEntity(dto))) {
            return new ResultVO<>(Status.SUCCESS, "更新成功");
        }
        return new ResultVO<>(Status.FAILED, "更新失败");
    }

    @Operation(summary = "查询参数详情", description = "查询系统参数详情")
    @PostMapping(value = "/detail")
    public ResultVO<FrameConfigVO> detail(@RequestBody @Validated(IGroup.detail.class) FrameConfigDTO dto) {
        FrameConfig frameConfig = iFrameConfigService.find(dto.getId());
        return new ResultVO<>(Status.SUCCESS, "查询成功", IFrameConfigMapper.INSTANCE.toVO(frameConfig));
    }

    @Operation(summary = "查询参数列表", description = "查询系统参数列表")
    @PostMapping(value = "/search")
    public ResultVO<String> search(@RequestBody @Validated(IGroup.search.class) FrameConfigDTO dto) {
        TableModel<FrameConfigVO> tableModel = new TableModel<>() {
            @Override
            public List<FrameConfigVO> fetch() {
                QueryWrapper<FrameConfig> qw = new QueryWrapper<>();
                qw.lambda()
                        .like(StrUtil.isNotBlank(dto.getConfigName()), FrameConfig::getConfig_name, dto.getConfigName())
                        .eq(StrUtil.isNotBlank(dto.getCategoryId()), FrameConfig::getCategory_id, dto.getCategoryId());

                if (StrUtil.isNotBlank(dto.getSort()) && StrUtil.isNotBlank(dto.getFilter())) {
                    boolean isAscend = "ascend".equals(dto.getSort());
                    qw.orderBy(true, isAscend, dto.getFilter());
                }
                Page<FrameConfig> page = iFrameConfigService.findList(qw, dto.getCurrent(), dto.getPageSize());
                this.setTotal(page.getTotal());
                return page.convert(IFrameConfigMapper.INSTANCE::toVO).getRecords();
            }
        };

        return new ResultVO(Status.SUCCESS, "查询成功", tableModel);
    }
}
