/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-11 11:14:09
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-11 11:17:44
 */
package com.wjwy.rsda.common.enums;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TreeNode {
 protected String id;
 protected String parentId;
 protected String name;
 protected List<TreeNode> children = new ArrayList<TreeNode>();

 public void add(TreeNode node) {
  children.add(node);
 }
}