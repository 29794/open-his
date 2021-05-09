package xyz.ly11.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 29794
 * @date 1/20/2021 22:11
 * 挂号项目
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "xyz-ly11-domain-RegisteredItemDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredItemDTO extends BaseDTO {
    private static final long serialVersionUID = -6462482858567364409L;
    /**
     * 挂号项ID
     */
    @ApiModelProperty(value = "挂号项ID")
    private Long regItemId;

    /**
     * 挂号项目名称
     */
    @ApiModelProperty(value = "挂号项目名称")
    private String regItemName;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal regItemFee;


    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;


}