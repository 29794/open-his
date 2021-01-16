package xyz.ly11.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 29794
 * @date 1/16/2021 17:19
 * 角色信息DTO
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "xyz-ly11-domain-RoleDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO extends BaseDTO {

    private static final long serialVersionUID = -3614746907974165550L;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色权限编码
     */
    @NotBlank(message = "角色权限编码不能为空")
    @ApiModelProperty(value = "角色权限编码")
    private String roleCode;

    /**
     * 显示顺序
     */
    @NotNull(message = "角色排序码不能为空")
    @ApiModelProperty(value = "显示顺序")
    private Integer roleSort;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 角色状态（0正常 1停用）
     */
    @NotBlank(message = "角色状态不能为空")
    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    private String status;

}