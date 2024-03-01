package com.xli.metadata.codemain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.metadata.codemain.entity.CodeMain;

import java.util.List;

public interface ICodeMainService extends IService<CodeMain> {

    boolean insert(CodeMain codeMain);

    boolean delete(Long id);

    CodeMain find(Long id);

    List<CodeMain> findList(QueryWrapper<CodeMain> qw);

    Page<CodeMain> findList(QueryWrapper<CodeMain> qw, long current, long size);

    Long getCodeIdByCodeName(String codeName);
}
