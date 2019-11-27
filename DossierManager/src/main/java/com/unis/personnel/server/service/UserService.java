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
	 * @Description: 获取列表分页
	 * @param @param paramMap
	 * @param @return   
	 * @return List<UserVO>  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月27日
	 */
	List<UserVO> getUserPageListInfo(Map<String, Object> paramMap);

	/**
	 * @Description: 获取数据条数
	 * @param @param paramMap
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月27日
	 */
	int getUserPageTotalCount(Map<String, Object> paramMap);

}
