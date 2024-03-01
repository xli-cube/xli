package ${package.Entity}.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ${package.Entity}.${entity};
import ${package.Entity}.dto.${entityDTO};
import ${package.Entity}.vo.${entityVO};

@Mapper
public interface ${IEntityMapper} {

${IEntityMapper} INSTANCE = Mappers.getMapper(${IEntityMapper}.class);

@Mappings({
<#list tableStructs as tableStruct>
    @Mapping(source = "${tableStruct.fieldCamel}", target = "${tableStruct.field}")<#if !tableStruct?is_last>,</#if>
</#list>
})
${entity} toEntity(${entityDTO} dto);

@Mappings({
<#list tableStructs as tableStruct>
    @Mapping(source = "${tableStruct.field}", target = "${tableStruct.fieldCamel}")<#if !tableStruct?is_last>,</#if>
</#list>
})
${entityVO} toVO(${entity} ${_entity});
}
