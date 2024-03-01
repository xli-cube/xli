package com.xli.mis.tablebasic.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.mis.datasource.entity.DataSource;
import com.xli.mis.tablebasic.entity.TableBasic;
import com.xli.mis.tablestruct.entity.TableStruct;

import java.util.List;

public interface ITableBasicService extends IService<TableBasic> {

    boolean insert(TableBasic tableBasic);

    boolean delete(Long id);

    boolean update(TableBasic tableBasic);

    TableBasic find(Long id);

    Page<TableBasic> findList(QueryWrapper<TableBasic> qw, long current, long size);

    void generatorCode(DataSource dataSource, TableBasic tableBasic, List<TableStruct> tableStructList, String author, String outputDir, String mapOutputDir);
}
