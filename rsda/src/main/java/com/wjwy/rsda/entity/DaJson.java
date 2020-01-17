package com.wjwy.rsda.entity;

import java.util.List;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-17 08:45:20
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-17 11:05:15
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DaJson {
 private String id;
 private String pid;
 private String title;
 private Boolean disabled;
 private Boolean checked;
 private Integer pageNum;
 private List<DaJson> children;
 private List<Electronic> photos;
}