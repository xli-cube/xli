package com.xli.menu.framerouter.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xli.dto.component.SelectItem;
import com.xli.dto.component.TreeNode;
import com.xli.dto.component.model.TableModel;
import com.xli.dto.component.model.TreeModel;
import com.xli.dto.params.TreeNodeDTO;
import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import com.xli.dto.validation.group.IGroup;
import com.xli.menu.framerouter.entity.dto.RouterAddDTO;
import com.xli.menu.framerouter.entity.dto.RouterSearchDTO;
import com.xli.menu.framerouter.entity.dto.RouterUpdateDTO;
import com.xli.menu.framerouter.entity.mapper.IFrameRouterMapper;
import com.xli.menu.framerouter.entity.vo.RouterVO;
import com.xli.metadata.utils.CodeModalFactory;
import com.xli.ui.menu.framerouter.entity.FrameRouter;
import com.xli.ui.menu.framerouter.service.IFrameRouterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Tag(name = "菜单管理")
@RestController
@RequestMapping("/frameRouter")
public class FrameRouterController {

    @Autowired
    IFrameRouterService iFrameRouterService;

    @Operation(summary = "新增菜单")
    @PostMapping(value = "/add")
    public ResultVO<String> add(@RequestBody @Validated(IGroup.add.class) RouterAddDTO dto) {
        FrameRouter frameRouter = IFrameRouterMapper.INSTANCE.toEntity(dto);
        frameRouter.setId(IdUtil.getSnowflakeNextId());
        if (iFrameRouterService.insert(frameRouter)) {
            return new ResultVO<>(Status.SUCCESS, "添加成功");
        }
        return new ResultVO<>(Status.FAILED, "添加失败");
    }

    @Operation(summary = "删除菜单")
    @PostMapping(value = "/delete")
    public ResultVO<String> delete(@RequestBody List<String> ids) {
        int i = 0;
        for (String id : ids) {
            if (iFrameRouterService.delete(Long.parseLong(id))) {
                i++;
            }
        }
        return new ResultVO<>(Status.SUCCESS, i + "条数据删除成功");
    }

    @Operation(summary = "更新菜单")
    @PostMapping(value = "/update")
    public ResultVO<String> update(@RequestBody @Validated(IGroup.update.class) RouterUpdateDTO dto) {
        FrameRouter frameRouter = IFrameRouterMapper.INSTANCE.toEntity(dto);
        if (iFrameRouterService.update(frameRouter)) {
            return new ResultVO<>(Status.SUCCESS, "更新成功");
        }
        return new ResultVO<>(Status.FAILED, "更新失败");
    }

    @Operation(summary = "查询详情")
    @PostMapping(value = "/detail/{id}")
    public ResultVO<RouterVO> detail(@PathVariable("id") @NotBlank String id) {
        FrameRouter frameRouter = iFrameRouterService.find(Long.valueOf(id));
        if (frameRouter!=null) {
            return new ResultVO<>(Status.SUCCESS, "查询成功",IFrameRouterMapper.INSTANCE.toVO(frameRouter));
        }
        return new ResultVO<>(Status.FAILED, "查询失败");
    }

    @Operation(summary = "查询列表")
    @PostMapping(value = "/search")
    public ResultVO<TableModel<RouterVO>> search(@RequestBody @Validated(IGroup.search.class) RouterSearchDTO dto) {
        TableModel<RouterVO> tableModel = new TableModel<>() {
            @Override
            public List<RouterVO> fetch() {
                QueryWrapper<FrameRouter> qw = new QueryWrapper<>();
                qw.lambda()
                        .like(StrUtil.isNotBlank(dto.getMenuName()), FrameRouter::getMenu_name, dto.getMenuName())
                        .eq(StrUtil.isNotBlank(dto.getPid()), FrameRouter::getPid, dto.getPid());

                if (StrUtil.isNotBlank(dto.getFilter())) {
                    boolean isAscend = false;
                    if (StrUtil.isNotBlank(dto.getSort())) {
                        isAscend = "ascend".equals(dto.getSort());
                    }
                    qw.orderBy(true, isAscend, dto.getFilter());
                }

                Page<FrameRouter> page = iFrameRouterService.findList(qw, dto.getCurrent(), dto.getPageSize());
                this.setTotal(page.getTotal());
                return page.convert(IFrameRouterMapper.INSTANCE::toVO).getRecords();
            }
        };
        return new ResultVO<>(Status.SUCCESS, "查询成功", tableModel);
    }

    @Operation(summary = "查询菜单")
    @PostMapping(value = "/searchMenu")
    public ResultVO<JSONArray> searchMenu() {
        String json = "[{path:'/frame',name:'frame',routes:[{path:'organ',name:'frame-organ',routes:[{path:'user',name:'frame-organ-user',component:'./frame/organ/user/FrameUserPage',},{path:'ou',name:'frame-organ-ou',component:'./frame/organ/ou/FrameOuPage',},{path:'role',name:'frame-organ-role',component:'./frame/organ/role/FrameRolePage',}],},{path:'metadata',name:'frame-metadata',routes:[{path:'config',name:'frame-metadata-config',component:'./frame/metadata/frameconfig/FrameConfigPage',},{path:'codemain',name:'frame-metadata-codemain',component:'./frame/metadata/codemain/CodeMainList',},{path:'framerouter',name:'frame-metadata-framerouter',component:'./frame/metadata/framerouter/FrameRouterList',}]},{path:'datamanage',name:'frame-datamanage',routes:[{path:'datasource',name:'frame-datamanage-datasource',component:'./frame/datamanage/datasource/DataSourceList',},{path:'tablebasic',name:'frame-datamanage-tablebasic',component:'./frame/datamanage/tablebasic/TableBasicList',}]}],},{path:'/user',layout:false,routes:[{name:'login',path:'/user/login',component:'./User/Login',},],},{path:'/welcome',name:'welcome',icon:'smile',component:'./Welcome',},{path:'/admin',name:'admin',icon:'crown',access:'canAdmin',routes:[{path:'/admin',redirect:'/admin/sub-page'},{path:'/admin/sub-page',name:'sub-page',component:'./Admin'}]},{name:'list.table-list',icon:'table',path:'/list',component:'./TableList'},{path:'/',redirect:'/welcome'},{path:'*',layout:false,component:'./404'}]";
        return new ResultVO<>(Status.SUCCESS, "查询成功", JSONUtil.parseArray(json));
    }

    @Operation(summary = "查询菜单层级树")
    @PostMapping(value = "/fetchRouterTree")
    public ResultVO<TreeModel<RouterVO>> fetchRouterTree(@RequestBody @Validated(IGroup.search.class) TreeNodeDTO dto) {
        TreeModel<RouterVO> treeModel = new TreeModel<>() {
            @Override
            public List<TreeNode<RouterVO>> fetch(TreeNode<RouterVO> treeNode) {
                List<TreeNode<RouterVO>> treeNodeList = new ArrayList<>();
                if (treeNode == null) {
                    List<FrameRouter> frameRouterList = iFrameRouterService.findList(new QueryWrapper<>());
                    for (FrameRouter frameRouter : frameRouterList) {
                        RouterVO routerVO=  IFrameRouterMapper.INSTANCE.toVO(frameRouter);
                        TreeNode<RouterVO> childNode = new TreeNode<>();
                        childNode.setId(routerVO.getId());
                        childNode.setText(routerVO.getMenuName());
                        childNode.setPid(routerVO.getPid() == null ? "" : routerVO.getPid());
                        treeNodeList.add(childNode);
                    }
                }
                return treeNodeList;
            }
        };
        return new ResultVO<>(Status.SUCCESS, "查询成功", treeModel);
    }

    @Operation(summary = "是否显示")
    @PostMapping(value = "/getVisibleModel")
    public ResultVO<List<SelectItem>> getVisibleModel() {
        List<SelectItem> visiableList = CodeModalFactory.factory("是否", false);
        return new ResultVO<>(Status.SUCCESS, "查询成功", visiableList);
    }

    @Operation(summary = "是否启用")
    @PostMapping(value = "/getEnabledModel")
    public ResultVO<List<SelectItem>> getEnabledModel() {
        List<SelectItem> enabledList = CodeModalFactory.factory("是否", false);
        return new ResultVO<>(Status.SUCCESS, "查询成功", enabledList);
    }

    @Operation(summary = "路由类型代码项")
    @PostMapping(value = "/getRouterTypeModel")
    public ResultVO<List<SelectItem>> getRouterTypeModel() {
        List<SelectItem> routerTypeList = CodeModalFactory.factory("路由类型", false);
        return new ResultVO<>(Status.SUCCESS, "查询成功", routerTypeList);
    }
}
