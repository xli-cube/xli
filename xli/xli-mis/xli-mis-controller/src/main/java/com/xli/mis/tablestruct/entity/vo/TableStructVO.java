package com.xli.mis.tablestruct.entity.vo;

import lombok.Data;


@Data
public class TableStructVO {

    private String id;

    private String fieldNameEn;

    private String fieldNameCn;

    private String fieldType;

    private Integer fieldLength;

    private Integer notnull;

    private String tableId;

    private String controlType;

    private String codeId;

    private String showInTable;

    private String showInDetail;

    private String showInSearch;

    private Integer orderNum;
}
