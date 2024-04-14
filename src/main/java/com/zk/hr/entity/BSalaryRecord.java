package com.zk.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
    public class BSalaryRecord implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户ID
     */
      private Long uid;

      /**
     * 工资月份
     */
      private String salaryMonth;

      /**
     * 基本工资
     */
      private Float mustSalary;

      /**
     * 全勤工资
     */
      private Float realitySalary;

      /**
     * 迟到扣款
     */
      private Float lateAmount;

      /**
     * 纳税扣款
     */
      private Float taxAmount;

      /**
     * 绩效奖金
     */
      private Float meritsAmount;

      /**
     * 五险扣款
     */
      private Float pensionAmount;

      /**
     * 请假扣款
     */
      private Float leaveAmount;

      /**
     * 创建时间
     */
      private Date createTime;


}
