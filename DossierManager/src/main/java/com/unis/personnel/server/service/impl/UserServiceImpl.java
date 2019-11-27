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

	/* (non-Javadoc)
	 * 分页列表
	 * @see com.unis.personnel.server.service.UserService#getUserPageListInfo(java.util.Map)
	 */
	@Override
	public List<UserVO> getUserPageListInfo(Map<String, Object> paramMap) {
		return userDomain.getUserPageListInfo(paramMap);
	}

	/* (non-Javadoc)
	 * 分页条数
	 * @see com.unis.personnel.server.service.UserService#getUserPageTotalCount(java.util.Map)
	 */
	@Override
	public int getUserPageTotalCount(Map<String, Object> paramMap) {
		return userDomain.getUserPageTotalCount(paramMap);
	}

}
