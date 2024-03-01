package ${package.Controller};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import com.xli.dto.param.base.BaseParam;
import com.xli.dto.param.result.ResultVO;
import com.xli.dto.param.result.status.Status;
import org.springframework.web.bind.annotation.*;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.mapper.${IEntityMapper};
import ${package.Entity}.${entity};
import ${package.Entity}.dto.${entityDTO};
import ${package.Entity}.vo.${entityVO};
import cn.hutool.core.util.StrUtil;
import com.xli.dto.component.model.TableModel;

/**
* ${table.comment!}前端控制器
*
* @author ${author}
* @since ${date}
*/
@RestController
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>")
public class ${table.controllerName} {

@Autowired
private ${table.serviceName} ${_serviceName};

@PostMapping(value = "/add")
public ResultVO add(@RequestBody @Validated(BaseParam.add.class) ${entityDTO} dto) {
${entity} ${_entity}=${IEntityMapper}.INSTANCE.toEntity(dto);
if (${_serviceName}.insert(${_entity})) {
return new ResultVO(Status.SUCCESS, "添加成功");
}
return new ResultVO(Status.FAILED, "添加失败");
}

@PostMapping(value = "/delete")
public ResultVO delete(@RequestBody List
<String> ids) {
    int i = 0;
    for (String id : ids) {
    if (${_serviceName}.delete(Long.valueOf(id))) {
    i++;
    }
    }
    return new ResultVO(Status.SUCCESS, i + "条数据删除成功");
    }

    @PostMapping(value = "/update")
    public ResultVO update(@RequestBody @Validated(BaseParam.update.class) ${entityDTO} dto) {
    ${entity} ${_entity} =${IEntityMapper}.INSTANCE.toEntity(dto);
    if (${_serviceName}.update(${_entity})) {
    return new ResultVO(Status.SUCCESS, "更新成功");
    }
    return new ResultVO(Status.FAILED, "更新失败");
    }

    @PostMapping(value = "/detail")
    public ResultVO detail(@RequestBody @Validated(BaseParam.detail.class) ${entityDTO} dto) {
    ${entity} ${_entity} = ${_serviceName}.find(Long.valueOf(dto.getId()));
    ${entityVO} ${_entityVO} = ${IEntityMapper}.INSTANCE.toVO(${_entity});
    return new ResultVO(Status.SUCCESS, "查询成功", ${_entityVO});
    }

    @PostMapping(value = "/search")
    public ResultVO search(@RequestBody @Validated(BaseParam.search.class) ${entityDTO} dto) {
    TableModel<${entityVO}> tableModel = new TableModel<>() {
    @Override
    public List<${entityVO}> fetchData() {
    QueryWrapper<${entity}> qw = new QueryWrapper<>();

    <#list tableStructs as tableStruct>
        <#if tableStruct.showInSearch>
            qw.lambda().eq(StrUtil.isNotBlank(dto.get${tableStruct.fieldDTO}()), ${entity}::get${tableStruct.fieldDB}, dto.get${tableStruct.fieldDTO}());
        </#if>
    </#list>

    if (StrUtil.isNotBlank(dto.getSort()) && StrUtil.isNotBlank(dto.getFilter())) {
    boolean isAscend = "ascend".equals(dto.getSort());
    qw.orderBy(true, isAscend, dto.getFilter());
    }
    Page<${entity}> page = ${_serviceName}.findList(qw, dto.getCurrent(), dto.getPageSize());
    this.setTotal(page.getTotal());
    return page.convert(${IEntityMapper}.INSTANCE::toVO).getRecords();
    }
    };
    return new ResultVO(Status.SUCCESS, "查询成功", tableModel);
    }
    }