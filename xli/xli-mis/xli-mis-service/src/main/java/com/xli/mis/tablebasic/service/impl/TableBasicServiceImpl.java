package com.xli.mis.tablebasic.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.xli.mis.datasource.entity.DataSource;
import com.xli.mis.tablebasic.entity.TableBasic;
import com.xli.mis.tablebasic.mapper.TableBasicMapper;
import com.xli.mis.tablebasic.service.ITableBasicService;
import com.xli.mis.tablestruct.entity.TableStruct;
import com.xli.mis.tablestruct.service.ITableStructService;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;


@Service
public class TableBasicServiceImpl extends ServiceImpl<TableBasicMapper, TableBasic> implements ITableBasicService {

    @Autowired
    ITableStructService iTableStructService;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Autowired
    private TableBasicMapper tablebasicMapper;

    @Override
    public boolean insert(TableBasic tableBasic) {
        try {
            tablebasicMapper.createTable(tableBasic.getSql_table_name(), tableBasic.getTable_name());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.save(tableBasic);
    }

    @Override
    public boolean delete(Long id) {
        TableBasic tableBasic = find(id);
        if (tableBasic != null) {
            List<TableStruct> tableStructList = iTableStructService.findListByTableId(id);
            for (TableStruct tableStruct : tableStructList) {
                iTableStructService.delete(tableStruct.getId(), false);
            }
            boolean exists = tablebasicMapper.tableExists(tableBasic.getSql_table_name());
            if (exists) {
                try {
                    tablebasicMapper.dropTable(tableBasic.getSql_table_name());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return this.removeById(id);
        }
        return false;
    }

    @Override
    public boolean update(TableBasic tableBasic) {
        return this.updateById(tableBasic);
    }

    @Override
    public TableBasic find(Long id) {
        return this.getById(id);
    }

    @Override
    public Page<TableBasic> findList(QueryWrapper<TableBasic> qw, long current, long size) {
        return this.page(new Page<>(current, size), qw);
    }

    @Override
    public void generatorCode(DataSource dataSource, TableBasic tableBasic, List<TableStruct> tableStructList, String author, String outputDir, String mapOutputDir) {
        if (null == dataSource) {
            dataSource = new DataSource();
            dataSource.setUrl(url);
            dataSource.setUser(username);
            dataSource.setPwd(password);
        }
        String classPath = outputDir + File.separator + "com" + File.separator + "xli" + File.separator + StrUtil.lowerFirst(tableBasic.getEntity_name());
        String antDesignPath = outputDir + File.separator + "web" + File.separator + tableBasic.getEntity_name() + "List";
        DataSourceConfig.Builder config = new DataSourceConfig.Builder(dataSource.getUrl(), dataSource.getUser(), dataSource.getPwd());
        FastAutoGenerator.create(config).templateConfig(builder -> {
                    builder.entity("/templates/entity/entity.java");
                    builder.mapper("/templates/mapper/mapper.java");
                    builder.service("/templates/service/service.java");
                    builder.serviceImpl("/templates/service/impl/serviceImpl.java");
                    builder.controller("/templates/controller/controller.java");
                    builder.disable(TemplateType.XML);
                }).globalConfig(builder -> {
                    //全局配置（作者、java类输出路径、默认不打开文件夹）
                    builder.author(author).outputDir(outputDir).disableOpenDir();
                }).packageConfig(builder -> {
                    //包配置（包路径、模块名、mapper路径）
                    builder.parent("com.xli").moduleName(StrUtil.lowerFirst(tableBasic.getEntity_name())).pathInfo(Collections.singletonMap(OutputFile.xml, mapOutputDir));
                }).strategyConfig(builder -> {
                    //策略配置（表名、开启Lombok、字段上加注解、下划线不转驼峰）
                    builder.addInclude(tableBasic.getSql_table_name())
                            .entityBuilder()
                            .convertFileName((entity) -> tableBasic.getEntity_name())
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .columnNaming(NamingStrategy.no_change)
                            .idType(IdType.ASSIGN_UUID)
                            //entity开启文件覆盖
                            .enableFileOverride()
                            .serviceBuilder()
                            //service开启文件覆盖
                            .enableFileOverride()
                            .mapperBuilder()
                            .mapperAnnotation(Mapper.class)
                            //mapper开启文件覆盖
                            .enableFileOverride()
                            .controllerBuilder()
                            //controller开启文件覆盖
                            .enableFileOverride();
                }).injectionConfig(builder -> builder.beforeOutputFile((tableInfo, objectMap) -> {
                            //预处理模板信息
                            objectMap.put("author", author);
                            objectMap.put("_entity", StrUtil.lowerFirst(tableInfo.getEntityName()));
                            objectMap.put("_serviceName", StrUtil.lowerFirst(tableInfo.getServiceName()));
                            objectMap.put("entityDTO", tableInfo.getEntityName() + "DTO");
                            objectMap.put("entityVO", tableInfo.getEntityName() + "VO");
                            objectMap.put("_entityDTO", StrUtil.lowerFirst(tableInfo.getEntityName()) + "DTO");
                            objectMap.put("_entityVO", StrUtil.lowerFirst(tableInfo.getEntityName()) + "VO");
                            objectMap.put("IEntityMapper", "I" + tableInfo.getEntityName() + "Mapper");

                            List<Map<String, Object>> tableStructs = new ArrayList<>();
                            for (TableStruct tableStruct : tableStructList) {
                                Map<String, Object> tableStructMap = new HashMap<>();
                                tableStructMap.put("showInSearch", "1".equals(tableStruct.getShow_in_search()));
                                tableStructMap.put("fieldDTO", StrUtil.upperFirst(StrUtil.toCamelCase(tableStruct.getField_name_en())));
                                tableStructMap.put("fieldDB", StrUtil.upperFirst(tableStruct.getField_name_en()));

                                //生成IEntityMapper参数
                                String field = tableStruct.getField_name_en();
                                String fieldCamel = StrUtil.toCamelCase(tableStruct.getField_name_en());
                                tableStructMap.put("field", field);
                                tableStructMap.put("fieldCamel", fieldCamel);
                                //---------------------------------------------------------------------------
                                //生成DTO参数
                                String fieldType = "string";
                                if ("TextBox".equals(tableStruct.getControl_type()) || "TextArea".equals(tableStruct.getControl_type()) || "Integer".equals(tableStruct.getControl_type())) {
                                    fieldType = "string";
                                }
                                tableStructMap.put("fieldType", fieldType);
                                //---------------------------------------------------------------------------
                                //生成antdesign组件
                                tableStructMap.put("showInTable", "1".equals(tableStruct.getShow_in_table()));
                                tableStructMap.put("fieldNameCn", tableStruct.getField_name_cn());

                                tableStructMap.put("notnull", "1".equals(tableStruct.getNotnull()));
                                //---------------------------------------------------------------------------
                                tableStructs.add(tableStructMap);
                            }
                            //生成antdesign组件
                            objectMap.put("componentName", tableBasic.getEntity_name() + "List");
                            //---------------------------------------------------------------------------
                            objectMap.put("tableStructs", tableStructs);
                        }).customFile(
                                customBuilder -> {
                                    customBuilder.fileName("I" + tableBasic.getEntity_name() + "Mapper.java").templatePath("/templates/entity/mapper/iEntityMapper.java.ftl").filePath(classPath + File.separator + "entity" + File.separator + "mapper").enableFileOverride();
                                }
                        ).customFile(
                                customBuilder -> {
                                    customBuilder.fileName(tableBasic.getEntity_name() + "DTO.java").templatePath("/templates/entity/dto/entityDTO.java.ftl").filePath(classPath + File.separator + "entity" + File.separator + "dto").enableFileOverride();
                                })
                        .customFile(
                                customBuilder -> {
                                    customBuilder.fileName(tableBasic.getEntity_name() + "VO.java").templatePath("/templates/entity/vo/entityVO.java.ftl").filePath(classPath + File.separator + "entity" + File.separator + "vo").enableFileOverride();
                                })
                        .customFile(
                                customBuilder -> {
                                    customBuilder.fileName("data.d.ts").templatePath("/templates/web/data.d.ts.ftl").filePath(antDesignPath).enableFileOverride();
                                })
                        .customFile(
                                customBuilder -> {
                                    customBuilder.fileName("service.ts").templatePath("/templates/web/service.ts.ftl").filePath(antDesignPath).enableFileOverride();
                                })
                        .customFile(
                                customBuilder -> {
                                    customBuilder.fileName("index.ts").templatePath("/templates/web/index.tsx.ftl").filePath(antDesignPath).enableFileOverride();
                                })
                        .customFile(
                                customBuilder -> {
                                    customBuilder.fileName("CreateForm.ts").templatePath("/templates/web/components/CreateForm.tsx.ftl").filePath(antDesignPath + File.separator + "components").enableFileOverride();
                                })
                        .customFile(
                                customBuilder -> {
                                    customBuilder.fileName("UpdateForm.ts").templatePath("/templates/web/components/UpdateForm.tsx.ftl").filePath(antDesignPath + File.separator + "components").enableFileOverride();
                                }).build())
                .templateEngine(new FreemarkerTemplateEngine() {
                    //指定一个模板引擎
                    @Override
                    protected void outputCustomFile(@NotNull List<CustomFile> customFile, @NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
                        String parentPath = this.getPathInfo(OutputFile.parent);
                        customFile.forEach((file) -> {
                            String filePath = StringUtils.isNotBlank(file.getFilePath()) ? file.getFilePath() : parentPath;
                            if (StringUtils.isNotBlank(file.getPackageName())) {
                                filePath = filePath + File.separator + file.getPackageName();
                                filePath = filePath.replaceAll("\\.", "\\" + File.separator);
                            }
                            String fileName = filePath + File.separator + file.getFileName();
                            this.outputFile(new File(fileName), objectMap, file.getTemplatePath(), file.isFileOverride());
                        });
                    }
                }).execute();
    }
}
