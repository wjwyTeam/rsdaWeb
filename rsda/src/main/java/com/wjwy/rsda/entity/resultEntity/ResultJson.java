package com.wjwy.rsda.entity.resultEntity;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("Json>实体参数")
public class ResultJson {
	public boolean status;
	public String msg;
	public String exceptionMsg;
	public ResultJson GetJson(boolean status,String msg,String exceptionMsg) {
		this.status = status;
		this.msg = msg;
		this.exceptionMsg = exceptionMsg;
		return this;
	}
}
