package com.zk.hr.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zk.hr.common.DataGridView;
import com.zk.hr.common.DeptPageModel;
import com.zk.hr.common.ResultObj;
import com.zk.hr.common.TreeNode;
import com.zk.hr.entity.SysDept;
import com.zk.hr.service.SysDeptService;
import com.zk.hr.service.SysRoleService;
import com.zk.hr.service.SysRoleUserService;
import com.zk.hr.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2024-03-31
 */
@RestController
@RequestMapping("/dept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @RequestMapping("/loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson() {
        List<SysDept> deptList = this.sysDeptService.list();
        List<TreeNode> treeNodeList = new ArrayList<>();
        for (SysDept sysDept : deptList) {
            TreeNode treeNode = new TreeNode(sysDept.getId(), sysDept.getPid(), sysDept.getTitle(), false);
            treeNodeList.add(treeNode);
        }
        return new DataGridView(treeNodeList);
    }

    @RequestMapping("/loadAllDept")
    public DataGridView lodeAllDept(DeptPageModel pageModel) {
        Page<SysDept> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(pageModel.getId() != null, "id", pageModel.getId());
        Page<SysDept> resultPage = this.sysDeptService.page(page, queryWrapper);
        return new DataGridView(resultPage.getTotal(), resultPage.getRecords());
    }

    @RequestMapping("/addDept")
    public ResultObj addDept(SysDept dept) {
        boolean save = this.sysDeptService.save(dept);
        if (save) return ResultObj.ADD_SUCCESS;
        return ResultObj.ADD_ERROR;
    }

    @RequestMapping("/updateDept")
    public ResultObj updateDept(SysDept dept) {
        boolean updateById = this.sysDeptService.updateById(dept);
        if (updateById) return ResultObj.UPDATE_SUCCESS;
        return ResultObj.UPDATE_ERROR;
    }

    @RequestMapping("/deleteDept")
    public ResultObj deleteDept(Integer id) {
        boolean removeById = this.sysDeptService.removeById(id);
        if (removeById) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }


    @RequestMapping("/checkDeptHasChildrenNode")
    public Map<String, Boolean> checkDeptHasChildrenNode(Integer id){
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",id);
        List<SysDept> list = this.sysDeptService.list(queryWrapper);
        Map<String,Boolean> map = new HashMap<>();
        if(!list.isEmpty()){
            map.put("value",true);
        }else {
            map.put("value",false);
        }
        return map;
    } 

}

