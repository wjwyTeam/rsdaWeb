package com.wjwy.rsda.controller;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import com.wjwy.rsda.entity.Role;
import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Date: 2019-12-06 08:26:11
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-06 08:33:26
 */

@RequestMapping("/role")
@RestController
@Api(value = "用户所属角色", tags = "用户所属角色维护")
public class RoleController {
    /**
     * 所属角色业务层注入
     */
    @Autowired
    private RoleService roleService;

    public Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    /**
     * 列表查询
     * 
     * @param id
     * @param name
     */
    @ApiOperation(value = "角色列表", notes = "参数:id-主键, name-角色名称")
    @GetMapping("/roleFindList")
    public void roleFindList(String id, String name) {
        JSONObject json = new JSONObject();
        try {
            List<Role> roleList = roleService.findList(id, name);
            json.put("count", roleList.size());
            json.put("data", roleList);
            json.put("msg", "success");
            if (!roleList.isEmpty()) {
                json.put("code", 200);
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
            json.put(String.valueOf(EnumEntitys.RETURN_MSG.getValue()), "error");
            json.put(String.valueOf(EnumEntitys.RETURN_CODE.getValue()), "400");
        }

    }

    /**
     * 角色新增
     * 
     * @param role
     * @return
     */
    @ApiOperation(value = "角色新增", notes = "参数:Role-JSON对象")
    @PostMapping("/roleInsert")
    public JSONObject insertRole(@RequestBody Role role) {
        JSONObject resultJSONObject = new JSONObject();
        try {
            int resultTotal = 0;// 操作的记录数
            resultTotal = roleService.insertRole(role);
            boolean flag = false;
            if (resultTotal > 0) {
                flag = true;
            }
            resultJSONObject.put("success", flag);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return resultJSONObject;
    }


     /**
     * 角色更新
     * 
     * @param role
     * @return
     */
    @ApiOperation(value = "角色更新", notes = "参数:Role-JSON对象")
    @PostMapping("/updateRole")
    public JSONObject updateRole(@RequestBody Role role) {
        JSONObject resultJSONObject = new JSONObject();
        try {
            int resultTotal = 0;// 操作的记录数
            resultTotal = roleService.updateRole(role);
            boolean flag = false;
            if (resultTotal > 0) {
                flag = true;
            }
            resultJSONObject.put("success", flag);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return resultJSONObject;
    }


    @ResponseBody
	@ApiOperation(value = "根据用户ID批量删除角色", notes = "根据用户ID批量删除角色")
	@PostMapping("/deleteRoleAlls")
	public JSONObject deleteRoleAlls(String[] ids) {

		JSONObject resultJSONObject = new JSONObject();
		try {
			int resultTotal = 0;
			Role role = new Role();
			for (String id : ids) {
				if (!id.isEmpty()) {
					role.setId(id);
					resultTotal = roleService.delete(role);
				}
			}
			boolean flag = false;
			if (resultTotal > 0) {
				flag = true;
			}
			resultJSONObject.put("success", flag);

		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

		return resultJSONObject;
	}

	/**
	 * @Description: 根据用户ID删除用户
	 * @param
	 * @return
	 * @return void
	 * @throws JSONException
	 * @throws @author       ZHANGQI
	 * @date 2019年11月28日
	 */
	@ApiOperation(value = "根据用户ID删除角色", notes = "参数:id-主键")
	@PostMapping("/deleteRole")
	public JSONObject deleteRole(String id) {
		JSONObject resultJSONObject = new JSONObject();
		try {
			Role role = new Role();
			role.setId(id);
			int resultTotal = roleService.delete(role);

			boolean flag = false;
			if (resultTotal > 0) {
				flag = true;
			}
			resultJSONObject.put("success", flag);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return resultJSONObject;
    }



    
}
