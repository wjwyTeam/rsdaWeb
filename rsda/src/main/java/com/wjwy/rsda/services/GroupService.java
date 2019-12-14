/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-10 08:26:54
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-11 19:03:53
 */
package com.wjwy.rsda.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.wjwy.rsda.entity.Group;
import com.wjwy.rsda.mapper.GroupMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-10 08:26:54
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-10 08:43:37
 */
@Service("groupService")
@Transactional
public  class GroupService{
    @Autowired
    private GroupMapper groupMapper;

    /**
     * 新增
     * @param group
     * @return int
     */
	public int insertGroup(Group group) {
        group.setGroupId(UUID.randomUUID().toString().toLowerCase());
        group.setCreateTime(new Date());
        return groupMapper.insert(group);
	}

    /**
     * 查询列表
     * @param groupId
     * @param groupName
     * @return List<Group>
     */
	public List<Group> findList(String groupId, String groupName) {
        Example example = new Example(Group.class);
        Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(groupId)) {
            criteria.andEqualTo("groupId", groupId);
        }
        if (!StringUtil.isEmpty(groupName)) {
            criteria.andEqualTo("functionName", groupName);
        }
        return groupMapper.selectByExample(example);
	}

    /**
     * 更新功能组
     * @param group
     * @return int
     */
	public int updateGroup(Group group) {
        Example example = new Example(Group.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("functionId", group.getGroupId());
        return groupMapper.updateByExampleSelective(group, example);
	}

	public int delete(Group group) {
		 /**
         * 不可以删除 IsCandel 不能删除的用户
         */

        Example example = new Example(Group.class);
        Criteria criteria = example.createCriteria();

        criteria.andEqualTo("groupId", group.getGroupId());

        List<Group> groupNew = groupMapper.selectByExample(example);

        criteria.andEqualTo("isCandel", false);
        group.setDelFlag(true);

        int count = groupMapper.updateByExampleSelective(group, example);
        if (groupNew.size() == 0) {
            count = HttpStatus.GONE.value();
        }
        return count;
	}
}