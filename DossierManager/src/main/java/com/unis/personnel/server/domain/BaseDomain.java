package com.unis.personnel.server.domain;

import java.text.NumberFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * @author huangxing
 * @version 1.0
 * @Title BaseDomain
 * @ProjectName 持久化
 * @Description 
 * @date 2019/07/22 17点26分
 */
public class BaseDomain {
	/**
	 *   根据id获取最大信息编号
	 * @param maxId  当前最大编号
	 * @param preStr 数据前缀
	 * @param length 拼接长度
	 * @return
	 */
	protected String getMaxCodeById(String maxId,String preStr,int length) {
		String serialNumberStr = "";
		if (StringUtils.isEmpty(maxId)) {
			maxId = "0000000000000";
		}
		int serialNumber = 0;
		int maxSerialNumber = Integer.valueOf(maxId.substring(
				maxId.length() - length, maxId.length()));
		serialNumber = maxSerialNumber + 1;
		
		NumberFormat nf = NumberFormat.getInstance();
        //设置是否使用分组
        nf.setGroupingUsed(false);
        //设置最大整数位数
        nf.setMaximumIntegerDigits(length);
        //设置最小整数位数   
        nf.setMinimumIntegerDigits(length);
		serialNumberStr = nf.format(serialNumber);
		StringBuilder emailIdBuilder = new StringBuilder(preStr).append(serialNumberStr);
		return emailIdBuilder.toString();
	}
}
