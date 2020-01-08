/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-03 16:08:57
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-08 09:48:16
 */
package com.wjwy.rsda.services;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.Department;
import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.mapper.DepartmentMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

@Service("departmentService")
@Transactional
public class DepartmentService {

    /**
     * 
     * 结构树展示
     */
    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 单位数据查询
     * 
     * @param parentId
     * @return List<TreeNode>
     */
    public List<Department> list(String parentId) {
        if(StringUtil.isEmpty(parentId)|| parentId.equalsIgnoreCase("{}")){
            parentId=String.valueOf(EnumEntitys.GJD.getValue());
        }
        List<Department> departmentlists = departmentMapper.selectTreeList(parentId);
        return departmentlists;
    }

    /***
     * 新增
     * 
     * @param department
     * @return int
     */
    public int insertUnitTree(Department department) {
        department.setId(UUID.randomUUID().toString().toLowerCase());
        if (StringUtil.isEmpty(department.getParentId())) {
            department.setParentId(String.valueOf(EnumEntitys.GJD.getValue()));
        } 
        department.setCreateTime(new Date());
        
        department.setDorder(Integer.parseInt(this.getMaxCodeById(departmentMapper.getMaxCodeById(""),"",departmentMapper.selectMax())));

        return departmentMapper.insertSelective(department);
    }

    /**
     * 更新
     * 
     * @param department
     * @return int
     */
    public int updateUnitTree(Department department) {
        Example example = new Example(Department.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", department.getId());
        return departmentMapper.updateByExampleSelective(department, example);
    }

    /**
     * 删除
     * 
     * @param department
     * @return int
     */
    public int deleteUnitTree(Department department) {
        Example example = new Example(Department.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", department.getId());

        List<Department> departmentNew = departmentMapper.selectByExample(example);
        criteria.andEqualTo("isCandel", false);

        int count = departmentMapper.deleteByExample(example);
      
        if (departmentNew.size() == 0) {
            count = HttpStatus.GONE.value();
        }
        return count;
    }

    /**
     * 
     * @param department
     * @return
     */
	public List<Department> findList(Department department) {
        List<Department> dList = new ArrayList<Department>();
        Example example = new Example(Department.class);
        example.setOrderByClause("create_time DESC");
        Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(department.getId())) {
            criteria.andEqualTo("id", department.getId());
        }
            
        if (!StringUtil.isEmpty(department.getParentId())) {
            criteria.andEqualTo("parentId", department.getParentId());
        }
        
        if (!StringUtil.isEmpty(department.getName())) {
            criteria.andEqualTo("name", department.getName());
        }
            dList = departmentMapper.selectByExample(example);
        return dList;
    }

    /**
     * 通过ID查找数据对象
     * @param id
     * @return
     */
	public Department getDept(String id) {
        Example example = new Example(Department.class);
        Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(id)) {
            criteria.andEqualTo("id", id);
        }
		return departmentMapper.selectOneByExample(example);
	}

    /**
     * 通过ID查找子数据集合
     * @param id
     * @return
     */
	public List<Department> getChildren(String id) {
		Example example = new Example(Department.class);
        Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(id)) {
            criteria.andEqualTo("parentId", id);
        }
        criteria.andEqualTo("delFlag", false);
		return departmentMapper.selectByExample(example); 
    } 
    

    /**
     * 单位分页列表查询
     * @param id
     * @param unitType
     * @param name
     * @param limit
     * @param page
     * @return List<Department>
     */
	public PageInfo<Department> findTreeList(String id, String name, Integer unitType, Integer page, Integer limit) {
        PageHelper.startPage(page , limit);
        
        if(StringUtil.isEmpty(id)){
            id = "";
        }
           
        if(StringUtil.isEmpty(name)){
            name = "";
       }

        List<Department> department = departmentMapper.selectTreeOne(id,name,unitType);
    
		if (department == null) {
			return null;
		}
		PageInfo<Department> PageInfoDO = new PageInfo<Department>(department);
		return PageInfoDO;
    }


    /**
	 *   根据id获取最大信息编号
	 * @param maxId  当前最大编号
	 * @param preStr 数据前缀
	 * @param length 拼接长度
	 * @return
	 */
 
	public String getMaxCodeById(String maxId,String preStr,int length) {
		String serialNumberStr = "";
		if (StringUtils.isEmpty(maxId)) {
			maxId = "0000000000000";
		}
		int serialNumber = 0;
		int maxSerialNumber = Integer.valueOf(maxId.substring(
				maxId.length() - length, maxId.length()));
		serialNumber = maxSerialNumber + 1;
		
		NumberFormat nf = NumberFormat.getInstance();
        //设置是否使用分组
        nf.setGroupingUsed(false);
        //设置最大整数位数
        nf.setMaximumIntegerDigits(length);
        //设置最小整数位数   
        nf.setMinimumIntegerDigits(length);
		serialNumberStr = nf.format(serialNumber);
		StringBuilder emailIdBuilder = new StringBuilder(preStr).append(serialNumberStr);
		return emailIdBuilder.toString();
    }
    /**
     * 上移数据
     * @param ids
     * @param id
     * @return
     */
	public int moveUpOrDown( String[] ids, String id,boolean option) {

        if(ids == null || StringUtil.isEmpty(id)){
             return 0;
        }

  
        List<Department> departmentM = new ArrayList<Department>();
        Department departUp =  departmentMapper.selectByPrimaryKey(id);
       
        for (String idUps : ids) {
            Department departall =  departmentMapper.selectByPrimaryKey(idUps);
            if (idUps.equals(departUp.getParentId()) || departall.getParentId().equals(id)) {
                return 2;
            }
            Example example = new Example(Department.class);
            Criteria criteria = example.createCriteria();
            criteria.andEqualTo("id", idUps);
            Department departDown = departmentMapper.selectOneByExample(example);
            departUp.setDorder(departDown.getDorder());
            if(option){
                departDown.setDorder(departUp.getDorder()-1);
            }else{
                departDown.setDorder(departUp.getDorder()+1);
            }
            
            departmentM.add(departDown);
            departmentM.add(departUp);
        }

        for (Department departmentsUpdate : departmentM) {
            departmentMapper.updateByPrimaryKeySelective(departmentsUpdate);
        }
		return 1;
	}
}