package com.unis.personnel.server.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.unis.personnel.common.vo.UserQueryVO;
import com.unis.personnel.common.vo.UserVO;
import com.unis.personnel.server.domain.UserDomain;
import com.unis.personnel.server.service.UserService;

/**
 * @author 张琪
 * @date 2019年11月20日
 * @Description 
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDomain userDomain;
	/**
	 * 分页查询列表
	 */
	@Override
	public PageInfo<UserVO> getPageList(UserQueryVO userQueryVO, Integer pageNum, Integer pageSize) {
		return userDomain.getPageList(userQueryVO,pageNum,pageSize);
	}
	
	/**
	 * 登录验证
	 */
	@Override
	public UserVO checkName(UserVO userVO) {
		return userDomain.checkName(userVO);
	}

	/**
	 * 查询该条数据
	 */
	@Override
	public UserVO getPersonInfo(String userId) {
		return userDomain.getPersonInfo(userId);
	}


	/* 根据ID 删除数据
	 * @see com.unis.personnel.server.service.UserService#delete(com.unis.personnel.common.vo.UserVO)
	 */
	@Override
	public int delete(UserVO userVO) {
		return userDomain.delete(userVO);
	}

	/* 根据ID 查找 数据
	 * @see com.unis.personnel.server.service.UserService#getUserSql(java.lang.String)
	 */
	@Override
	public UserVO getUserSql(String userId) {
		return userDomain.getUserSql(userId);
	}

	/* 更新用户
	 * @see com.unis.personnel.server.service.UserService#update(com.unis.personnel.common.vo.UserVO)
	 */
	@Override
	public int update(UserVO userVO) {
		return userDomain.update(userVO);
	}

	/* 新增
	 * @see com.unis.personnel.server.service.UserService#insert(com.unis.personnel.common.vo.UserVO)
	 */
	@Override
	public int insert(UserVO userVO) {
		return userDomain.insert(userVO);
	}

	/* 批量删除
	 * @see com.unis.personnel.server.service.UserService#deleteAll(java.lang.String[])
	 */
	@Override
	public int deleteAll(String[] ids) {
		return userDomain.deleteAll(ids);
	}

	/* 根据用户名查询基本信息
	 * @see com.unis.personnel.server.service.UserService#getUserByName(java.lang.String)
	 */
	@Override
	public UserVO getUserByName(String userName) {
		return userDomain.getUserByName(userName);
	}



}
