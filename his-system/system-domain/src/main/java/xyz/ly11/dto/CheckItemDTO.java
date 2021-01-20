package xyz.ly11.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @author 29794
 * @date 1/20/2021 21:46
 * 检查费用表
 */
@ApiModel(value = "xyz-ly11-domain-CheckItemDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckItemDTO extends BaseDTO {

    private static final long serialVersionUID = -5879372295277936468L;

    /**
     * 项目费用ID
     */
    @ApiModelProperty(value = "项目费用ID")
    private Long checkItemId;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空")
    @ApiModelProperty(value = "项目名称")
    private String checkItemName;

    /**
     * 关键字【查询用】
     */
    @ApiModelProperty(value = "关键字【查询用】")
    private String keywords;

    /**
     * 项目单价
     */
    @ApiModelProperty(value = "项目单价")
    private BigDecimal unitPrice;

    /**
     * 项目成本
     */
    @ApiModelProperty(value = "项目成本")
    private BigDecimal cost;

    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String unit;

    /**
     * 项目类别IDsxt_sys_dict_type
     */
    @ApiModelProperty(value = "项目类别IDsxt_sys_dict_type")
    private String typeId;

    /**
     * 状态0正常1停用 sxt_sys_dict_type
     */
    @ApiModelProperty(value = "状态0正常1停用 sxt_sys_dict_type")
    private String status;


}