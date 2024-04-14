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
    public class SysRole implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 角色ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 角色名称
     */
      private String name;

      /**
     * 角色备注
     */
      private String remark;

      /**
     * 可用状态
     */
      private Integer available;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;


}
