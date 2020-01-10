package com.wjwy.rsda.services;

import java.text.NumberFormat;

import com.wjwy.rsda.common.util.StringUtils;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-10 09:07:43
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-10 09:08:41
 */

public class BaseService {
 public String getMaxCodeById(String maxId, String preStr, int length) {
  String serialNumberStr = "";
  if (StringUtils.isEmpty(maxId)) {
   maxId = "0000000000000";
  }
  int serialNumber = 0;
  int maxSerialNumber = Integer.valueOf(maxId.substring(maxId.length() - length, maxId.length()));
  serialNumber = maxSerialNumber + 1;

  NumberFormat nf = NumberFormat.getInstance();
  // 设置是否使用分组
  nf.setGroupingUsed(false);
  // 设置最大整数位数
  nf.setMaximumIntegerDigits(length);
  // 设置最小整数位数
  nf.setMinimumIntegerDigits(length);
  serialNumberStr = nf.format(serialNumber);
  StringBuilder emailIdBuilder = new StringBuilder(preStr).append(serialNumberStr);
  return emailIdBuilder.toString();
 }


}