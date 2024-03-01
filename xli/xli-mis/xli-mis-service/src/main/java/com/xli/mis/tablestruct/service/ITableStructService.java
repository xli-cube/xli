package com.xli.mis.tablestruct.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.mis.tablestruct.entity.TableStruct;

import java.util.List;

public interface ITableStructService extends IService<TableStruct> {

    boolean insert(TableStruct tableStruct);

    boolean delete(Long id, boolean delDbStruct);

    boolean update(TableStruct tableStruct);

    TableStruct find(Long id);

    Page<TableStruct> findList(QueryWrapper<TableStruct> qw, long current, long size);

    List<TableStruct> findListByTableId(Long tableId);
}
