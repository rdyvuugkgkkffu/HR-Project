package com.zk.hr.mapper;

import com.zk.hr.entity.BSalaryRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2024-03-31
 */
public interface BSalaryRecordMapper extends BaseMapper<BSalaryRecord> {
    //用户名字查询
    @Select({"select u.name from sys_user u,b_salary_record s where s.uid = u.id and s.id =#{id};"})
    public String getUserNameById(Integer id);

    //部门名字查询
    @Select({"select d.title from sys_user u,b_salary_record s ,hr.sys_dept d where s.uid = u.id and u.deptid = d.id and s.id =1;"})
    public String getDeptNameById(Integer id);
}
