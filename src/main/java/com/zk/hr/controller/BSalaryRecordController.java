package com.zk.hr.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zk.hr.common.DataGridView;
import com.zk.hr.common.PageModel;
import com.zk.hr.common.ResultObj;
import com.zk.hr.common.SalaryPageModel;
import com.zk.hr.entity.BSalaryRecord;
import com.zk.hr.mapper.BSalaryRecordMapper;
import com.zk.hr.service.BSalaryRecordService;
import com.zk.hr.vo.BSalaryRecordVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
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
@RequestMapping("/salaryRecord")
public class BSalaryRecordController {
    @Autowired
    private BSalaryRecordService bSalaryRecordService;

    @Autowired
    private BSalaryRecordMapper bSalaryRecordMapper;

    /**
     * 分页管理
     *
     * @param pageModel
     * @return
     */
    @RequestMapping("/loadAllSalaryRecord")
    public DataGridView loadAllSalaryRecord(SalaryPageModel pageModel) {
        Page<BSalaryRecord> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
        QueryWrapper<BSalaryRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(pageModel.getSalaryMonth()), "salary_month", pageModel.getSalaryMonth());
        Page<BSalaryRecord> resultPage = this.bSalaryRecordService.page(page, queryWrapper);

        List<BSalaryRecordVO> list = new ArrayList<>();
        for (BSalaryRecord record : resultPage.getRecords()) {
            BSalaryRecordVO vo = new BSalaryRecordVO();
            BeanUtils.copyProperties(record, vo);
            vo.setName(this.bSalaryRecordMapper.getUserNameById(record.getId()));
            vo.setDeptname(this.bSalaryRecordMapper.getDeptNameById(record.getId()));
            list.add(vo);
        }
        DataGridView dataGridView = new DataGridView(resultPage.getTotal(), list);
        return dataGridView;
    }

    @RequestMapping("/addSalaryRecord")
    public ResultObj addSalaryRecord(BSalaryRecord bSalaryRecord) {
        boolean save = this.bSalaryRecordService.save(bSalaryRecord);
        if (save) return ResultObj.ADD_SUCCESS;
        return ResultObj.ADD_ERROR;
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping("/deleteSalaryRecord")
    public ResultObj deleteSalaryRecord(Integer id){
        boolean remove = this.bSalaryRecordService.removeById(id);
        if (remove) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/batchDeleteSalaryRecord")
    public ResultObj batchDeleteSalaryRecord(String ids){
        String[] split = ids.split(",");
        List<String> list = Arrays.asList(split);
        boolean remove = this.bSalaryRecordService.removeByIds(list);
        if (remove) return ResultObj.DELETE_SUCCESS;
        return ResultObj.DELETE_ERROR;
    }
    
}

