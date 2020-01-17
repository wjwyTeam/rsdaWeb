package com.wjwy.rsda.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.enums.EnumEntitys;
import com.wjwy.rsda.entity.DaCodeItem;
import com.wjwy.rsda.entity.DaElectronic;
import com.wjwy.rsda.entity.DaJson;
import com.wjwy.rsda.entity.DaMaterialtem;
import com.wjwy.rsda.entity.Electronic;
import com.wjwy.rsda.mapper.DaCodeItemMapper;
import com.wjwy.rsda.mapper.DaElectronicMapper;
// import com.wjwy.rsda.mapper.PersonalMapper;
import com.wjwy.rsda.mapper.DaMaterialtemMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-14 10:05:37
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-14 10:07:28
 */
@Service("daCodeItemService")
@Transactional
public class DaCodeItemService {
  @Autowired
  private DaCodeItemMapper daCodeItemMapper;
  @Autowired
  private DaMaterialtemMapper daMaterialtemMapper;
  @Autowired
  private DaElectronicMapper daElectronicMapper;

  /**
   * 通过ID获取对象
   * 
   * @param DaCodeItemId
   * @return
   */
  public DaCodeItem getById(String id) {
    Example example = new Example(DaCodeItem.class);
    Criteria criteria = example.createCriteria();
    if (!StringUtil.isEmpty(id)) {
      criteria.andEqualTo("id", id);
    }
    return daCodeItemMapper.selectOneByExample(example);
  }

  /**
   * 获取第一级对象数据
   * 
   * @param DaCodeItemId
   * @return
   */
  public List<DaJson> getByGetLsit(String personId) {

    List<DaJson> lists = new ArrayList<DaJson>();

    Example example = new Example(DaCodeItem.class);
    example.setOrderByClause("sort ASC");
    Criteria criteria = example.createCriteria();
    criteria.andEqualTo("pid", EnumEntitys.GJD.getValue());
    List<DaCodeItem> daList = daCodeItemMapper.selectByExample(example);
    if (daList == null) {
      return null;
    }
    for (DaCodeItem daCodeItem : daList) {
      // 查找是否存在下级是否需要展示 1.本表级别关系 0.联表关系 标记字段 ：cid
      if (EnumEntitys.BB.getValue() == daCodeItem.getCid()) {
        // 查找本表是否存在下级关系
        List<DaCodeItem> checkCid = daCodeItemMapper.selectCid((String) EnumEntitys.GJD.getValue());
        if (checkCid == null) {
          return null;
        }

        DaJson daJsonF = new DaJson();
        // 判断是否存在子级
        for (DaCodeItem checkCidList : checkCid) {
          if (checkCidList.getId().equals(daCodeItem.getId()) && checkCidList.getCid()) {
            List<DaCodeItem> checkCids = daCodeItemMapper.selectT(checkCidList.getId());
            for (DaCodeItem daCodeItem2 : checkCids) {
              DaJson daJsonO = new DaJson();
              // 如果存在继续判断联表是否存在下级 0
              List<DaMaterialtem> ls = daMaterialtemMapper.selectByMater(personId, daCodeItem2.getId());
              if (ls.size() > 0) {
                for (DaMaterialtem lss : ls) {
                  // 底层拼接
                  DaJson daJsonT = new DaJson();
                  daJsonT.setId(lss.getId());
                  daJsonT.setTitle(lss.getName());
                  daJsonT.setDisabled(false);
                  daJsonT.setPid(lss.getItemCode());
                  daJsonT.setPageNum(lss.getPageNumber());
                  List<DaElectronic> da = daElectronicMapper.selectMater(personId, lss.getItemCode(), lss.getId());
                  if (da != null) {
                    List<Electronic> cs = new ArrayList<Electronic>();
                    for (DaElectronic daElectronic : da) {
                      Electronic c = new Electronic();
                      c.setId(daElectronic.getId());
                      c.setFileName(daElectronic.getFileName());
                      cs.add(c);
                    }
                    daJsonT.setPhotos(cs);
                  }
                  lists.add(daJsonT);

                }
                // 本表底层拼接
                daJsonO.setId(daCodeItem2.getId());
                daJsonO.setTitle(daCodeItem2.getName());
                daJsonO.setDisabled(false);
                daJsonO.setPid(daCodeItem2.getPid());
                daJsonO.setPageNum(ls.size());
                lists.add(daJsonO);
                // 本表一层拼接
                daJsonF.setId(checkCidList.getId());
                daJsonF.setTitle(checkCidList.getName());
                daJsonF.setDisabled(false);
                daJsonF.setPageNum(checkCid.size());
                daJsonF.setPid((String) EnumEntitys.GJD.getValue());
                lists.add(daJsonF);

              }
            }
          }
        }
      } else {
        // 判断是否存在子级
        DaJson daJsonO = new DaJson();
        List<DaMaterialtem> ls = daMaterialtemMapper.selectByMater(personId, daCodeItem.getId());
        if (ls.size() > 0) {
          for (DaMaterialtem lss : ls) {
            DaJson daJsonT = new DaJson();
            // 本表底层拼接
            daJsonT.setId(lss.getId());
            daJsonT.setTitle(lss.getName());
            daJsonT.setDisabled(false);
            daJsonT.setPageNum(lss.getPageNumber());
            daJsonT.setPid(lss.getItemCode());
            List<DaElectronic> da = daElectronicMapper.selectMater(personId, lss.getItemCode(), lss.getId());
            if (da != null) {
              List<Electronic> cs = new ArrayList<Electronic>();
              for (DaElectronic daElectronic : da) {
                Electronic c = new Electronic();
                c.setId(daElectronic.getId());
                c.setFileName(daElectronic.getFileName());
                cs.add(c);
              }
              daJsonT.setPhotos(cs);
            }
            lists.add(daJsonT);
            // 本表一层拼接
            daJsonO.setId(daCodeItem.getId());
            daJsonO.setTitle(daCodeItem.getName());
            daJsonO.setDisabled(false);
            daJsonO.setPageNum(ls.size());
            daJsonO.setPid((String) EnumEntitys.GJD.getValue());
            lists.add(daJsonO);
          }
        }
      }

    }
    return lists;
  }

  /**
   * 目录管理列表数据查询
   * 
   * @param DaCodeItem
   * @param page
   * @param limit
   * @return
   */
  public PageInfo<DaCodeItem> findList(DaCodeItem daCodeItem, Integer page, Integer limit) {
    PageHelper.startPage(page, limit);
    Example example = new Example(DaCodeItem.class);
    example.setOrderByClause("create_time DESC");
    Criteria criteria = example.createCriteria();
    if (!StringUtil.isEmpty(daCodeItem.getId())) {
      criteria.andEqualTo("id", daCodeItem.getId());
    }

    // Personal pers = new Personal();
    // List<DaCodeItem> ps = new ArrayList<DaCodeItem>();
    // if (!StringUtil.isEmpty(daCodeItem.getPersonId())) {
    // criteria.andEqualTo("personId", daCodeItem.getPersonId());
    // // 查询person personalId
    // Example exampleP = new Example(Personal.class);
    // exampleP.setOrderByClause("create_time DESC");
    // Criteria criteriaP = exampleP.createCriteria();
    // criteriaP.andEqualTo("personalId", daCodeItem.getPersonId());
    // pers = personalMapper.selectOneByExample(exampleP);

    // }

    List<DaCodeItem> daCodeItems = daCodeItemMapper.selectByExample(example);

    // for (DaCodeItem daCodes : daCodeItems) {
    // DaCodeItem d = new DaCodeItem();
    // d = daCodes;
    // d.setPerson(pers);
    // ps.add(d);
    // }

    PageInfo<DaCodeItem> PageInfoDO = new PageInfo<DaCodeItem>(daCodeItems);
    return PageInfoDO;
  }

  /**
   * 目录管理表单数据新增
   * 
   * @param DaCodeItem
   * @return int
   */
  public int daCodeItemInsert(DaCodeItem daCodeItem) {
    daCodeItem.setId(UUID.randomUUID().toString().toLowerCase());
    return daCodeItemMapper.insertSelective(daCodeItem);
  }

  /**
   * 目录管理表单数据更新
   * 
   * @param DaCodeItem
   * @return int
   */
  public int daCodeItemUpdate(DaCodeItem daCodeItem) {
    Example example = new Example(DaCodeItem.class);
    Criteria criteria = example.createCriteria();
    criteria.andEqualTo("id", daCodeItem.getId());
    return daCodeItemMapper.updateByExampleSelective(daCodeItem, example);
  }

  /**
   * 目录管理移除数据
   * 
   * @param ids
   * @return int
   */
  public int daCodeItemRemove(String[] ids) {
    int res = 0;
    for (String id : ids) {
      Example example = new Example(DaCodeItem.class);
      Criteria criteria = example.createCriteria();
      criteria.andEqualTo("id", id);
      res = daCodeItemMapper.deleteByExample(example);
    }
    return res;
  }

  /***
   * 
   * @param personId
   * @return
   */
  public List<DaJson> treeJsonsFunctions(String personId) {
    List<DaJson> treeList = getByGetLsit(personId);
    List<DaJson> rootList = new ArrayList<DaJson>();
    for (DaJson function : treeList) {
      if (function.getPid().equals(EnumEntitys.GJD.getValue())) {
        rootList.add(function);
      }
    }

    for (DaJson nav : rootList) {
      List<DaJson> child = getChild(nav.getId(), treeList);
      nav.setChildren(child);
    }
    return rootList;

  }

  /**
   * 
   * @param id
   * @param functions
   */
  public List<DaJson> getChild(String id, List<DaJson> functions) {
    List<DaJson> chilList = new ArrayList<DaJson>();

    for (DaJson function : functions) {
      if (function.getPid().equals(id)) {
        chilList.add(function);
      }
    }

    for (DaJson functionC : chilList) {
      functionC.setChildren(getChild(functionC.getId(), functions));
    }
    if (chilList.size() == 0) {
      return null;
    }
    return chilList;
  }

}