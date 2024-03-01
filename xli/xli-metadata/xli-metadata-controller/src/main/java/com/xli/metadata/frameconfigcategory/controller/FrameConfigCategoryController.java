package com.xli.metadata.frameconfigcategory.controller;

import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import com.xli.dto.validation.group.IGroup;
import com.xli.metadata.frameconfigcategory.entity.dto.FrameConfigCategoryDTO;
import com.xli.metadata.frameconfigcategory.entity.mapper.IFrameConfigCategoryMapper;
import com.xli.metadata.frameconfigcategory.service.IFrameConfigCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frameConfigCategory")
public class FrameConfigCategoryController {

    @Autowired
    private IFrameConfigCategoryService iFrameConfigCategoryService;

    @PostMapping(value = "/add")
    public ResultVO<String> add(@RequestBody @Validated(IGroup.add.class) FrameConfigCategoryDTO dto) {
        if (iFrameConfigCategoryService.insert(IFrameConfigCategoryMapper.INSTANCE.toEntity(dto))) {
            return new ResultVO<>(Status.SUCCESS, "添加成功");
        }
        return new ResultVO<>(Status.FAILED, "添加失败");
    }

    @PostMapping(value = "/delete")
    public ResultVO<String> delete(@RequestBody @Validated(IGroup.delete.class) List<String> ids) {
        int i = 0;
        for (String id : ids) {
            if (iFrameConfigCategoryService.delete(Long.valueOf(id))) {
                i++;
            }
        }
        return new ResultVO<>(Status.SUCCESS, i + "条数据删除成功");
    }

    @PostMapping(value = "/fetchTree")
    public ResultVO<String> fetchTree() {
        return new ResultVO<>(Status.SUCCESS, "查询成功");
    }
}
