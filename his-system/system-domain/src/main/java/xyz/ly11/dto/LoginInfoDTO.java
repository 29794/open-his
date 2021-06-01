package xyz.ly11.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author 29794
 * @date 12/2/2020 23:31
 * 系统访问记录
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "xyz-ly11-dto-LoginInfoDTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoDTO extends BaseDTO {

    private static final long serialVersionUID = -4184684876763746464L;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;

    /**
     * 登陆账号
     */
    @ApiModelProperty(value = "登陆账号")
    private String loginAccount;

    /**
     * 登录IP地址
     */
    @ApiModelProperty(value = "登录IP地址")
    private String ipAddr;

    /**
     * 登录状态（0成功 1失败）字典表
     */
    @ApiModelProperty(value = "登录状态（0成功 1失败）字典表")
    private String loginStatus;

    /**scoop
     * 登陆类型：0系统用户1患者用户 字典表
     */
    @ApiModelProperty(value = "登陆类型0系统用户1患者用户 字典表")
    private String loginType;


}