package com.wjwy.rsda.service;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.User;

/**
 * @author 张琪
 * @date 2019年11月20日
 * @Description 
 */
public interface UserService  {

	/**
	 *  分页查询列表
	 * @param userQueryVO
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<User> getPageList(User User, Integer pageNum, Integer pageSize);


	/**
	 * @Title: getPersonInfo
	 * @Description: TODO方法描述:(这里用一句话描述这个方法的作用)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return User    返回类型
	 * @throws
	*/
	User getPersonInfo(String userId);



	/**
	 * @Description: 根据ID删除用户
	 * @param @param User
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月28日
	 */
	int delete(User User);

	/**
	 * @Description: 更新用户
	 * @param @param User
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月28日
	 */
	int update(User User);

	/**
	 * @Description: 新增
	 * @param @param User
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月28日
	 */
	int insert(User User);

}
