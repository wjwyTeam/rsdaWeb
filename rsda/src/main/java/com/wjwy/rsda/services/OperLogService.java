/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-17 18:17:36
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-11 09:40:34
 */
package com.wjwy.rsda.services;

import java.util.List;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.wjwy.rsda.entity.Department;
import com.wjwy.rsda.entity.OperLog;
import com.wjwy.rsda.mapper.DepartmentMapper;
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
	@Autowired
	private DepartmentMapper departmentMapper;
	public PageInfo<OperLog> getPageList(String operName, Integer pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(OperLog.class);
		example.setOrderByClause("oper_time DESC");
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
		if (StringUtil.isNotEmpty(operLog.getDeptName())) {
			 	Example example = new Example(Department.class);
			 	Criteria criteria = example.createCriteria();
				 criteria.andEqualTo("id",operLog.getDeptName());
					Department de = departmentMapper.selectOneByExample(example);
					if(de != null){
								operLog.setDeptName(de.getName());
					}
					
			}
			operLoguserDao.insertOperlog(operLog);
		}

		/**
			* 根据ID查询详情数据
			* @param operId
			* @return
		 */
	public OperLog getId(Long operId) {
		Example example = new Example(OperLog.class);
		Criteria criteria = example.createCriteria();

		criteria.andEqualTo("operId", operId);
		return operLoguserDao.selectOneByExample(example);
	}

}