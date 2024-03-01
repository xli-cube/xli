package com.xli.metadata.codeitems.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xli.metadata.codeitems.entity.CodeItems;

import java.util.List;


public interface ICodeItemsService extends IService<CodeItems> {

	boolean insert(CodeItems codeItems);

	boolean delete(Long id);

	boolean update(CodeItems codeItems);

	List<CodeItems> getCodeItemsByCodeId(Long codeId);

	List<CodeItems> getCodeItemsByCodeName(String codeName);

	Page<CodeItems> findList(QueryWrapper<CodeItems> qw, long current, long size);
}
