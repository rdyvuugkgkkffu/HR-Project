package com.zk.hr.mapper;

import com.zk.hr.entity.BWorkRecord;
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
public interface BWorkRecordMapper extends BaseMapper<BWorkRecord> {
    @Select({"select u.name from sys_user u,b_work_record s where s.uid = u.id and s.id =#{id};"})
    public String getUserNameById(Integer id);
}
