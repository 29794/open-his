package xyz.ly11.vo;

import cn.hutool.http.useragent.UserAgentUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.ly11.domain.User;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author by 29794
 * @date 2020/10/9 20:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveUser implements Serializable {

    private static final long serialVersionUID = 5174286805223540871L;

    private User user;

    /**
     * 用户角色
     */
    private List<String> roles = Collections.emptyList();

    /**
     * 用户权限
     */
    private List<String> permissions;
}
