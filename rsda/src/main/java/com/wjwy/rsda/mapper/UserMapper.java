/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: zgr
 * @Date: 2019-11-30 23:18:29
 * @LastEditors: zgr
 * @LastEditTime: 2019-12-03 18:42:44
 */
package com.wjwy.rsda.mapper;

import java.util.List;
import com.wjwy.rsda.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from sys_user where status = 0")
    List<User> getAllUsers();

    @Select("select * from sys_user where status = 0  and username=#{userName}")
    User getUserByUserName(String userName);

    @Select("select * from sys_user where status = 0  and  username=#{userName} and password=#{passWord} ")
    User getUserByLogin(String userName, String passWord);
    
    @Select("select * from sys_user where status = 0  and userid=#{userId}")
    User selectByPrimaryKey(String userId);

    @Select("select * from sys_user where status = 0")
    List<User> findPageList(User user);

    @Update("update sys_user set status = 1  where userid = #{userId}")
    int deleteByPrimaryKey(String userId);

    @Update("#{sql}")
    int updateByPrimaryKeySelective(User user);

    @Insert("")
    int insertSelective(User user);

    /**
     * @Description: 主键策略生成
     * @param @param  date
     * @param @return
     * @return String
     * @throws @author ZHANGQI
     * @date 2019年11月28日
     */
    @Select("SELECT  MAX(userid) FROM  sys_user where userid  LIKE CONCAT(#{pstr,jdbcType=VARCHAR}, '%')")
    String getMaxCodeById(@Param("pstr") String pstr);
}