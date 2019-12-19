/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-17 18:17:36
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-19 17:25:13
 */
package com.wjwy.rsda.services;

import java.util.List;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.OperLog;
import com.wjwy.rsda.mapper.OperLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-17 18:17:36
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-18 15:46:05
 */

@Service("operLogService")
@Transactional
public class OperLogService {

	@Autowired
	private OperLogMapper operLoguserDao;

	public PageInfo<OperLog> getPageList(String operName, Integer pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(OperLog.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("operName", operName);
		List<OperLog> logs = operLoguserDao.selectByExample(example);
		if (logs == null) {
			return null;
		}
		PageInfo<OperLog> PageInfoDO = new PageInfo<OperLog>(logs);
		return PageInfoDO;
	}

	/**
	 * 新增操作日志
	 * 
	 * @param operLog 操作日志对象
	 */

		public void insertOperlog(OperLog operLog) {
			operLoguserDao.insertOperlog(operLog);
		}

}