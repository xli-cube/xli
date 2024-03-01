package ${package.Entity}.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ${entityVO} implements Serializable {

private String id;

<#list tableStructs as tableStruct>
    private <#if tableStruct.fieldType=='string'>String</#if> ${tableStruct.fieldCamel};
</#list>
}
