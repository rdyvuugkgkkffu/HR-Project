package com.zk.hr.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2024-03-31
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class BWorkRecord implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户ID
     */
      private Integer uid;

      /**
     * 打卡日期
     */
      private String workDate;

      /**
     * 上班时间
     */
      private String upTime;

      /**
     * 下班时间
     */
      private String downTime;

      /**
     * 打卡状态
     */
      private Integer status;

      /**
     * 创建时间
     */
      @TableField(fill = FieldFill.INSERT)
      private Date createTime;

      /**
     * 更新时间
     */
      @TableField(fill = FieldFill.INSERT_UPDATE)
      private Date updateTime;


}
