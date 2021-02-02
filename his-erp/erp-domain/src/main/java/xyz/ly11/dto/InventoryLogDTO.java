package xyz.ly11.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 29794
 * @date 2/2/2021 21:55
 *
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="xyz-ly11-domain-InventoryLogDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "stock_inventory_log")
public class InventoryLogDTO extends BaseDTO  {
    private static final long serialVersionUID = 8283730009805226684L;

    /**
     * 采购单据ID
     */
    @ApiModelProperty(value = "采购单据ID")
    private String purchaseId;

    /**
     * 药品名称
     */
    @ApiModelProperty(value = "药品名称")
    private String medicinesName;

    /**
     * 药品分类 sys_dict_data表his_medicines_type
     */
    @ApiModelProperty(value = "药品分类 sys_dict_data表his_medicines_type")
    private String medicinesType;

    /**
     * 处方类型 sys_dict_data表his_prescription_type
     */
    @ApiModelProperty(value = "处方类型 sys_dict_data表his_prescription_type")
    private String prescriptionType;

    /**
     * 生产厂家ID
     */
    @ApiModelProperty(value = "生产厂家ID")
    private String producterId;


}