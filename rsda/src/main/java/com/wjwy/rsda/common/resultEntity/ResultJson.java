/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 08:27:14
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-06 18:04:20
 */
package com.wjwy.rsda.common.resultEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResultJson {
	public Object status;
	public Object msg;
	public Object data;
	public ResultJson GetJson(Object status,Object msg,Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
		return this;
	}

	public ResultJson(Object data) {
		super();
		this.data = data;
	}
}