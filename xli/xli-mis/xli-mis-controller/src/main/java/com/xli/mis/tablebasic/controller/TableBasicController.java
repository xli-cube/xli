package com.xli.mis.tablebasic.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xli.dto.component.model.TableModel;
import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import com.xli.dto.validation.group.IGroup;
import com.xli.mis.tablebasic.entity.TableBasic;
import com.xli.mis.tablebasic.entity.dto.TableBasicAddDTO;
import com.xli.mis.tablebasic.entity.dto.TableBasicDTO;
import com.xli.mis.tablebasic.entity.dto.TableBasicSearchDTO;
import com.xli.mis.tablebasic.entity.dto.TableBasicUpdateDTO;
import com.xli.mis.tablebasic.entity.mapper.ITableBasicMapper;
import com.xli.mis.tablebasic.entity.vo.TableBasicVO;
import com.xli.mis.tablebasic.service.ITableBasicService;
import com.xli.mis.tablestruct.entity.TableStruct;
import com.xli.mis.tablestruct.service.ITableStructService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tableBasic")
public class TableBasicController {

    @Autowired
    ITableStructService iTableStructService;
    @Autowired
    private ITableBasicService iTableBasicService;

    @PostMapping(value = "/add")
    public ResultVO<String> add(@RequestBody @Validated(IGroup.add.class) TableBasicAddDTO dto) {
        TableBasic tableBasic = ITableBasicMapper.INSTANCE.toEntity(dto);
        tableBasic.setId(IdUtil.getSnowflakeNextId());
        if (StrUtil.isBlank(tableBasic.getEntity_name())) {
            tableBasic.setEntity_name(StrUtil.toCamelCase(StrUtil.upperFirst(tableBasic.getSql_table_name())));
        }
        if (iTableBasicService.insert(tableBasic)) {
            return new ResultVO<>(Status.SUCCESS, "添加成功");
        }
        return new ResultVO<>(Status.FAILED, "失败成功");
    }

    @PostMapping(value = "/delete")
    public ResultVO<String> delete(@RequestBody List<String> ids) {
        int i = 0;
        for (String id : ids) {
            if (iTableBasicService.delete(Long.valueOf(id))) {
                i++;
            }
        }
        return new ResultVO<>(Status.SUCCESS, i + "条数据删除成功");
    }

    @PostMapping(value = "/update")
    public ResultVO<String> update(@RequestBody @Validated(IGroup.update.class) TableBasicUpdateDTO dto) {
        if (iTableBasicService.update(ITableBasicMapper.INSTANCE.toEntity(dto))) {
            return new ResultVO<>(Status.SUCCESS, "更新成功");
        }
        return new ResultVO<>(Status.FAILED, "更新失败");
    }

    @PostMapping(value = "/search")
    public ResultVO<TableModel<TableBasicVO>> search(@RequestBody @Validated(IGroup.search.class) TableBasicSearchDTO dto) {
        TableModel<TableBasicVO> tableModel = new TableModel<>() {
            @Override
            public List<TableBasicVO> fetch() {
                QueryWrapper<TableBasic> qw = new QueryWrapper<>();

                if (StrUtil.isNotBlank(dto.getFilter())) {
                    boolean isAscend = false;
                    if (StrUtil.isNotBlank(dto.getSort())) {
                        isAscend = "ascend".equals(dto.getSort());
                    }
                    qw.orderBy(true, isAscend, dto.getFilter());
                }

                qw.lambda()
                        .like(StrUtil.isNotBlank(dto.getTableName()), TableBasic::getTable_name, dto.getTableName())
                        .like(StrUtil.isNotBlank(dto.getSqlTableName()), TableBasic::getSql_table_name, dto.getSqlTableName());

                Page<TableBasic> page = iTableBasicService.findList(qw, dto.getCurrent(), dto.getPageSize());
                this.setTotal(page.getTotal());
                return page.convert(ITableBasicMapper.INSTANCE::toVO).getRecords();
            }
        };
        return new ResultVO<>(Status.SUCCESS, "查询成功", tableModel);
    }

    @PostMapping(value = "/generateCode")
    public ResultVO<String> generateCode(@RequestBody List<String> ids) {
        for (String id : ids) {
            try {
                TableBasic tableBasic = iTableBasicService.find(Long.valueOf(id));
                if (tableBasic != null) {
                    List<TableStruct> tableStructList = iTableStructService.findListByTableId(tableBasic.getId());
                    String author = System.getProperties().getProperty("user.name");
                    String outputDir = System.getProperty("user.dir") + File.separator + "generatorCode";
                    String mapOutputDir = outputDir + File.separator + "mapper";
                    iTableBasicService.generatorCode(null, tableBasic, tableStructList, author, outputDir, mapOutputDir);
                }
            } catch (Exception ex) {
                log.error("生成代码报错：" + ex);
            }
        }
        return new ResultVO<>(Status.SUCCESS, "生成成功");
    }
}
