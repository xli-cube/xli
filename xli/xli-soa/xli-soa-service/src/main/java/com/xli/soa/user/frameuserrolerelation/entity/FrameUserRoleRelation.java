package com.xli.soa.user.frameuserrolerelation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("frame_userrolerelation")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FrameUserRoleRelation implements Serializable {

	@Serial
	private static final long serialVersionUID = 6375153950485620452L;

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	private Long row_id;

	private Long user_id;

	private Long role_id;
}
