/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: zgr
 * @Date: 2019-12-03 14:49:36
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-13 13:59:57
 */
package com.wjwy.rsda.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.tool.except.CaptchaException;
import com.wjwy.rsda.common.tool.except.UserBlockedException;
import com.wjwy.rsda.common.tool.except.UserDeleteException;
import com.wjwy.rsda.common.tool.except.UserNotExistsException;
import com.wjwy.rsda.common.tool.except.UserPasswordNotMatchException;
import com.wjwy.rsda.common.tool.factory.AsyncFactory;
import com.wjwy.rsda.common.tool.factory.AsyncManager;
import com.wjwy.rsda.common.util.DateUtils;
import com.wjwy.rsda.common.util.MD5Util;
import com.wjwy.rsda.common.util.MessageUtils;
import com.wjwy.rsda.common.util.ServletUtils;
import com.wjwy.rsda.common.util.ShiroUtils;
import com.wjwy.rsda.common.util.StringUtils;
import com.wjwy.rsda.entity.Role;
import com.wjwy.rsda.entity.User;
import com.wjwy.rsda.entity.UserRole;
import com.wjwy.rsda.common.enums.EnumEntitys;
import com.wjwy.rsda.common.enums.MessageConstant;
import com.wjwy.rsda.common.enums.ShiroConstants;
import com.wjwy.rsda.mapper.RoleMapper;
import com.wjwy.rsda.mapper.UserMapper;
import com.wjwy.rsda.mapper.UserRoleMapper;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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

	@Autowired
	private PasswordService passwordService;

	public Logger logger = LoggerFactory.getLogger(UserService.class);

	/**
	 * @param userQueryVO
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<User> getPageList(String userId, String userName, String deptId, String workDept, Boolean delFlag,
			Integer pageNum, Integer pageSize, String roleId) {

		PageHelper.startPage(pageNum, pageSize);
		List<User> users = getList(userId, userName, deptId, workDept, delFlag, roleId);
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
		User user1 = userDao.selectOneByExample(example);
		user.setPassWord(
				passwordService.encryptPassword(user.getUserName(), MD5Util.md5ToHex(user.getPassWord()), user1.getSalt()));
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

		user.setSalt(ShiroUtils.randomSalt());
		user.setPassWord(
				passwordService.encryptPassword(user.getUserName(), MD5Util.md5ToHex(user.getPassWord()), user.getSalt()));
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
	public List<User> getList(String userId, String userName, String deptId, String workDept, Boolean delFlag,
			String roleId) {
		Subject subject = SecurityUtils.getSubject();

		if (subject != null && subject.getPrincipals() != null) {
			User userobj = subject.getPrincipals().oneByType(User.class);
			if (userobj != null) {
				userId = userobj.getUserId();
			}
			Example exampleR = new Example(UserRole.class);
			Criteria criteriaR = exampleR.createCriteria();
			criteriaR.andEqualTo("userId", userId);
			List<UserRole> ur = userRoleDao.selectByExample(exampleR);
			for (UserRole userRole : ur) {
				Example exampleT = new Example(Role.class);
				Criteria criteriaT = exampleT.createCriteria();
				criteriaT.andEqualTo("id", userRole.getRoleId());
				List<Role> r = roleDao.selectByExample(exampleT);
				for (Role role : r) {
					if (role.getEnName().equals(EnumEntitys.SUPER.getValue())) {
						userId = null;
					}
				}
			}

		}
		Example example = new Example(User.class);
		// 注意：排序使用的是列名
		example.setOrderByClause("create_time DESC");
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
					criteriaRole.andEqualTo("roleStatus", true);
					role = roleDao.selectOneByExample(exampleRole);
					roleNewList.add(role);
					if (StringUtils.isNotEmpty(roleId) && roleId.equals(userRole.getRoleId())) {
						// 已分配
						user.setISRole(true);
					}

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
			logger.info("=========================================》", userRoleDao.insertSelective(userRole));
		}

		return true;
	}

	/**
	 * 取消授权
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public boolean userDelRole(String userId, String roleId) {

		if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(roleId)) {
			return false;
		}
		Example example = new Example(UserRole.class);
		Criteria criteria = example.createCriteria();
		// 将当前用户所选角色全部清除，新增
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("roleId", roleId);
		logger.info("=========================================》", userRoleDao.deleteByExample(example));
		return true;
	}

	/**
	 * 授权
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public boolean userInRole(String userId, String roleId) {
		if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(roleId)) {
			return false;
		}
		UserRole userRole = new UserRole();
		userRole.setRId(UUID.randomUUID().toString().toLowerCase());
		userRole.setRoleId(roleId);
		userRole.setUserId(userId);
		logger.info("=========================================》", userRoleDao.insertSelective(userRole));
		return true;
	}

	/**
	 * selectUserByLoginName
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public User selectUserByLoginName(String userName) {
		User user = new User();
		user.setDelFlag(false);
		user.setUserName(userName);
		return userDao.selectOne(user);
	}

	/**
	 * selectUserByPhoneNumber
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public User selectUserByPhoneNumber(String userName) {
		User user = new User();
		user.setDelFlag(false);
		user.setPhone(userName);
		return userDao.selectOne(user);
	}

	/**
	 * 登录
	 */
	public User login(String userName, String passWord) {

		// 验证码校验。
		if (ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA) != null) {
			AsyncManager.me().execute(
					AsyncFactory.recordLogininfor(userName, MessageConstant.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
			throw new CaptchaException();
		}
		// 用户名或密码为空 错误
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)) {
			AsyncManager.me()
					.execute(AsyncFactory.recordLogininfor(userName, MessageConstant.LOGIN_FAIL, MessageUtils.message("not.null")));
			throw new UserNotExistsException();
		}
		// 密码如果不在指定范围内 错误
		if (passWord.length() < MessageConstant.PASSWORD_MIN_LENGTH
				|| passWord.length() > MessageConstant.PASSWORD_MAX_LENGTH) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, MessageConstant.LOGIN_FAIL,
					MessageUtils.message("user.passWord.not.match")));
			throw new UserPasswordNotMatchException();
		}

		// 用户名不在指定范围内 错误
		if (userName.length() < MessageConstant.USERNAME_MIN_LENGTH
				|| userName.length() > MessageConstant.USERNAME_MAX_LENGTH) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, MessageConstant.LOGIN_FAIL,
					MessageUtils.message("user.passWord.not.match")));
			throw new UserPasswordNotMatchException();
		}
		// 查询用户信息
		User user = selectUserByLoginName(userName);

		if (user == null && maybeMobilePhoneNumber(userName)) {
			user = selectUserByPhoneNumber(userName);
		}

		if (user == null) {
			AsyncManager.me().execute(
					AsyncFactory.recordLogininfor(userName, MessageConstant.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
			throw new UserNotExistsException();
		}

		if (EnumEntitys.DELETED.getValue().equals(user.getDelFlag())) {
			AsyncManager.me().execute(
					AsyncFactory.recordLogininfor(userName, MessageConstant.LOGIN_FAIL, MessageUtils.message("user.password.delete")));
			throw new UserDeleteException();
		}

		if (EnumEntitys.DISABLE.getValue().equals(user.getUserType())) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, MessageConstant.LOGIN_FAIL,
					MessageUtils.message("user.blocked", user.getRemark())));
			throw new UserBlockedException();
		}

		passwordService.validate(user, passWord);

		AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, MessageConstant.LOGIN_SUCCESS, "登录成功"));
		recordLoginInfo(user);
		return user;
	}

	private boolean maybeMobilePhoneNumber(String username) {
		if (!username.matches(MessageConstant.MOBILE_PHONE_NUMBER_PATTERN)) {
			return false;
		}
		return true;
	}

	/**
	 * 记录登录信息
	 * 
	 * @return
	 */
	public int recordLoginInfo(User user) {
		user.setUserIp(ShiroUtils.getIp());
		user.setCreateTime(DateUtils.getNowDate());
		Example example = new Example(User.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", user.getUserId());
		return userDao.updateByExampleSelective(user, example);
	}

}
