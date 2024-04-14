package com.zk.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
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
    public class SysUser implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 用户ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 登录名
     */
      private String loginname;

      /**
     * 姓名
     */
      private String name;

      /**
     * 密码
     */
      private String password;

      /**
     * 性别
     */
      private Integer sex;

      /**
     * 是否可用
     */
      private Integer available;

      /**
     * 入职时间
     */
      private LocalDateTime hiredate;

      /**
     * 部门ID
     */
      private Integer deptid;

      /**
     * 地址
     */
      private String address;

      /**
     * 备注
     */
      private String remark;

      /**
     * 电话
     */
      private String telephone;

      /**
     * 邮箱
     */
      private String email;


}
