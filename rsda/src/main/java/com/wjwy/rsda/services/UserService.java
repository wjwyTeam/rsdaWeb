/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: zgr
 * @Date: 2019-12-03 14:49:36
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-17 18:54:56
 */
package com.wjwy.rsda.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.MD5Util;
import com.wjwy.rsda.entity.Role;
import com.wjwy.rsda.entity.User;
import com.wjwy.rsda.entity.UserRole;
import com.wjwy.rsda.mapper.RoleMapper;
import com.wjwy.rsda.mapper.UserMapper;
import com.wjwy.rsda.mapper.UserRoleMapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	@Autowired
	private UserRoleMapper userRoleDao;

	@Autowired
	private RoleMapper roleDao;

	public Logger logger = LoggerFactory.getLogger(UserService.class);

	/**
	 * @param userQueryVO
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<User> getPageList(String userId, String userName, String deptId, String workDept, Boolean delFlag,
			Integer pageNum, Integer pageSize) {

		PageHelper.startPage(pageNum, pageSize);
		List<User> users = getList(userId, userName, deptId, workDept, delFlag);
		if (users == null) {
			return null;
		}
		PageInfo<User> PageInfoDO = new PageInfo<User>(users);
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
		user.setDelFlag(false);
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
		List<User> userNew = userDao.selectByExample(example);
		criteria.andEqualTo("isCandel", false);
		int count = userDao.deleteByExample(example);
		if (userNew.size() == 0) {
			count = HttpStatus.GONE.value();
		}
		return count;
	}

	/**
	 * @Title: getPersonInfo @Description: TODO方法描述:(通过ID查找该条数据) @param @param
	 *         userId @param @return 设定文件 @return User 返回类型 @throws
	 */
	public User getPersonInfo(String userId) {
		User user = new User();
		user.setDelFlag(false);
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
	
		user.setPassWord(MD5Util.md5ToHex(user.getPassWord()));
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

		user.setPassWord(MD5Util.md5ToHex("123456"));
		user.setUserId(UUID.randomUUID().toString().toLowerCase());
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

		List<User> users = userDao.selectByExample(example);
		if (users == null) {
			return null;
		}
		List<User> userNew = new ArrayList<User>();
		for (User user : users) {

			Example exampleUserRole = new Example(UserRole.class);
			Criteria criteriaUserRole = exampleUserRole.createCriteria();
			criteriaUserRole.andEqualTo("userId", user.getUserId());
			List<UserRole> userRoleNew = userRoleDao.selectByExample(exampleUserRole);

			List<Role> roleNewList = new ArrayList<Role>();
			if (!userRoleNew.isEmpty()) {
				for (UserRole userRole : userRoleNew) {
					Role role = new Role();
					Example exampleRole = new Example(Role.class);
					Criteria criteriaRole = exampleRole.createCriteria();
					criteriaRole.andEqualTo("id", userRole.getRoleId());
					role = roleDao.selectOneByExample(exampleRole);
					roleNewList.add(role);
				}
				user.setRoles(roleNewList);
			}
			
			userNew.add(user);
		}
		return userNew;
	}

	/**
	 * 用户添加多个角色
	 * 
	 * @param userId
	 * @param ids
	 * @return
	 */
	public boolean userSelRole(String userId, String[] ids) {
		if (userId.isEmpty() || userId == null) {
			return false;
		}
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		Example example = new Example(UserRole.class);
		Criteria criteria = example.createCriteria();
		// 将当前用户所选角色全部清除，新增
		criteria.andEqualTo("userId", userId);
		logger.info(userId, userRoleDao.deleteByExample(example));
		for (String roleId : ids) {
			criteria.andEqualTo("roleId", roleId);
			userRole.setRId(UUID.randomUUID().toString().toLowerCase());
			userRole.setRoleId(roleId);
			logger.info(roleId, userRoleDao.insertSelective(userRole));
		}

		return true;
	}

	
}
