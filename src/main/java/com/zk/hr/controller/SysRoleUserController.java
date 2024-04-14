package com.zk.hr.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zk.hr.common.DataGridView;
import com.zk.hr.common.PageModel;
import com.zk.hr.common.ResultObj;
import com.zk.hr.common.TreeNode;
import com.zk.hr.entity.SysPermission;
import com.zk.hr.entity.SysRole;
import com.zk.hr.entity.SysRolePermission;
import com.zk.hr.service.SysPermissionService;
import com.zk.hr.service.SysRolePermissionService;
import com.zk.hr.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2024-03-31
 */
@RestController
@RequestMapping("/role")
public class SysRoleUserController {
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private SysRolePermissionService rolePermissionService;
  

    @RequestMapping("/loadAllRole")
    public DataGridView loadAllRole(PageModel pageModel) {
        Page<SysRole> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
        Page<SysRole> resultPage = this.roleService.page(page);
        return new DataGridView(resultPage.getTotal(), resultPage.getRecords());
    }

    @RequestMapping("/addRole")
    public ResultObj addRole(SysRole role) {
        boolean save = this.roleService.save(role);
        if (save) return ResultObj.ADD_SUCCESS;
        return ResultObj.ADD_ERROR;
    }

    @RequestMapping("/updateRole")
    public ResultObj updateRole(SysRole role) {
        boolean updateById = this.roleService.updateById(role);
        if (updateById) return ResultObj.UPDATE_SUCCESS;
        return ResultObj.UPDATE_ERROR;
    }

    @RequestMapping("/deleteRole")
    public ResultObj deleteRole(Integer id) {
        boolean removeById = this.roleService.removeById(id);
        if (removeById) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }

    @RequestMapping("/initPermissionByRoleId")
    public DataGridView initPermissionByRoleId(Integer id) {
        //查询所有菜单
        List<SysPermission> permissionlist = this.permissionService.list();
        List<TreeNode> treeNodeList = new ArrayList<>();
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rid", id);
        List<SysRolePermission> rolePermissionlist = this.rolePermissionService.list(queryWrapper);

        for (SysPermission permission : permissionlist) {
            TreeNode treeNode = new TreeNode(permission.getId(), permission.getPid(), permission.getTitle(), true);
            //判断当前角色是否拥有当前权限
            for (SysRolePermission sysRolePermission : rolePermissionlist) {
            if(permission.getId().equals(sysRolePermission.getPid())) treeNode.setCheckArr("1"); 
            }
            treeNodeList.add(treeNode);

        }
        return new DataGridView(treeNodeList);
    }
    @RequestMapping("/updatePermission")
    public ResultObj updatePermission(Integer[] ids){
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rid", ids[0]);
        this.rolePermissionService.remove(queryWrapper);
        List<SysRolePermission> list = new ArrayList<>();
        for (int i = 1; i < ids.length; i++) {
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setRid(ids[0]);
            rolePermission.setPid(ids[i]);
            list.add(rolePermission);
        }
        boolean saveBatch = this.rolePermissionService.saveBatch(list);
        if(saveBatch) return ResultObj.DISPATCH_SUCCESS;
        return ResultObj.DISPATCH_ERROR;
    }

}

