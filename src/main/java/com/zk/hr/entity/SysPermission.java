package com.zk.hr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    public class SysPermission implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 菜单或权限ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 父级菜单或权限ID
     */
      private Integer pid;

      /**
     * 菜单或权限名称
     */
      private String title;

      /**
     * 菜单图标
     */
      private String icon;

      /**
     * 菜单栏跳转路径
     */
      private String href;

      /**
     * 菜单是否展开
     */
      private Integer open;

      /**
     * 状态【0不可用1可用】
     */
      private Integer available;

    private String type;


}
