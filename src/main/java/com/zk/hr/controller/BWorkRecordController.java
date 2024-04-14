package com.zk.hr.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zk.hr.common.DataGridView;
import com.zk.hr.common.PageModel;
import com.zk.hr.common.ResultObj;
import com.zk.hr.common.WorkStatusEnum;
import com.zk.hr.entity.BWorkRecord;
import com.zk.hr.entity.SysUser;
import com.zk.hr.mapper.BWorkRecordMapper;
import com.zk.hr.service.BWorkRecordService;
import com.zk.hr.util.WebUtils;
import com.zk.hr.vo.BWorkRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/workRecord")
public class BWorkRecordController {
    @Autowired
    private BWorkRecordService bWorkRecordService;

    @Autowired
    private BWorkRecordMapper bWorkRecordMapper;

    @Value("${work-up-time}")
    private Integer upTime;

    @Value("${work-down-time}")
    private Integer downTime;

    @Value("${work-over-time}")
    private Integer overTime;

    @RequestMapping("/loadAllWorkRecord")
    public DataGridView loadAllWorkRecord(PageModel pageModel) {
        Page<BWorkRecord> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
        Page<BWorkRecord> resultPage = this.bWorkRecordService.page(page);
        List<BWorkRecordVO> list = new ArrayList<>();
        for (BWorkRecord record : resultPage.getRecords()) {
            BWorkRecordVO vo = new BWorkRecordVO();
            BeanUtils.copyProperties(record, vo);
            vo.setName(this.bWorkRecordMapper.getUserNameById(record.getId()));
            list.add(vo);
        }
        DataGridView dataGridView = new DataGridView(resultPage.getTotal(), list);
        return dataGridView;
    }

    @RequestMapping("/addWorkRecord")
    public ResultObj addWorkRecord(BWorkRecord bWorkRecord) {
        SysUser user = (SysUser) WebUtils.getSession().getAttribute("user");
        bWorkRecord.setUid(user.getId());
        //判断上下班
        QueryWrapper<BWorkRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", bWorkRecord.getUid())
                .eq("work_date", bWorkRecord.getWorkDate());
        BWorkRecord one = this.bWorkRecordService.getOne(queryWrapper);
        if (one == null) {
            //0 上班
            //判断是否迟到
            Integer workRecordUpTime = Integer.valueOf(bWorkRecord.getUpTime().substring(0, 2));
            if (workRecordUpTime > upTime || (workRecordUpTime == upTime && (Integer.valueOf(bWorkRecord.getUpTime().substring(3, 5))) > 0)) {
                bWorkRecord.setStatus(WorkStatusEnum.LATE.getCode());
            } else {
                bWorkRecord.setStatus(WorkStatusEnum.NORMAL.getCode());
            }
            boolean save = this.bWorkRecordService.save(bWorkRecord);
            if (save) return ResultObj.ADD_WORK_SUCCESS;
            return ResultObj.ADD_WORK_ERROR;
        } else {
            //判断是否重复打卡
            if(one.getDownTime()!=null){
                return ResultObj.ADD_WORK_ERROR_1;
            }
            //1 下班
            one.setDownTime(bWorkRecord.getUpTime());
            Integer workRecordDownTime = Integer.valueOf(bWorkRecord.getUpTime().substring(0, 2));
            if (workRecordDownTime < downTime) {
                one.setStatus(WorkStatusEnum.LEAVE.getCode());//早退
            } else {//加班
                if (workRecordDownTime >overTime || (Integer.valueOf(bWorkRecord.getUpTime().substring(3, 5))) > 0) {
                    one.setStatus(WorkStatusEnum.LATE.getCode());//加班
                }
            }
            boolean update = this.bWorkRecordService.updateById(one);
            if (update) return ResultObj.ADD_WORK_SUCCESS;
            return ResultObj.ADD_WORK_ERROR;
        }

    }

}

