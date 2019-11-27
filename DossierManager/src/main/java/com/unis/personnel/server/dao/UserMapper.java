package com.unis.personnel.server.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unis.personnel.common.vo.UserQueryVO;
import com.unis.personnel.common.vo.UserVO;
import com.unis.personnel.server.entity.UserDO;
/**
 * @author 张琪
 * @date 2019年11月20日
 * @Description 
 */

@Transactional(rollbackFor = Exception.class)
@org.apache.ibatis.annotations.Mapper
@Repository
public interface UserMapper {
	/**
	 * @param userQueryVO
	 * @return
	 */
	public List<UserDO> findPageList(UserQueryVO userQueryVO);
	
    int deleteByPrimaryKey(String userId);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKeyWithBLOBs(UserDO record);

    int updateByPrimaryKey(UserDO record);

	/**
	 * @Title: checkName
	 * @Description: TODO方法描述:(登录验证)
	 * @param @param userQueryVO
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	*/
	public boolean checkName(UserQueryVO userQueryVO);

	/**
	 * @Title: selectOne
	 * @Description: TODO方法描述:(查询数据库是否存在)
	 * @param @param sql
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	*/
	public UserDO selectOne(String sql);

	/**
	 * @Description: 列表
	 * @param @param paramMap
	 * @param @return   
	 * @return List<UserVO>  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月27日
	 */
	public List<UserVO> getUserPageListInfo(Map<String, Object> paramMap);

	/**
	 * @Description: 数量
	 * @param @param paramMap
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月27日
	 */
	public int getUserPageTotalCount(Map<String, Object> paramMap);
}