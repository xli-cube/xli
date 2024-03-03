package com.xli.mis.tablestruct.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xli.dto.component.model.TableModel;
import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import com.xli.dto.validation.group.IGroup;
import com.xli.metadata.codemain.service.ICodeMainService;
import com.xli.mis.tablebasic.entity.TableBasic;
import com.xli.mis.tablebasic.service.ITableBasicService;
import com.xli.mis.tablestruct.entity.TableStruct;
import com.xli.mis.tablestruct.entity.dto.TableStructAddDTO;
import com.xli.mis.tablestruct.entity.dto.TableStructSearchDTO;
import com.xli.mis.tablestruct.entity.dto.TableStructUpdateDTO;
import com.xli.mis.tablestruct.entity.mapper.ITableStructMapper;
import com.xli.mis.tablestruct.entity.vo.TableStructVO;
import com.xli.mis.tablestruct.service.ITableStructService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tableStruct")
public class TableStructController {

    @Autowired
    ICodeMainService iCodeMainService;
    @Autowired
    private ITableBasicService iTableBasicService;
    @Autowired
    private ITableStructService iTableStructService;

    @PostMapping(value = "/add")
    public ResultVO<String> add(@RequestBody @Validated(IGroup.add.class) TableStructAddDTO dto) {
        TableBasic tableBasic = iTableBasicService.find(Long.valueOf(dto.getTableId()));
        if (tableBasic != null) {
            TableStruct tableStruct = ITableStructMapper.INSTANCE.toEntity(dto);
            tableStruct.setId(IdUtil.getSnowflakeNextId());
            tableStruct.setSql_table_name(tableBasic.getSql_table_name());
            if ("NVARCHAR".equals(tableStruct.getField_type()) && (tableStruct.getField_length() == null || tableStruct.getField_length() <= 0)) {
                tableStruct.setField_length(50);
            }
            boolean result = iTableStructService.insert(tableStruct);
            if (result) {
                return new ResultVO<>(Status.SUCCESS, "添加成功");
            }
        }
        return new ResultVO<>(Status.FAILED, "添加失败");
    }

    @PostMapping(value = "/delete")
    public ResultVO<String> delete(@RequestBody List<String> ids) {
        int i = 0;
        for (String id : ids) {
            if (iTableStructService.delete(Long.valueOf(id), true)) {
                i++;
            }
        }
        return new ResultVO<>(Status.SUCCESS, i + "条数据删除成功");
    }

    @PostMapping(value = "/update")
    public ResultVO<String> update(@RequestBody @Validated(IGroup.update.class) TableStructUpdateDTO dto) {
        if (iTableStructService.update(ITableStructMapper.INSTANCE.toEntity(dto))) {
            return new ResultVO<>(Status.SUCCESS, "更新成功");
        }
        return new ResultVO<>(Status.FAILED, "更新失败");
    }

    @PostMapping(value = "/search")
    public ResultVO<TableModel<TableStructVO>> search(@RequestBody @Validated(IGroup.search.class) TableStructSearchDTO dto) {
        TableModel<TableStructVO> tableModel = new TableModel<>() {
            @Override
            public List<TableStructVO> fetch() {
                QueryWrapper<TableStruct> qw = new QueryWrapper<>();

                if (StrUtil.isNotBlank(dto.getFilter())) {
                    boolean isAscend = false;
                    if (StrUtil.isNotBlank(dto.getSort())) {
                        isAscend = "ascend".equals(dto.getSort());
                    }
                    qw.orderBy(true, isAscend, dto.getFilter());
                }

                qw.lambda()
                        .eq(StrUtil.isNotBlank(dto.getTableId()), TableStruct::getTable_id, dto.getTableId())
                        .like(StrUtil.isNotBlank(dto.getFieldNameCn()), TableStruct::getField_name_cn, dto.getFieldNameCn())
                        .like(StrUtil.isNotBlank(dto.getFieldNameEn()), TableStruct::getField_name_en, dto.getFieldNameEn());

                Page<TableStruct> page = iTableStructService.findList(qw, dto.getCurrent(), dto.getPageSize());
                this.setTotal(page.getTotal());
                return page.convert(ITableStructMapper.INSTANCE::toVO).getRecords();
            }
        };
        return new ResultVO<>(Status.SUCCESS, "查询成功", tableModel);
    }
}
