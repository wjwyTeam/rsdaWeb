/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 08:52:38
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-11 10:49:18
 */
package com.wjwy.rsda.common;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {

	private Object id;
	@JsonProperty("parentId")
	private Object parentId;
	private String title;
	private String icon;
	private String href;
	private Boolean spread;
	private String filed;
	private List<TreeNode> children = new ArrayList<TreeNode>();
	private String checked = "0";// 0代表不选中 1代表选中

	/**
	 * 首页左边导航树的构造器
	 */
	public TreeNode(Object id, Object parentId, String title, String icon, String href, Boolean spread) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.title = title;
		this.icon = icon;
		this.href = href;
		this.spread = spread;
	}

	/**
	 * dtree的数据格式
	 * 
	 * @param id
	 * @param pid
	 * @param title
	 * @param spread
	 */
	public TreeNode(Object id, Object parentId, String title, Boolean spread) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.title = title;
		this.spread = spread;

	}

	/**
	 * dTree复选树的构造器
	 * 
	 * @param id
	 * @param pid
	 * @param title
	 * @param spread
	 * @param checkArr
	 */
	public TreeNode(Object id, Object parentId, String title, Boolean spread, String checked) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.title = title;
		this.spread = spread;
		this.checked = checked;
	}

}
