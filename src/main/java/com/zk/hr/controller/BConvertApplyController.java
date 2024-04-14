package com.zk.hr.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zk.hr.common.DataGridView;
import com.zk.hr.common.PageModel;
import com.zk.hr.common.ResultObj;
import com.zk.hr.entity.BConvertApply;
import com.zk.hr.entity.SysUser;
import com.zk.hr.mapper.SysUserMapper;
import com.zk.hr.service.BConvertApplyService;
import com.zk.hr.service.SysUserService;
import com.zk.hr.util.WebUtils;
import com.zk.hr.vo.BConvertApplyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/convertApply")
public class BConvertApplyController {
    @Autowired
    private BConvertApplyService convertApplyService;
    
    @Autowired
    private SysUserMapper userMapper;
    
    @RequestMapping("/loadAllconvertApply")
    public DataGridView loadAllconvertApply(PageModel pageModel){
        Page<BConvertApply> page = new Page<>(pageModel.getPage(), pageModel.getLimit());
        Page<BConvertApply> page1 = this.convertApplyService.page(page);
        List<BConvertApplyVO> list = new ArrayList<>();
        for (BConvertApply record : page1.getRecords()) {
            BConvertApplyVO vo = new BConvertApplyVO();
            BeanUtils.copyProperties(record,vo);
            vo.setApplyName(this.userMapper.getUserNameById(record.getApplyUserId()));
            vo.setApprovalName(this.userMapper.getUserNameById(record.getApprovalUserId()));
            list.add(vo);
        }
        return new DataGridView(page1.getTotal(),list);
    }
    
    @RequestMapping("/addConvertApply")
    public ResultObj addConvertApply(BConvertApply convertApply){
        convertApply.setStatus(0);
        SysUser user = (SysUser) WebUtils.getSession().getAttribute("user");
        convertApply.setApplyUserId(user.getId());
        boolean save = this.convertApplyService.save(convertApply);
        if (save) return ResultObj.ADD_SUCCESS;
        return ResultObj.ADD_ERROR;
    }

    @RequestMapping("/updateConvertApply")
    public ResultObj updateConvertApply(BConvertApply convertApply){
        convertApply.setApprovalDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        SysUser user = (SysUser) WebUtils.getSession().getAttribute("user");
        //不能自己审核自己
        if(user.getId().equals(convertApply.getApplyUserId())) return ResultObj.APPROVAL_ALREADY_ERROR;
        convertApply.setApprovalUserId(user.getId());
        boolean updateById = this.convertApplyService.updateById(convertApply);
        if(updateById) return ResultObj.APPROVAL_SUCCESS;
        return ResultObj.APPROVAL_ERROR;
    }

}

