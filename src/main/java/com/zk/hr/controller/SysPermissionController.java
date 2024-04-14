package com.zk.hr.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zk.hr.common.DataGridView;
import com.zk.hr.common.TreeNode;
import com.zk.hr.entity.SysPermission;
import com.zk.hr.service.SysPermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.swing.event.TreeModelListener;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2024-03-31
 */
@RestController
@RequestMapping("/permission")
public class SysPermissionController {
    
    @Resource
    private SysPermissionService sysPermissionService;
    
    @RequestMapping("/loadIndexLeftMenuJson")
    public DataGridView LoadIndexLeftMenuJson(){
        QueryWrapper<SysPermission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type","menu");
        List<SysPermission> list =this.sysPermissionService.list(queryWrapper);
        //将list转为treeNode结构
        List<TreeNode> treeNodelist =new ArrayList<>();
        for (SysPermission sysPermission : list) {
            if(sysPermission.getPid().equals(0)){//一级菜单
                TreeNode parent = new TreeNode();//父节点
                List<TreeNode> children=new ArrayList<>();//子节点
                BeanUtils.copyProperties(sysPermission,parent);
                treeNodelist.add(parent);//把treenode对象装到treenodelist里面
                for(SysPermission permission:list){
                    if(parent.getId().equals(permission.getPid())){
                        TreeNode child = new TreeNode();
                        BeanUtils.copyProperties(permission,child);
                        children.add(child);
                        
                    }
                }
                parent.setChildren(children);
            }
        }
        return new  DataGridView(treeNodelist);
    }

}

