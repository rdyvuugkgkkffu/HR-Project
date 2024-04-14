package com.zk.hr.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zk.hr.common.DataGridView;
import com.zk.hr.common.PageModel;
import com.zk.hr.common.ResultObj;
import com.zk.hr.entity.BMerit;
import com.zk.hr.service.BMeritService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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
@RequestMapping("/merit")
public class BMeritController {
    @Autowired
    private BMeritService meritService;

    @RequestMapping("/loadAllMerit")
    public DataGridView loadAllMerit(PageModel pageModel) {//分页操作
        Page<BMerit> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
       Page<BMerit> resultPage = this.meritService.page(page);
       DataGridView dataGridView = new DataGridView(resultPage.getTotal(),resultPage.getRecords());
       return  dataGridView;
    }

    /**
     * 新增
     * @param merit
     * @return
     */
    @RequestMapping("/addMerit")
    public ResultObj addMerit(BMerit merit){
        boolean save = this.meritService.save(merit);
        if (save) return ResultObj.ADD_SUCCESS;
        return ResultObj.ADD_ERROR;
    }

    @RequestMapping("/updateMerit")
    public ResultObj updateMerit(BMerit bMerit){
        boolean update = this.meritService.updateById(bMerit);
        if (update) return ResultObj.UPDATE_SUCCESS;
        return ResultObj.UPDATE_ERROR;
    }

    /**
     * 根据id单个删除
     * @param id
     * @return
     */
    @RequestMapping("/deleteMerit")
    public ResultObj deleteMerit(Integer id){
        boolean remove = this.meritService.removeById(id);
        if (remove) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/batchDeleteMerit")
    public ResultObj batchDeleteMerit(String ids){
        String[] split = ids.split(",");//将前端传来的字符串分开传入数组
        List<String> list = Arrays.asList(split);//将数组转存为集合
        boolean remove = this.meritService.removeByIds(list);
        if (remove) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }

}

