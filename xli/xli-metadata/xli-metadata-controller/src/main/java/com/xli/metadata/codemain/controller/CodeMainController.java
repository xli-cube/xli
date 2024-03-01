package com.xli.metadata.codemain.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xli.dto.component.model.TableModel;
import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import com.xli.dto.validation.group.IGroup;
import com.xli.metadata.codemain.entity.CodeMain;
import com.xli.metadata.codemain.entity.dto.CodeMainDTO;
import com.xli.metadata.codemain.entity.mapper.ICodeMainMapper;
import com.xli.metadata.codemain.entity.vo.CodeMainVO;
import com.xli.metadata.codemain.service.ICodeMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/codeMain")
public class CodeMainController {

    @Autowired
    ICodeMainService iCodeMainService;

    @PostMapping(value = "/add")
    public ResultVO<String> add(@RequestBody @Validated(IGroup.add.class) CodeMainDTO dto) {
        if (iCodeMainService.insert(ICodeMainMapper.INSTANCE.toEntity(dto))) {
            return new ResultVO<>(Status.SUCCESS, "添加成功");
        }
        return new ResultVO<>(Status.FAILED, "添加失败");
    }

    @PostMapping(value = "/delete")
    public ResultVO<String> delete(@RequestBody List<String> ids) {
        int i = 0;
        for (String id : ids) {
            if (iCodeMainService.delete(Long.valueOf(id))) {
                i++;
            }
        }
        return new ResultVO<>(Status.SUCCESS, i + "条数据删除成功");
    }

    @PostMapping(value = "/search")
    public ResultVO<TableModel<CodeMainVO>> search(@RequestBody @Validated(IGroup.search.class) CodeMainDTO dto) {
        TableModel<CodeMainVO> tableModel = new TableModel<>() {
            @Override
            public List<CodeMainVO> fetch() {
                QueryWrapper<CodeMain> qw = new QueryWrapper<>();
                qw.lambda()
                        .eq(StrUtil.isNotBlank(dto.getCategoryId()), CodeMain::getCategory_id, dto.getCategoryId())
                        .like(StrUtil.isNotBlank(dto.getCodeName()), CodeMain::getCode_name, dto.getCodeName());

                if (StrUtil.isNotBlank(dto.getSort()) && StrUtil.isNotBlank(dto.getFilter())) {
                    boolean isAscend = "ascend".equals(dto.getSort());
                    qw.orderBy(true, isAscend, dto.getFilter());
                }
                Page<CodeMain> page = iCodeMainService.findList(qw, dto.getCurrent(), dto.getPageSize());
                this.setTotal(page.getTotal());
                return page.convert(ICodeMainMapper.INSTANCE::toVO).getRecords();
            }
        };
        return new ResultVO<>(Status.SUCCESS, "查询成功", tableModel);
    }
}
