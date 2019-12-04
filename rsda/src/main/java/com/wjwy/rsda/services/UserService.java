/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: zgr
 * @Date: 2019-12-03 14:49:36
 * @LastEditors: zgr
 * @LastEditTime: 2019-12-04 07:41:37
 */
package com.wjwy.rsda.services;

import java.util.List;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.User;
import com.wjwy.rsda.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author ZHANGQI
 * @date 2019年11月20日
 * @Description 
 */
@Service("userService")
public class UserService{
	@Autowired
	private UserMapper userDao;
	/**
	 * @param userQueryVO
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<User> getPageList(User user, Integer pageNum, Integer pageSize) {
	
		PageHelper.startPage(pageNum, pageSize);
		List<User> Users =  userDao.findPageList(user);
		PageInfo<User> PageInfoDO = new PageInfo<User>(Users);

		return PageInfoDO;
	}

	/**
	 * @Description: 根据ID 删除数据
	 * @param @param User
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月28日
	 */
	public int delete(User User) {
		return userDao.deleteByPrimaryKey(User.getUserId());
	}

	/**
	 * @Title: getPersonInfo
	 * @Description: TODO方法描述:(通过ID查找该条数据)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return User    返回类型
	 * @throws
	*/
	public User getPersonInfo(String userId) {
		return userDao.selectByPrimaryKey(userId);
	}


	/**
	 * @Description: 根据ID更新用户
	 * @param @param User
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月28日
	 */
	public int update(User User) {
		return userDao.updateByPrimaryKeySelective(User);
	}


	/**
	 * @Description: 新增
	 * @param @param User
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author ZHANGQ
	 * @date 2019年11月28日
	 */
	public int insert(User User) {
		return userDao.insertSelective(User); 
	}
}
