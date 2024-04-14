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
    public class SysDept implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 父级部门ID
     */
      private Integer pid;

      /**
     * 部门名称
     */
      private String title;

      /**
     * 备注
     */
      private String remark;

      /**
     * 地址
     */
      private String address;

      /**
     * 状态【0不可用1可用】
     */
      private Integer available;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;


}
