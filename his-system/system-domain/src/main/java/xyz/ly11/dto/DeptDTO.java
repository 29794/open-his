package xyz.ly11.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 29794
 * @date 1/15/2021 21:07
 * 部门/科室表
 */
@ApiModel(value = "xyz-ly11-domain-Dept-DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptDTO extends BaseDTO{
    private static final long serialVersionUID = -5771827155236269464L;
    /**
     * 部门科室id
     */
    @ApiModelProperty(value = "部门科室id")
    private Long deptId;

    /**
     * 部门名称
     */
    @NotBlank(message = "科室名称不能为空")
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 挂号编号
     */
    @NotNull(message = "挂号编号不能为空")
    @ApiModelProperty(value = "挂号编号")
    private Integer regNumber;

    /**
     * 科室编号
     */
    @NotBlank(message = "科室编号不能为空")
    @ApiModelProperty(value = "科室编号")
    private String deptNumber;

    /**
     * 显示顺序
     */
    @NotNull(message = "排序码不能为空")
    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String deptLeader;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String leaderPhone;

    /**
     * 部门状态（0正常 1停用）
     */
    @NotBlank(message = "部门状态不能为空")
    @ApiModelProperty(value = "部门状态（0正常 1停用）")
    private String status;

}