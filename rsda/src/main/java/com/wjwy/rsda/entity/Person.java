package com.wjwy.rsda.entity;

import java.util.List;

import javax.persistence.Transient;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-17 08:36:32
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-17 08:42:59
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
public class Person{
 private String name;
 private String persid;
 private String unit;
 
 @Transient
 private List<DaJson> ydqx;
}