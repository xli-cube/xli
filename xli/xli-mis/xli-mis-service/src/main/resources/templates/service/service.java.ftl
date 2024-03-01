package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;
import java.util.Set;
/**
* ${table.comment!}
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

/**
* 新增记录
* @param ${_entity} 实体记录
* @return 数据库操作状态
*/
boolean insert(${entity} ${_entity});

/**
* 删除记录
* @param id 主键
* @return 数据库操作状态
*/
boolean delete(Long id);

/**
* 删除记录
* @param ids 数据主键集合
* @return 数据库操作状态
*/
boolean delete(Set
<Long>ids);

    /**
    * 更新记录
    * @param ${_entity} 实体记录
    * @return 数据库操作状态
    */
    boolean update(${entity} ${_entity});

    /**
    * 查询记录
    * @param id 主键
    * @return 查询结果
    */
    ${entity} find(Long id);

    /**
    * 查询数据集合
    *
    * @param qw 查询条件
    * @return 数据集合
    */
    List<${entity}> findList(QueryWrapper<${entity}> qw);

    /**
    * 查询数量
    *
    * @param qw 查询条件
    * @return 数据量
    */
    long findCount(QueryWrapper<${entity}> qw);

    /**
    * 分页查询
    *
    * @param qw 查询条件
    * @param current 分页值
    * @param size 每页数据量
    * @return 分页结果集
    */
    Page<${entity}> findList(QueryWrapper<${entity}> qw, long current, long size);
    }
    </#if>
