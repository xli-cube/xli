package com.xli.mis.tablestruct.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xli.dto.component.SelectItem;
import com.xli.dto.component.model.TableModel;
import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import com.xli.dto.validation.group.IGroup;
import com.xli.metadata.codemain.entity.CodeMain;
import com.xli.metadata.codemain.service.ICodeMainService;
import com.xli.mis.tablebasic.entity.TableBasic;
import com.xli.mis.tablebasic.service.ITableBasicService;
import com.xli.mis.tablestruct.entity.TableStruct;
import com.xli.mis.tablestruct.entity.dto.TableStructDTO;
import com.xli.mis.tablestruct.entity.mapper.ITableStructMapper;
import com.xli.mis.tablestruct.entity.vo.TableStructVO;
import com.xli.mis.tablestruct.service.ITableStructService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    public ResultVO add(@RequestBody @Validated(IGroup.add.class) TableStructDTO dto) {
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
                return new ResultVO(Status.SUCCESS, "添加成功");
            }
        }
        return new ResultVO(Status.FAILED, "添加失败");
    }

    @PostMapping(value = "/delete")
    public ResultVO delete(@RequestBody List<String> ids) {
        int i = 0;
        for (String id : ids) {
            if (iTableStructService.delete(Long.valueOf(id), true)) {
                i++;
            }
        }
        return new ResultVO(Status.SUCCESS, i + "条数据删除成功");
    }

    @PostMapping(value = "/update")
    public ResultVO update(@RequestBody @Validated(IGroup.update.class) TableStructDTO dto) {
        if (iTableStructService.update(ITableStructMapper.INSTANCE.toEntity(dto))) {
            return new ResultVO(Status.SUCCESS, "更新成功");
        }
        return new ResultVO(Status.FAILED, "更新失败");
    }

    @PostMapping(value = "/search")
    public ResultVO search(@RequestBody @Validated(IGroup.search.class) TableStructDTO dto) {
        TableModel<TableStructVO> tableModel = new TableModel<>() {
            @Override
            public List<TableStructVO> fetch() {
                QueryWrapper<TableStruct> qw = new QueryWrapper<>();
                qw.lambda()
                        .eq(StrUtil.isNotBlank(dto.getTableId()), TableStruct::getTable_id, dto.getTableId())
                        .like(StrUtil.isNotBlank(dto.getFieldNameCn()), TableStruct::getField_name_cn, dto.getFieldNameCn())
                        .like(StrUtil.isNotBlank(dto.getFieldNameEn()), TableStruct::getField_name_en, dto.getFieldNameEn());

                if (StrUtil.isNotBlank(dto.getSort()) && StrUtil.isNotBlank(dto.getFilter())) {
                    boolean isAscend = "ascend".equals(dto.getSort());
                    qw.orderBy(true, isAscend, dto.getFilter());
                }
                Page<TableStruct> page = iTableStructService.findList(qw, dto.getCurrent(), dto.getPageSize());
                this.setTotal(page.getTotal());
                return page.convert(ITableStructMapper.INSTANCE::toVO).getRecords();
            }
        };
        return new ResultVO(Status.SUCCESS, "查询成功", tableModel);
    }

    @PostMapping(value = "/getCodeMainList")
    public ResultVO getCodeMainList() {
        List<SelectItem> selectItemList = new ArrayList<>();
        List<CodeMain> codeMainList = iCodeMainService.findList(new QueryWrapper<>());
        for (CodeMain codeMain : codeMainList) {
            SelectItem selectItem = new SelectItem(codeMain.getId().toString(), codeMain.getCode_name());
            selectItemList.add(selectItem);
        }
        JSONObject jsonObject = new JSONObject();
        if (selectItemList != null && !selectItemList.isEmpty()) {
            for (SelectItem selectItem : selectItemList) {
                //jsonObject.put(selectItem.getValue(), selectItem.toJSONOption());

            }
        }
        return new ResultVO(Status.SUCCESS, "查询成功", jsonObject);
    }
}
