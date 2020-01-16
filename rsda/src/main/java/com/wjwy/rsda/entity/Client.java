/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-15 10:32:02
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-16 10:44:26
 */
package com.wjwy.rsda.entity;

import javax.websocket.Session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {

    private static final long serialVersionUID = 8957107006902627635L;

    private String userName;

    private Session session;

}
