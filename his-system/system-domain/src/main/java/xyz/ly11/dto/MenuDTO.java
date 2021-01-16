package xyz.ly11.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author 29794
 * @date 1/16/2021 15:23
 * todo
 */

/**
 * 菜单权限表
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "xyz-ly11-domain-MenuDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO extends BaseDTO {
    private static final long serialVersionUID = 6760787039679379360L;
    /**
     * 菜单ID
     */
    @TableId(value = "menu_id")
    private Long menuId;

    /**
     * 父菜单ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 父节点ID集合
     */
    @ApiModelProperty(value = "父节点ID集合")
    private String parentIds;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    @NotBlank(message = "菜单名称不为空")
    private String menuName;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @NotBlank(message = "菜单类型不为空")
    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识")
    private String percode;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址")
    private String path;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 菜单状态（0正常 1停用）
     */
    @NotBlank(message = "菜单状态不能为空")
    @ApiModelProperty(value = "菜单状态（0正常 1停用）")
    private String status;

}