package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Set;
import java.util.List;
/**
* ${table.comment!}实现类
*
* @author ${author}
* @since ${date}
*/
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

@Override
public boolean insert(${entity} ${_entity}) {
return this.save(${_entity});
}

@Override
public boolean delete(Long id) {
return this.removeById(id);
}

@Override
public boolean delete(Set
<Long> ids) {
    return this.removeByIds(ids);
    }

    @Override
    public boolean update(${entity} ${_entity}) {
    return this.updateById(${_entity});
    }

    @Override
    public ${entity} find(Long id) {
    return this.getById(id);
    }

    @Override
    public List<${entity}> findList(QueryWrapper<${entity}> qw) {
    return this.list(qw);
    }

    @Override
    public long findCount(QueryWrapper<${entity}> qw) {
    return this.count(qw);
    }

    @Override
    public Page<${entity}> findList(QueryWrapper<${entity}> qw, long current, long size) {
    return this.page(new Page<>(current, size), qw);
    }
    }
    </#if>
