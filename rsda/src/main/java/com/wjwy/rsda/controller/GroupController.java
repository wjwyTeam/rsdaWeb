package com.wjwy.rsda.controller;

import java.util.List;

import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.Group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-10 08:24:47
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-10 08:33:36
 */
@RequestMapping("/group")
@RestController
@Api(value = "功能组", tags = "G2-菜单管理API维护")
public class GroupController {
    @Autowired
    private com.wjwy.rsda.services.GroupService groupService;
    public Logger logger = LoggerFactory.getLogger(GroupController.class);

    /**
     * 列表查询
     * 
     * @param groupId
     * @param groupName
     * @return ResponseWrapper
     */
    @ApiOperation(value = "功能组列表", notes = "参数:id-主键, name-功能名称")
    @GetMapping("/groupFindList")
    public ResponseWrapper groupFindList(String groupId, String groupName) {
        try {
            List<Group> roleList = groupService.findList(groupId, groupName);
            return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", roleList, null, roleList.size());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");

    }

    /**
     * 功能新增
     * 
     * @param group
     * @return ResponseWrapper
     */
    @ApiOperation(value = "功能新增", notes = "参数:Group-JSON对象")
    @PostMapping("/groupInsert")
    public ResponseWrapper groupInsert(@RequestBody Group group) {

        try {
            int resultTotal = groupService.insertGroup(group);
            if (resultTotal == 0) {
                return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "新增失败", null, null, null);
            }
            return ResponseWrapper.success(HttpStatus.OK.value(), "新增成功", null, null, null);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
    }

    /**
     * 功能更新
     * 
     * @param group
     * @return ResponseWrapper
     */
    @ApiOperation(value = "功能更新", notes = "参数:Group-JSON对象")
    @PostMapping("/updateGroup")
    public ResponseWrapper updateGroup(@RequestBody Group group) {
        try {
            int resultTotal = groupService.updateGroup(group);
            if (resultTotal == 0) {
                return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "更新失败", null, null, null);
            }
            return ResponseWrapper.success(HttpStatus.OK.value(), "更新成功", null, null, null);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
    }

    /**
     * 根据ID批量删除功能
     * 
     * @param ids
     * @return ResponseWrapper
     */
    @ResponseBody
    @ApiOperation(value = "根据ID批量删除功能", notes = "根据ID批量删除功能")
    @PostMapping("/deleteGroupAlls")
    public ResponseWrapper deleteGroupAlls(String[] ids) {
        ResponseWrapper rmAll = null;
        for (String id : ids) {
            if (!id.isEmpty()) {
                rmAll = deleteGroup(id);
            }
        }
        return rmAll;
    }

    /**
     * 根据用户ID删除功能
     * 
     * @param id
     * @return ResponseWrapper
     */
    @ApiOperation(value = "根据ID删除功能", notes = "参数:id-主键")
    @PostMapping("/deleteGroup")
    public ResponseWrapper deleteGroup(String id) {

        try {
            Group group = new Group();
            group.setGroupId(id);
            int resultTotal = groupService.delete(group);
            if (resultTotal == 0) {
                return ResponseWrapper.success(HttpStatus.OK.value(), "禁止删除[当前功能]", null, null, null);
            }
            return ResponseWrapper.success(HttpStatus.OK.value(), "删除成功", null, null, null);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
    }
}