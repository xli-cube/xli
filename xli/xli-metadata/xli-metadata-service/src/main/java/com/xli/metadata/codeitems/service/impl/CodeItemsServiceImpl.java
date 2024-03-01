package com.xli.metadata.codeitems.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.metadata.codeitems.entity.CodeItems;
import com.xli.metadata.codeitems.mapper.CodeItemsMapper;
import com.xli.metadata.codeitems.service.ICodeItemsService;
import com.xli.metadata.codemain.service.ICodeMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CodeItemsServiceImpl extends ServiceImpl<CodeItemsMapper, CodeItems> implements ICodeItemsService {

    @Autowired
    ICodeMainService iCodeMainService;

    @Override
    public boolean insert(CodeItems codeItems) {
        return this.save(codeItems);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(CodeItems codeItems) {
        return this.updateById(codeItems);
    }

    @Override
    public List<CodeItems> getCodeItemsByCodeId(Long codeId) {
        QueryWrapper<CodeItems> qw = new QueryWrapper<>();
        qw.lambda().eq(CodeItems::getCode_id, codeId);
        return this.list(qw);
    }

    @Override
    public List<CodeItems> getCodeItemsByCodeName(String codeName) {
        Long codeId = iCodeMainService.getCodeIdByCodeName(codeName);
        if (codeId != null) {
            return getCodeItemsByCodeId(codeId);
        }
        return new ArrayList<>();
    }

    @Override
    public Page<CodeItems> findList(QueryWrapper<CodeItems> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }
}
