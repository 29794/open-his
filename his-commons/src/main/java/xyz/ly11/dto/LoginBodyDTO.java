package xyz.ly11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author by 29794
 * @date 2020/10/9 1:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginBodyDTO implements Serializable {

    private static final long serialVersionUID = 6861633313136051620L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

}
