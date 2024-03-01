package com.xli.metadata.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.xli.dto.component.SelectItem;
import com.xli.metadata.codeitems.entity.CodeItems;
import com.xli.metadata.codeitems.service.ICodeItemsService;

import java.util.ArrayList;
import java.util.List;

public class CodeModalFactory {
    public static List<SelectItem> factory(String codeName, boolean hasInitValue) {
        if (StrUtil.isNotBlank(codeName)) {
            ICodeItemsService iCodeItemsService = SpringUtil.getBean(ICodeItemsService.class);
            List<SelectItem> selectItemList = new ArrayList<>();
            if (hasInitValue) {
                selectItemList.add(new SelectItem("", "请选择"));
            }
            List<CodeItems> codeItemsList = iCodeItemsService.getCodeItemsByCodeName(codeName);
            for (CodeItems codeItems : codeItemsList) {
                selectItemList.add(new SelectItem(codeItems.getItem_value(), codeItems.getItem_text()));
            }
            return selectItemList;
        }
        return new ArrayList<>();
    }
}
