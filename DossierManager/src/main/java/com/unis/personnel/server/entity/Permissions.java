package com.unis.personnel.server.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ClassName: Permissions
 * @Description: 权限表
 * @author ZHANGQI
 * @date 2019年11月29日
 */

@ToString
@Getter
@Setter
public class Permissions {
	private String id;
    private String permissionsName;
}
