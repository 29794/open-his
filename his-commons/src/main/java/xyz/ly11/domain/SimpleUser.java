package xyz.ly11.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author by 29794
 * @date 2020/10/9 0:59
 * 用户登录的数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUser implements Serializable {

    private static final long serialVersionUID = 1621855587137551039L;

    private Serializable userId;

    private String userName;

}
