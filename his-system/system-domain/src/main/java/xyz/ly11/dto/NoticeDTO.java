package xyz.ly11.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author 29794
 * @date 1/20/2021 21:13
 * 通知公告表
 */
@ApiModel(value = "xyz-ly11-domain-NoticeDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO extends BaseDTO {
    private static final long serialVersionUID = 811858573842864495L;
    /**
     * 公告ID
     */
    @ApiModelProperty(value = "公告ID")
    private Integer noticeId;

    /**
     * 公告标题
     */
    @NotBlank(message = "公告标题不能为空")
    @ApiModelProperty(value = "公告标题")
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    @NotBlank(message = "公告类型不能为空")
    @ApiModelProperty(value = "公告类型（1通知 2公告）")
    private String noticeType;

    /**
     * 公告内容
     */
    @NotBlank(message = "公告内容不能为空")
    @ApiModelProperty(value = "公告内容")
    private String noticeContent;

    /**
     * 公告状态（0正常 1关闭）
     */
    @NotBlank(message = "公告状态不能为空")
    @ApiModelProperty(value = "公告状态（0正常 1关闭）")
    private String status;

    /**
     * 创建者
     */
    @ApiModelProperty(value="创建者")
    private String createBy;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;


}