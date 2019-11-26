/**
 * 
 */
package com.unis.personnel.server.domain;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.unis.common.utils.LsBeanUtils;
import com.unis.personnel.common.uils.MybatisUtils;
import com.unis.personnel.common.vo.UserQueryVO;
import com.unis.personnel.common.vo.UserVO;
import com.unis.personnel.server.dao.UserMapper;
import com.unis.personnel.server.entity.UserDO;

/**
 * @author ZHANGQI
 * @date 2019年11月20日
 * @Description 
 */
@Component
public class UserDomain extends BaseDomain{
	@Autowired
	private UserMapper userDao;
	
    private static Logger logger = LogManager.getLogger(UserDomain.class);
	/**
	 * @param userQueryVO
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<UserVO> getPageList(UserQueryVO userQueryVO, Integer pageNum, Integer pageSize) {
		PageInfo<UserVO> pageInfoVO = new PageInfo<UserVO>();
		PageHelper.startPage(pageNum, pageSize);
		List<UserDO> userDOs =  userDao.findPageList(userQueryVO);
		PageInfo<UserDO> PageInfoDO = new PageInfo<UserDO>(userDOs);
		LsBeanUtils.copyAllProperties(PageInfoDO,pageInfoVO);
		 System.out.println(userDOs);
		
		return pageInfoVO;
	}
	
	
	/**
	 * @Title: checkName
	 * @Description: TODO方法描述:(登录验证)
	 * @param @param userQueryVO
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	*/
	public UserVO checkName(UserVO userVO) {
		
		//账号 密码 验证
		String sql = "select  *  from  user  where user_name =" + "'"+userVO.getUserName() +"'"+" and  pass_word = "+ "'"+userVO.getPassWord()+"'";
		userDao = MybatisUtils.getMapper(UserMapper.class);
		
		UserDO userDO = new UserDO();
		userDO= userDao.selectOne(sql);
		
		if (userDO == null) {
			return null;
		}
		//复制
		LsBeanUtils.copyAllProperties(userDO, userVO);
		logger.info(userVO.getPassWord());
       
		return userVO;
	}


	/**
	 * @Title: getPersonInfo
	 * @Description: TODO方法描述:(通过ID查找该条数据)
	 * @param @param userId
	 * @param @return    设定文件
	 * @return UserVO    返回类型
	 * @throws
	*/
	public UserVO getPersonInfo(String userId) {
		UserDO userDO = new UserDO();
		//通过ID回显数据
		String sql = "select  *  from  user  where user_id =" + "'"+userId +"'";
		userDao = MybatisUtils.getMapper(UserMapper.class);
		userDO= userDao.selectOne(sql);
		
		if (userDO == null) {
			return null;
		}
		UserVO userVO = new UserVO();
		//复制
		LsBeanUtils.copyAllProperties(userDO, userVO);
		return userVO;
	}
}
