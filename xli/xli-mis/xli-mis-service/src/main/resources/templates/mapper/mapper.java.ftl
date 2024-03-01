package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
<#if mapperAnnotation>
    import org.apache.ibatis.annotations.Mapper;
    import org.springframework.stereotype.Repository;
</#if>

/**
* ${table.comment!} Mapper
*
* @author ${author}
* @since ${date}
*/
<#if mapperAnnotation>
    @Mapper
    @Repository
</#if>
<#if kotlin>
    interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
    public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

    }
</#if>
