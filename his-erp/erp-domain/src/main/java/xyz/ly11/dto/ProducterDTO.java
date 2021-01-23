package xyz.ly11.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author 29794
 * @date 1/22/2021 21:49
 * 生产厂家数据传输对象
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "xyz-ly11-domain-ProducterDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "stock_producter")
public class ProducterDTO extends BaseDTO {
    private static final long serialVersionUID = 941175392792927101L;
    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    private Long producterId;

    /**
     * 厂家名称
     */
    @NotBlank(message = "厂家名称不能为空")
    @ApiModelProperty(value = "厂家名称")
    private String producterName;

    /**
     * 厂家简码 搜索用
     */
    @NotBlank(message = "厂家名称不能为空")
    @ApiModelProperty(value = "厂家简码 搜索用")
    private String producterCode;

    /**
     * 厂家地址
     */
    @ApiModelProperty(value = "厂家地址 ")
    private String producterAddress;

    /**
     * 厂家电话
     */
    @NotBlank(message = "厂家电话不能为空")
    @ApiModelProperty(value = "厂家电话")
    private String producterTel;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String producterPerson;

    /**
     * 关键字
     */
    @NotBlank(message = "关键字不能为空")
    @ApiModelProperty(value = "关键字")
    private String keywords;

    /**
     * 状态标志（0正常 1停用）sys_normal_disable
     */
    @NotBlank(message = "状态标志不能为空")
    @ApiModelProperty(value = "状态标志（0正常 1停用）sys_normal_disable")
    private String status;

}