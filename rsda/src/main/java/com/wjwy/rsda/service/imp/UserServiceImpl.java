package com.wjwy.rsda.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.domain.UserDomain;
import com.wjwy.rsda.entity.User;
import com.wjwy.rsda.service.UserService;

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
	public PageInfo<User> getPageList(User user, Integer pageNum, Integer pageSize) {
		return userDomain.getPageList(user,pageNum,pageSize);
	}
	
	/**
	 * 查询该条数据
	 */
	@Override
	public User getPersonInfo(String userId) {
		return userDomain.getPersonInfo(userId);
	}


	/* 根据ID 删除数据
	 * @see com.unis.personnel.server.service.UserService#delete(com.unis.personnel.common.vo.User)
	 */
	@Override
	public int delete(User User) {
		return userDomain.delete(User);
	}

	/* 更新用户
	 * @see com.unis.personnel.server.service.UserService#update(com.unis.personnel.common.vo.User)
	 */
	@Override
	public int update(User User) {
		return userDomain.update(User);
	}

	/* 新增
	 * @see com.unis.personnel.server.service.UserService#insert(com.unis.personnel.common.vo.User)
	 */
	@Override
	public int insert(User User) {
		return userDomain.insert(User);
	}

}
