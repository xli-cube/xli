package com.xli.mis.tablestruct.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@TableName("table_struct")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableStruct implements Serializable {

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	private Long row_id;

	private Long table_id;

	private String sql_table_name;

	private String field_name_en;

	private String field_name_cn;

	private String field_type;

	private String control_type;

	private Integer field_length;

	private String notnull;

	private Long code_id;

	private String show_in_table;

	private String show_in_detail;

	private String show_in_search;

	private Integer order_num;
}
