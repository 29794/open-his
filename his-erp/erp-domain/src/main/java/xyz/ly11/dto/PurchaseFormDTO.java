package xyz.ly11.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 29794
 * @date 2/2/2021 22:01
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("com-ly11-dto-PurchaseFromDTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseFormDTO extends BaseDTO {

    private static final long serialVersionUID = -7471761405945120693L;
    //存放采购单主表数据
    private PurchaseDTO purchaseDto;

    //存放采购单详情数据
    private List<PurchaseItemDTO> purchaseItemDtos;


}