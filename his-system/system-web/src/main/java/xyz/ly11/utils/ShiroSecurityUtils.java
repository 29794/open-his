package xyz.ly11.utils;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import xyz.ly11.constants.Constants;
import xyz.ly11.domain.SimpleUser;
import xyz.ly11.domain.User;
import xyz.ly11.vo.ActiveUser;

import java.util.List;

/**
 * @author by 29794
 * @date 2020/10/16 0:55
 */
public class ShiroSecurityUtils {

    /***
     *  得到当前登陆的用户对象的ActiveUser
     */
    public static ActiveUser getCurrentActiveUser() {
        Subject subject = SecurityUtils.getSubject();
        return (ActiveUser) subject.getPrincipal();
    }

    /***
     *  得到当前登陆的用户对象User
     */
    public static User getCurrentUser() {
        return getCurrentActiveUser().getUser();
    }

    /***
     *  得到当前登陆的用户对象SimpleUser
     */
    public static SimpleUser getCurrentSimpleUser() {
        User user = getCurrentActiveUser().getUser();
        return new SimpleUser(user.getUserId(), user.getUserName());
    }

    /***
     * 得到当前登陆的用户名称
     */
    public static String getCurrentUserName() {
        return getCurrentActiveUser().getUser().getUserName();
    }

    /***
     * 得到当前登陆对象的角色编码
     */
    public static List<String> getCurrentUserRoles() {
        return getCurrentActiveUser().getRoles();
    }


    /***
     *  得到当前登陆对象的权限编码
     */
    public static List<String> getCurrentUserPermissions() {
        return getCurrentActiveUser().getPermissions();
    }

    /***
     * 判断当前用户是否是超管
     */
    public static boolean isAdmin() {
        return getCurrentUser().getUserType().equals(Constants.USER_ADMIN);
    }

}


