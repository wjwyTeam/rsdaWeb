/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: zgr
 * @Date: 2019-12-03 14:49:36
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-06 11:33:00
 */
package com.wjwy.rsda.services;

import java.util.List;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.MD5Util;
import com.wjwy.rsda.common.util.UUIDUtils;
import com.wjwy.rsda.entity.User;
import com.wjwy.rsda.mapper.UserMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author ZHANGQI
 * @date 2019年11月20日
 * @Description
 */
@Service("userService")
@Transactional
public class UserService {
	@Autowired
	private UserMapper userDao;

	/**
	 * @param userQueryVO
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<User> getPageList(String userId, String userName, String deptId, String workDept, Boolean delFlag,
			Integer pageNum, Integer pageSize) {

		PageHelper.startPage(pageNum, pageSize);
		List<User> Users = getList(userId, userName, deptId, workDept, delFlag);
		PageInfo<User> PageInfoDO = new PageInfo<User>(Users);

		return PageInfoDO;
	}

	/**
	 * 登录验证
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public User getUserByLogin(String userName, String passWord) {
		User user = new User();
		user.setDelFlag(true);
		user.setUserName(userName);
		user.setPassWord(passWord);
		return userDao.selectOne(user);
	}

	/**
	 * @Description: 根据ID 删除数据
	 * @param @param  User
	 * @param @return
	 * @return int
	 * @throws @author ZHANGQI
	 * @date 2019年11月28日
	 */
	public int delete(User user) {
		/**
		 * 不可以删除 IsCandel 不能删除的用户
		 */
		Example example = new Example(User.class);
		Criteria criteria = example.createCriteria();

		criteria.andEqualTo("userId", user.getUserId());
		criteria.andEqualTo("isCandel", true);

		user.setDelFlag(false);
		int count = userDao.updateByExampleSelective(user, example);
		return count;
	}

	/**
	 * @Title: getPersonInfo @Description: TODO方法描述:(通过ID查找该条数据) @param @param
	 *         userId @param @return 设定文件 @return User 返回类型 @throws
	 */
	public User getPersonInfo(String userId) {
		User user = new User();
		user.setDelFlag(true);
		user.setUserId(userId);
		return userDao.selectOne(user);
	}

	/**
	 * @Description: 根据ID更新用户
	 * @param @param  User
	 * @param @return
	 * @return int
	 * @throws @author ZHANGQI
	 * @date 2019年11月28日
	 */
	public int update(User user) {
		Example example = new Example(User.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", user.getUserId());
		return userDao.updateByExampleSelective(user, example);
	}

	/**
	 * @Description: 新增
	 * @param @param  User
	 * @param @return
	 * @return int
	 * @throws @author ZHANGQ
	 * @date 2019年11月28日
	 */
	public int insert(User user) {
		user.setDelFlag(true);
		user.setIsCandel(true);
		user.setPassWord(MD5Util.md5ToHex("123456"));
		user.setUserId(UUIDUtils.uuid().toLowerCase());
		return userDao.insertSelective(user);
	}

	/**
	 * 列表查询
	 * 
	 * @param userId
	 * @param userName
	 * @param deptId
	 * @param workDept
	 * @param delFlag
	 * @return
	 */
	public List<User> getList(String userId, String userName, String deptId, String workDept, Boolean delFlag) {

		Example example = new Example(User.class);

		Criteria criteria = example.createCriteria();
		if (delFlag != null) {
			criteria.andEqualTo("delFlag", delFlag);
		}

		if (userId != null && StringUtils.isNotEmpty(userId)) {
			criteria.andEqualTo("userId", userId);
		}

		if (userName != null && StringUtils.isNotEmpty(userName)) {
			criteria.andEqualTo("userName", userName);
		}

		if (deptId != null && StringUtils.isNotEmpty(deptId)) {
			criteria.andEqualTo("deptId", deptId);
		}

		if (workDept != null && StringUtils.isNotEmpty(workDept)) {
			criteria.andEqualTo("workDept", workDept);
		}

		return userDao.selectByExample(example);
	}
}
