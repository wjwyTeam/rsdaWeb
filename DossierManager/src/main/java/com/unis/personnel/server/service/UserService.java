/**
 * 
 */
package com.unis.personnel.server.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.unis.personnel.common.vo.UserQueryVO;
import com.unis.personnel.common.vo.UserVO;

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
	PageInfo<UserVO> getPageList(UserQueryVO userQueryVO, Integer pageNum, Integer pageSize);

	/**
	 * @Title: checkName
	 * @Description: TODO方法描述:(这里用一句话描述这个方法的作用)
	 * @param @param userQueryVO
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	*/
	UserVO checkName(UserVO userVO);

	/**
	 * @Title: getPersonInfo
	 * @Description: TODO方法描述:(这里用一句话描述这个方法的作用)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return UserVO    返回类型
	 * @throws
	*/
	UserVO getPersonInfo(String userId);



	/**
	 * @Description: 根据ID删除用户
	 * @param @param userVO
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月28日
	 */
	int delete(UserVO userVO);

	/**
	 * @Description: 更具ID查数据
	 * @param @param userId   
	 * @return void  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月28日
	 */
	UserVO getUserSql(String userId);

	/**
	 * @Description: 更新用户
	 * @param @param userVO
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月28日
	 */
	int update(UserVO userVO);

	/**
	 * @Description: 新增
	 * @param @param userVO
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月28日
	 */
	int insert(UserVO userVO);

	/**
	 * @Description: 批量删除
	 * @param @param ids
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月29日
	 */
	int deleteAll(String[] ids);

	/**
	 * @Description: 根据用户名查询个人信息
	 * @param @param userName
	 * @param @return   
	 * @return UserVO  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月29日
	 */
	UserVO getUserByName(String userName);

}
