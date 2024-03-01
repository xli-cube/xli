package com.xli.mis.tablebasic.entity.vo;

import lombok.Data;


@Data
public class TableBasicVO {

    private String id;

    private String tableName;

    private String sqlTableName;

    private String entityName;

    private String datasourceId;

    private Integer orderNum;
}
