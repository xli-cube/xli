package com.xli.mis.tablebasic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@ToString
@TableName("table_basic")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableBasic implements Serializable {

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	private Long row_id;

	private String table_name;

	private String sql_table_name;

	private String entity_name;

	private Long datasource_id;

	private Integer order_num;
}
