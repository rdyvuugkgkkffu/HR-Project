package com.zk.hr.mapper;

import com.zk.hr.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2024-03-31
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {
    @Select({"select title from sys_dept where id = #{id} "})
    public String getDeptNameById(Integer id);

}
