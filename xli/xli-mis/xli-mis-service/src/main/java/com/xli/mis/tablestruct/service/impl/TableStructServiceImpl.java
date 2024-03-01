package com.xli.mis.tablestruct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.mis.tablestruct.entity.TableStruct;
import com.xli.mis.tablestruct.mapper.TableStructMapper;
import com.xli.mis.tablestruct.service.ITableStructService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TableStructServiceImpl extends ServiceImpl<TableStructMapper, TableStruct> implements ITableStructService {

    @Autowired
    TableStructMapper tableStructMapper;

    @Override
    public boolean insert(TableStruct tableStruct) {
        try {
            String nullStr = "null";
            if ("1".equals(tableStruct.getNotnull())) {
                nullStr = "not null";
            }
            String fieldTypeFull = "";
            if ("NVARCHAR".equals(tableStruct.getField_type())) {
                fieldTypeFull += tableStruct.getField_type() + "(" + tableStruct.getField_length() + ")";
            }
            boolean exists = tableStructMapper.columnExists(tableStruct.getSql_table_name(), tableStruct.getField_name_en());
            if (!exists) {
                tableStructMapper.createField(tableStruct.getSql_table_name(), tableStruct.getField_name_en(), tableStruct.getField_name_cn(), fieldTypeFull, nullStr);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.save(tableStruct);
    }

    @Override
    public boolean delete(Long id, boolean delDbStruct) {
        if (delDbStruct) {
            try {
                TableStruct tableStruct = find(id);
                if (tableStruct != null) {
                    boolean exists = tableStructMapper.columnExists(tableStruct.getSql_table_name(), tableStruct.getField_name_en());
                    if (exists) {
                        tableStructMapper.dropField(tableStruct.getSql_table_name(), tableStruct.getField_name_en());
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return this.removeById(id);
    }

    @Override
    public boolean update(TableStruct tableStruct) {
        return this.updateById(tableStruct);
    }

    @Override
    public TableStruct find(Long id) {
        return this.getById(id);
    }

    @Override
    public List<TableStruct> findListByTableId(Long tableId) {
        QueryWrapper<TableStruct> qw = new QueryWrapper<>();
        qw.lambda().eq(TableStruct::getTable_id, tableId);
        return this.list(qw);
    }

    @Override
    public Page<TableStruct> findList(QueryWrapper<TableStruct> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }
}
