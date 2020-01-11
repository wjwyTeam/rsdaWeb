/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-11 11:12:18
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-11 11:15:07
 */
package com.wjwy.rsda.common.util;

import java.util.ArrayList;
import java.util.List;

import com.wjwy.rsda.common.enums.TreeNode;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TreeUtil {
 /**
  * 两层循环实现建树
  *
  * @param treeNodes 传入的树节点列表
  * @return
  */
 public <T extends TreeNode> List<T> bulid(List<T> treeNodes, Object root) {

  List<T> trees = new ArrayList<>();

  for (T treeNode : treeNodes) {

   if (root.equals(treeNode.getParentId())) {
    trees.add(treeNode);
   }

   for (T it : treeNodes) {
    if (it.getParentId() == treeNode.getId()) {
     if (treeNode.getChildren() == null) {
      treeNode.setChildren(new ArrayList<>());
     }
     treeNode.add(it);
    }
   }
  }
  return trees;
 }

 /**
  * 使用递归方法建树
  *
  * @param treeNodes
  * @return
  */
 public <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
  List<T> trees = new ArrayList<T>();
  for (T treeNode : treeNodes) {
   if (root.equals(treeNode.getParentId())) {
    trees.add(findChildren(treeNode, treeNodes));
   }
  }
  return trees;
 }

 /**
  * 递归查找子节点
  *
  * @param treeNodes
  * @return
  */
 public <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
  for (T it : treeNodes) {
   if (treeNode.getId() == it.getParentId()) {
    if (treeNode.getChildren() == null) {
     treeNode.setChildren(new ArrayList<>());
    }
    treeNode.add(findChildren(it, treeNodes));
   }
  }
  return treeNode;
 }
}
