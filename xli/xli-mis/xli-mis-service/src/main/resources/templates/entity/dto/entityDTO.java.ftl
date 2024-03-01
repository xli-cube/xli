package ${package.Entity}.dto;

import com.xli.dto.param.base.BaseParam;
import lombok.Data;

@Data
public class ${entityDTO} extends BaseParam {

private static final long serialVersionUID = 1L;

private String id;

<#list tableStructs as tableStruct>
    private <#if tableStruct.fieldType=='string'>String</#if> ${tableStruct.fieldCamel};
</#list>
}
