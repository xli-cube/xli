package com.xli.mis.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@ToString
@TableName("datasource")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataSource implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long row_id;

    private String url;

    private String user;

    private String pwd;

    private Integer order_num;
}
