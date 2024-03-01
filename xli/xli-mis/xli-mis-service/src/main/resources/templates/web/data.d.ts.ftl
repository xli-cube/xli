export type ${entityDTO} ={
id?: string;
<#list tableStructs as tableStruct>
    ${tableStruct.fieldCamel}?<#if tableStruct.fieldType=='string'>: string</#if>;
</#list>
};