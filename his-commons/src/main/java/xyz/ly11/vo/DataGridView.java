package xyz.ly11.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author by 29794
 * @date 2020/10/15 23:46
 */
@ApiModel(value = "xyz-ly11-vo-DataGridView")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataGridView implements Serializable {
    private static final long serialVersionUID = 7409470716985446086L;
    /**
     * 数据量
     */
    @ApiModelProperty("数据量")
    private Long total;

    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private List<?> data;
}
