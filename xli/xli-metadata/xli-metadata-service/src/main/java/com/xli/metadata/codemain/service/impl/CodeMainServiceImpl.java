package com.xli.metadata.codemain.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xli.metadata.codeitems.entity.CodeItems;
import com.xli.metadata.codeitems.service.ICodeItemsService;
import com.xli.metadata.codemain.entity.CodeMain;
import com.xli.metadata.codemain.mapper.CodeMainMapper;
import com.xli.metadata.codemain.service.ICodeMainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeMainServiceImpl extends ServiceImpl<CodeMainMapper, CodeMain> implements ICodeMainService {

    @Override
    public boolean insert(CodeMain codeMain) {
        return this.save(codeMain);
    }

    @Override
    public boolean delete(Long id) {
        boolean result = this.removeById(id);
        if (result) {
            ICodeItemsService iCodeItemsService = SpringUtil.getBean(ICodeItemsService.class);
            List<CodeItems> codeItemsList = iCodeItemsService.getCodeItemsByCodeId(id);
            for (CodeItems codeItems : codeItemsList) {
                iCodeItemsService.delete(codeItems.getId());
            }
        }
        return result;
    }

    @Override
    public CodeMain find(Long id) {
        return this.getById(id);
    }

    @Override
    public List<CodeMain> findList(QueryWrapper<CodeMain> qw) {
        return this.list(qw);
    }

    @Override
    public Page<CodeMain> findList(QueryWrapper<CodeMain> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }

    @Override
    public Long getCodeIdByCodeName(String codeName) {
        if (StrUtil.isNotBlank(codeName)) {
            QueryWrapper<CodeMain> qw = new QueryWrapper<>();
            qw.lambda().eq(CodeMain::getCode_name, codeName);
            List<CodeMain> codeMainList = this.list(qw);
            if (!codeMainList.isEmpty()) {
                return codeMainList.get(0).getId();
            }
        }
        return null;
    }
}
